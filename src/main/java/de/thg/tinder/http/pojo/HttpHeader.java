package de.thg.tinder.http.pojo;

/**
 * A basic HTTP header used to send messages to Tinder API
 * 
 * @author Timo Grimm
 *
 */
public class HttpHeader {

	private String sessionId;

	private String contentType;

	private String platform;

	private String installId;
	

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getInstallId() {
		return installId;
	}

	public void setInstallId(String installId) {
		this.installId = installId;
	}

	
	
}
