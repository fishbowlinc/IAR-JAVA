package com.fb.in.app.reporting.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fb.in.app.reporting.sso.auth.CookieUtil;

@Controller
public class SSOLogout {
	private static final String jwtTokenCookieName = "JWT-TOKEN";

	@RequestMapping("/sso-logout")
	public String siseneLogout(HttpServletResponse httpServletResponse) throws IOException {
		CookieUtil.clear(httpServletResponse, jwtTokenCookieName);
		return "redirect:login.html";
	}
}
