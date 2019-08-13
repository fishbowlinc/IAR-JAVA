package com.fb.in.app.reporting.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fb.in.app.reporting.sso.auth.CookieUtil;

@Controller
public class AppLogoutController {
	private static final String jwtTokenCookieName = "JWT-TOKEN";

	@RequestMapping("/app-logout")
	public String getReport(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		CookieUtil.clear(httpServletRequest, httpServletResponse, jwtTokenCookieName);
		return "login";
	}
}