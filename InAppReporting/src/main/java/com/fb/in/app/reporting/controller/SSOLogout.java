package com.fb.in.app.reporting.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;

import com.fb.in.app.reporting.sso.auth.CookieUtil;

public class SSOLogout {
	private static final String jwtTokenCookieName = "JWT-TOKEN";

	@PostMapping("/sso-logout")
	public String siseneLogout(HttpServletResponse httpServletResponse) throws IOException {
		CookieUtil.clear(httpServletResponse, jwtTokenCookieName);
		// SisenseUtil.logoutFromSisense();
		return "index";
	}
}
