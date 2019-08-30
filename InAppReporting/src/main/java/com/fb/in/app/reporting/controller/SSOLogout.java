package com.fb.in.app.reporting.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fb.in.app.reporting.sso.auth.AuthUtil;

@Controller
public class SSOLogout {
	// private static final String redirectURL = "https://loginqa.fishbowl.com";
	private static Logger logger = LoggerFactory.getLogger(LogoutController.class);

	@RequestMapping("/sso-logout")
	public String siseneLogout(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("LogoutController logout function starts :::");
		Cookie[] cookieList = request.getCookies();
		String redirectUrl = "http://" + AuthUtil.getParentAppUrl(request.getServerName()) + "/Public/Login.aspx";
		if (cookieList != null)
			response = AuthUtil.deleteCookies(request, response);
		return "redirect:" + redirectUrl;
	}
}
