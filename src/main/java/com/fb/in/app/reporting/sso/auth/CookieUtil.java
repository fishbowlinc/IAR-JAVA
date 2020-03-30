package com.fb.in.app.reporting.sso.auth;

import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	public static void create(HttpServletResponse httpServletResponse, String name, String value, Boolean secure,
			Integer maxAge, String domain) {
		Cookie cookie = new Cookie(name, value);
		cookie.setSecure(secure);
		cookie.setHttpOnly(true);
		cookie.setMaxAge(maxAge);
		cookie.setDomain(domain);
		cookie.setPath("/");
		httpServletResponse.addCookie(cookie);
	}

	public static void clear(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			String name) {
		Cookie cookie = WebUtils.getCookie(httpServletRequest, name);
		if (null != cookie) {
			cookie.setPath("/");
			cookie.setHttpOnly(true);
			cookie.setMaxAge(0);
			httpServletResponse.addCookie(cookie);
			httpServletResponse.setHeader("Cache-Control", "no-cache,no-store,must-revalidate"); // HTTP 1.1
			httpServletResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0
			httpServletResponse.setDateHeader("Expires", 0); // Proxies
		}
	}

	public static String getValue(HttpServletRequest httpServletRequest, String name) {
		Cookie cookie = WebUtils.getCookie(httpServletRequest, name);
		return cookie != null ? cookie.getValue() : null;
	}
}
