package de.thg.tinder.stub;

/**
 * Stub interface for Tinder API
 * 
 * @author Timo Grimm
 *
 */
public interface TinderStub {
	
	/**
	 * Sends an sms containing the otp code to create session id
	 * @param phoneNumber the user's phone number
	 * @return smsCode Code to authenticate user
	 */
	public String sendSMSCode(String phoneNumber);
	
	/**
	 * Creates a session id
	 * @param phoneNumber
	 * @param smsCode
	 * @return
	 */
	public String createSessionIdFromSMSCode(String phoneNumber, String smsCode);

	/**
	 * Returns a list of possible prospectives
	 * @return
	 */
	public String getProspectives(String sessionId);
	
	/**
	 * 
	 * @param sessionId
	 * @return
	 */
	public boolean like(String sessionId);
	
	/**
	 * 
	 * @param sessionId
	 * @return
	 */
	public boolean dislike(String sessionId);
	
	/**
	 * Sets the location
	 * @param sessionId
	 * @param latudiude
	 * @param longitude
	 * @return
	 */
	public String setLocation(String sessionId, String latudiude, String longitude);
	
}
