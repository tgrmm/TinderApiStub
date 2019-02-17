package de.thg.tinder.http.pojo;

/**
 * Pojo representing a basic HTTP request Message
 * 
 * @author Timo Grimm
 *
 */
public class HttpMessage {

	/**
	 * The http header
	 */
	private HttpHeader header;
	
	/**
	 * The body of the http message, typically in JSON Format
	 */
	private String content;

	public HttpMessage(HttpHeader header, String content) {
		super();
		this.header = header;
		this.content = content;
	}

	public HttpHeader getHeader() {
		return header;
	}

	public void setHeader(HttpHeader header) {
		this.header = header;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
