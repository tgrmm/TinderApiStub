package de.thg.tinder.proxy;

import java.util.List;

import de.thg.tinder.api.pojos.Profile;
import de.thg.tinder.api.pojos.User;
import de.thg.tinder.common.URLConstants;
import de.thg.tinder.http.pojo.HttpHeader;
import de.thg.tinder.http.pojo.HttpMessage;
import de.thg.tinder.http.utils.HttpRequestFactory;
import de.thg.tinder.mapper.JsonToPojoMapper;
import de.thg.tinder.mapper.PojoToJsonMapper;

/**
 *  Connects to tinder API providing the most common functionality
 * 
 * @author Timo Grimm
 *
 */
public class TindeAPIImpl implements TinderAPI {

	public static String INSTALL_ID = "90a48525-2775-477f-bfe7-6d52c160543c";

	
	public static void main(String[] args) {
		TindeAPIImpl wrapper = new TindeAPIImpl();
		try {
			String t = wrapper.setLocation("aca32a06-73fd-4de2-961a-c9a1a94c49b6", "15.519412", "120.982222");
			System.out.println(t);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		String url = buildURL(URLConstants.RESOURCE_SET_LOCATION);

		HttpHeader header = createDefaultHeader(sessionId);
		String jsonContent = PojoToJsonMapper.convertLocationPojoToJson(latitude, longitude);
		HttpMessage httpMessage = new HttpMessage(header, jsonContent);
		return HttpRequestFactory.doPOST(url, httpMessage);
	}
	
	private String buildURL(String resource) {
		return URLConstants.URL_ENDPOINT + "/" + resource;
	}

	@Override
	public String sendSMSCodeToPhoneNumber(String phoneNumber) {
		String url = buildURL(URLConstants.RESOURCE_SEND_SMS);
		HttpHeader header = createInitHeader(INSTALL_ID);
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
		String urlRefreshToken = buildURL(URLConstants.RESOURCE_GET_REFRESH_TOKEN);
		HttpHeader httpHeader = createInitHeader(INSTALL_ID);
		String httpContent = PojoToJsonMapper.convertSMSValidatorRequestPojoToJson(phoneNumber, smsCode);
		HttpMessage httpMessage = new HttpMessage(httpHeader, httpContent);
		String response = HttpRequestFactory.doPOST(urlRefreshToken, httpMessage);			
		return JsonToPojoMapper.convertSMSValidatorResponseJsonToPojo(response).getData().getRefreshToken();
	}
	
	private String createSessionIdFromRefreshToken(String phoneNumber, String refreshToken) {
		HttpHeader header = createInitHeader(INSTALL_ID);
		String urlSessionId = buildURL(URLConstants.RESOURCE_GET_SESSION_ID);
		String content = PojoToJsonMapper.convertLoginRequestPojoToJson(phoneNumber, refreshToken);
		String result = HttpRequestFactory.doPOST(urlSessionId, new HttpMessage(header, content));	
		return result;
	}

	@Override
	public List<String> getCandidates(String sessionId) {
		String url = buildURL(URLConstants.RESOURCE_GET_PROSPECTIVE);
		HttpHeader header = createDefaultHeader(sessionId);
		String result = HttpRequestFactory.doGET(url, header);	
		return null;
	}

	@Override
	public String setSearchFilter(String sessionId, int minimumAge, int maximumAge, int maximumDistance) {
		String url = buildURL(URLConstants.RESOURCE_PROFILE);
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

	@Override
	public boolean like(String sessionId, String userId) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
