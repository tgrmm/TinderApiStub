package de.thg.tinder.proxy;

import java.util.List;

/**
 * Proxy interface providing the most common functionality of the Tinder API
 * 
 * @author Timo Grimm
 *
 */
public interface TinderAPI {
	
	/**
	 * Sends an sms containing the sms code to create the session id
	 * @param phoneNumber the user's phone number
	 * @return smsCode Code to authenticate user
	 */
	public String sendSMSCodeToPhoneNumber(String phoneNumber);
	
	/**
	 * Creates a session id. The session id is needed for all subsequent requests.
	 * @param phoneNumber The user's phone number. Must match the phone number from sendSMSCode(phoneNumber)
	 * @param smsCode The sms code retrieved from the received sms
	 * @return
	 */
	public String createSessionIdFromSMSCode(String phoneNumber, String smsCode);

	/**
	 * Returns a list of prospective candidates that match the criteria defined in the search filter.
	 * @return
	 */
	public List<String> getCandidates(String sessionId);
	
	/**
	 * 
	 * @param sessionId
	 * @return
	 */
	public boolean like(String sessionId, String userId);
	
	/**
	 * @param sessionId
	 * @return
	 */
	public boolean dislike(String sessionId);
	
	/**
	 * Sets the location
	 * This method requires gold membership!!
	 * @param sessionId
	 * @param latudiude
	 * @param longitude
	 * @return
	 */
	public String setLocation(String sessionId, String latudiude, String longitude);
	
	/**
	 * Sets the search filter
	 * @param sessionId
	 * @param minimumAge
	 * @param maximumAge
	 * @param maximumDistance
	 * @return
	 */
	public String setSearchFilter(String sessionId, int minimumAge, int maximumAge, int maximumDistance);
	
}
