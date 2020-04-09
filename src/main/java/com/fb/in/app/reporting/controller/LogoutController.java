/**
 * 
 */
package com.fb.in.app.reporting.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fb.in.app.reporting.constants.AppConstants;
import com.fb.in.app.reporting.sso.auth.AuthUtil;

/**
 * @author SKumar7
 *
 */
@Controller
@RequestMapping("/iar/sso")
public class LogoutController {
	private static Logger logger = LoggerFactory.getLogger(LogoutController.class);

	@RequestMapping("/logout-redirect")
	public String getReport(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		return "redirect:" + AppConstants.SISENSE_APP_LOGOUT_URL;
	}

	@RequestMapping("/logout-handler")
	public String siseneLogout(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("LogoutController logout function starts :::");
		Cookie[] cookieList = request.getCookies();
		if (cookieList != null)
			AuthUtil.deleteCookies(request, response, cookieList);
		String redirectUrl = "https://" + AuthUtil.getParentAppUrl(request.getServerName()) + "/Public/Login.aspx";

		return "redirect:" + redirectUrl;
	}
}
