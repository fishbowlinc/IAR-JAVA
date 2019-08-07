package com.fb.in.app.reporting.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SSOLogout {
	@RequestMapping("/sso-logout")
	public String siseneLogout(HttpServletResponse httpServletResponse) {
		return "login";
	}
}
