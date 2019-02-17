package de.thg.tinder.http.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import de.thg.tinder.http.pojo.HttpHeader;
import de.thg.tinder.http.pojo.HttpMessage;

/**
 * A utility class that sends HTTP POST and GET requests using Tinder specific HttpHeaders
 * @author Timo Grimm
 *
 */
public class HttpRequestFactory {

	private static String convertResponseToString(HttpResponse response) {
		InputStream ips;
		try {
			ips = response.getEntity().getContent();
			BufferedReader buf = new BufferedReader(new InputStreamReader(ips, "UTF-8"));
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new Exception("StatusCode: " + Integer.toString(response.getStatusLine().getStatusCode()));
			}
			StringBuilder sb = new StringBuilder();
			String s;
			while (true) {
				s = buf.readLine();
				if (s == null || s.length() == 0)
					break;
				sb.append(s);
			}
			buf.close();
			ips.close();
			return sb.toString();
		} catch (UnsupportedOperationException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Sends a POST request
	 * @param url
	 * @param httpMessage
	 * @return
	 */
	public static String doPOST(String url, HttpMessage httpMessage) {
		HttpClient httpClient = HttpClientBuilder.create().build(); // Use this instead

		try {
			HttpPost request = new HttpPost(url);
			if (StringUtils.isNotEmpty(httpMessage.getHeader().getSessionId())) {
				request.setHeader("X-Auth-Token", httpMessage.getHeader().getSessionId());
			}
			if (StringUtils.isNotEmpty(httpMessage.getHeader().getPlatform())) {
				request.setHeader("platform", httpMessage.getHeader().getPlatform());
			}
			if (StringUtils.isNotEmpty(httpMessage.getHeader().getInstallId())) {
				request.setHeader("install-id", httpMessage.getHeader().getInstallId());
			}
			if (StringUtils.isNotEmpty(httpMessage.getHeader().getPlatform())) {
				request.setHeader("platform", httpMessage.getHeader().getPlatform());
			}
			if (StringUtils.isNotEmpty(httpMessage.getHeader().getPlatform())) {
				request.setHeader("Content-Type", httpMessage.getHeader().getContentType());
			}
			if (StringUtils.isNotEmpty(httpMessage.getContent())) {
				request.setEntity(new StringEntity(httpMessage.getContent()));
			}
			HttpResponse response = httpClient.execute(request);
			return convertResponseToString(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Sends a GET Request
	 * @param url
	 * @param header
	 * @return
	 */
	public static String doGET(String url, HttpHeader header) {
		HttpClient httpClient = HttpClientBuilder.create().build(); // Use this instead
		try {
			HttpGet request = new HttpGet(url);
			if (StringUtils.isNotEmpty(header.getSessionId())) {
				request.setHeader("X-Auth-Token", header.getSessionId());
			}
			if (StringUtils.isNotEmpty(header.getPlatform())) {
				request.setHeader("platform", header.getPlatform());
			}
			if (StringUtils.isNotEmpty(header.getInstallId())) {
				request.setHeader("install-id", header.getInstallId());
			}
			if (StringUtils.isNotEmpty(header.getPlatform())) {
				request.setHeader("platform", header.getPlatform());
			}
			if (StringUtils.isNotEmpty(header.getPlatform())) {
				request.setHeader("Content-Type", header.getContentType());
			}
			HttpResponse response = httpClient.execute(request);
			return convertResponseToString(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
