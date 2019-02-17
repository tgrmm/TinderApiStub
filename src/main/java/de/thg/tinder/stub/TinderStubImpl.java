package de.thg.tinder.stub;

import de.thg.tinder.api.pojos.SmsValidatorResponse;
import de.thg.tinder.common.Constants;
import de.thg.tinder.http.pojo.HttpHeader;
import de.thg.tinder.http.pojo.HttpMessage;
import de.thg.tinder.http.utils.HttpRequestFactory;
import de.thg.tinder.mapper.JsonToPojoMapper;
import de.thg.tinder.mapper.PojoToJsonMapper;

/**
 *  A stub implementation for the Tinder API
 * 
 * @author Timo Grimm
 *
 */
public class TinderStubImpl implements TinderStub {

	public static void main(String[] args) {
		TinderStubImpl wrapper = new TinderStubImpl();
		try {
			String t = wrapper.setLocation("76a488e1-8968-475e-b673-344395df1ef8", "14.599512", "120.984222");
			System.out.println(t);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean like(String sessionId) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean dislike(String sessionId) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private HttpHeader createDefaultHeader(String sessionId) {
		HttpHeader header = new HttpHeader();
		header.setSessionId(sessionId);
		header.setPlatform("web");
		header.setContentType("application/json");
		return header;
	}
	
	private HttpHeader createInitHeader(String installId) {
		HttpHeader header = new HttpHeader();
		header.setInstallId(installId);
		header.setPlatform("web");
		header.setContentType("application/json");
		return header;
	}	

	public String setLocation(String sessionId, String latitude, String longitude) {
		String url = buildURL(Constants.RESOURCE_SET_LOCATION);

		HttpHeader header = createDefaultHeader(sessionId);
		String jsonContent = PojoToJsonMapper.convertLocationPojoToJson(latitude, longitude);
		HttpMessage httpMessage = new HttpMessage(header, jsonContent);
		return HttpRequestFactory.doPOST(url, httpMessage);
	}
	
	private String buildURL(String resource) {
		return Constants.URL_ENDPOINT + "/" + resource;
	}

	@Override
	public String sendSMSCode(String phoneNumber) {
		String jsonContent = PojoToJsonMapper.convertPhoneNumberPojoToJson(phoneNumber);
		String url = buildURL(Constants.RESOURCE_SEND_SMS);
		HttpHeader header = createInitHeader(Constants.INSTALL_ID);
		HttpMessage httpMessage = new HttpMessage(header, jsonContent);
		return HttpRequestFactory.doPOST(url, httpMessage);			
	}
	
	@Override
	public String createSessionIdFromSMSCode(String phoneNumber, String smsCode) {
		String refreshToken = createRefreshTokenFromSMSCode(phoneNumber, smsCode);
		String sessionId = createSessionIdFromRefreshToken(phoneNumber, refreshToken);
		return sessionId;
	}
	
	private String createRefreshTokenFromSMSCode(String phoneNumber, String smsCode) {
		String urlRefreshToken = buildURL(Constants.RESOURCE_GET_REFRESH_TOKEN);
		String jsonRefreshTokenRequestContent = PojoToJsonMapper.convertSMSValidatorRequestPojoToJson(phoneNumber, smsCode);
		HttpHeader header = createInitHeader(Constants.INSTALL_ID);
		HttpMessage httpMessage = new HttpMessage(header, jsonRefreshTokenRequestContent);
		String jsonResponse = HttpRequestFactory.doPOST(urlRefreshToken, httpMessage);			
		SmsValidatorResponse response = JsonToPojoMapper.convertSMSValidatorResponseJsonToPojo(jsonResponse);
		return response.getData().getRefreshToken();
	}
	
	private String createSessionIdFromRefreshToken(String phoneNumber, String refreshToken) {
		HttpHeader header = createInitHeader(Constants.INSTALL_ID);
		String urlSessionId = buildURL(Constants.RESOURCE_GET_SESSION_ID);
		String content = PojoToJsonMapper.convertLoginRequestPojoToJson(phoneNumber, refreshToken);
		String result = HttpRequestFactory.doPOST(urlSessionId, new HttpMessage(header, content));	
		return result;
	}

	@Override
	public String getProspectives(String sessionId) {
		String url = buildURL(Constants.RESOURCE_GET_PROSPECTIVE);
		HttpHeader header = createDefaultHeader(sessionId);
		String result = HttpRequestFactory.doGET(url, header);	
		return result;
	}
	
}
