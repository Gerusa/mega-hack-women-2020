package br.com.otto.play.comum;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * @author time21
 *
 */
public class HttpsConnection {

	public static HttpsURLConnection getHttpsConnection(String connectingUrl) throws Exception {
		URL url = new URL(connectingUrl);
		HttpsURLConnection webRequest = (HttpsURLConnection) url.openConnection();
		return webRequest;
	}
}
