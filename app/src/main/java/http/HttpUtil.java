package http;

import java.util.Base64;

public final class HttpUtil {
	public static final String ENDPOINT = "http://localhost:8080/bus-tickets/rest/tickets";

	public static String buildUrlWith(String ticketId) {
		return new StringBuilder(ENDPOINT).append("/").append(ticketId).toString();
	}

	public static String buildEncodedCredentials(String username, String password) {
		String credentials = new StringBuilder(username).append(":").append(password).toString();
		return new String(Base64.getEncoder().encode(credentials.getBytes()));
	}
}
