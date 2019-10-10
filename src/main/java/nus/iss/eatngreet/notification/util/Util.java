package nus.iss.eatngreet.notification.util;

import javax.servlet.http.HttpServletRequest;

public class Util {

	public static boolean checkAuthHeader(HttpServletRequest request) {
		if (!isStringEmpty(request.getHeader(Constants.AUTHORIZATION_HEADER_NAME))) {
			String authToken = request.getHeader(Constants.AUTHORIZATION_HEADER_NAME).substring("Bearer".length())
					.trim();
			return Constants.CONFIRMATION_MAIL_TOKEN.equals(authToken);
		}
		return false;
	}

	public static boolean isStringEmpty(String str) {
		return (str == null || str.trim().length() == 0);
	}

	private Util() {

	}

}
