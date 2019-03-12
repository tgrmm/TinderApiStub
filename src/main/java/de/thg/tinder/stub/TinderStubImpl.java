package de.thg.tinder.stub;

import de.thg.tinder.api.pojos.Profile;
import de.thg.tinder.api.pojos.User;
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
			String t = wrapper.setLocation("aca32a06-73fd-4de2-961a-c9a1a94c49b6", "15.519412", "120.982222");
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
		String url = buildURL(Constants.RESOURCE_SEND_SMS);
		HttpHeader header = createInitHeader(Constants.INSTALL_ID);
		String content = PojoToJsonMapper.convertPhoneNumberPojoToJson(phoneNumber);
		HttpMessage httpMessage = new HttpMessage(header, content);
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
		HttpHeader httpHeader = createInitHeader(Constants.INSTALL_ID);
		String httpContent = PojoToJsonMapper.convertSMSValidatorRequestPojoToJson(phoneNumber, smsCode);
		HttpMessage httpMessage = new HttpMessage(httpHeader, httpContent);
		String response = HttpRequestFactory.doPOST(urlRefreshToken, httpMessage);			
		return JsonToPojoMapper.convertSMSValidatorResponseJsonToPojo(response).getData().getRefreshToken();
	}
	
	private String createSessionIdFromRefreshToken(String phoneNumber, String refreshToken) {
		HttpHeader header = createInitHeader(Constants.INSTALL_ID);
		String urlSessionId = buildURL(Constants.RESOURCE_GET_SESSION_ID);
		String content = PojoToJsonMapper.convertLoginRequestPojoToJson(phoneNumber, refreshToken);
		String result = HttpRequestFactory.doPOST(urlSessionId, new HttpMessage(header, content));	
		return result;
	}

	@Override
	public String getProspectiveCandidates(String sessionId) {
		String url = buildURL(Constants.RESOURCE_GET_PROSPECTIVE);
		HttpHeader header = createDefaultHeader(sessionId);
		String result = HttpRequestFactory.doGET(url, header);	
		return result;
	}

	@Override
	public String updateSearchFilter(String sessionId, int minimumAge, int maximumAge, int maximumDistance) {
		String url = buildURL(Constants.RESOURCE_PROFILE);
		Profile profile = new Profile();
		profile.setUser(new User());
		profile.getUser().setAgeFilterMax(maximumAge);
		profile.getUser().setAgeFilterMin(minimumAge);
		profile.getUser().setDistanceFilter(maximumDistance);
		HttpHeader httpHeader = createDefaultHeader(sessionId);
		String httpContent = PojoToJsonMapper.convertProfilePojoToJson(minimumAge, maximumAge, maximumDistance);
		HttpMessage httpMessage = new HttpMessage(httpHeader, httpContent);
		return HttpRequestFactory.doPOST(url, httpMessage);
	}
	
}
