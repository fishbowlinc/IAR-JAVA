/**
 * 
 */
package com.fb.in.app.reporting.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fb.in.app.reporting.constants.AppConstants;
import com.fb.in.app.reporting.model.mail.MailPayload;
import com.fb.in.app.reporting.model.mail.SisenseMailObject;
import com.fb.in.app.reporting.sso.auth.AuthUtil;
import com.fb.in.app.reporting.sso.auth.SisenseUtil;

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
		Cookie[] cookieList = httpServletRequest.getCookies();
		if (cookieList != null)
			AuthUtil.deleteCookies(httpServletRequest, httpServletResponse, cookieList);
		return "redirect:" + AppConstants.SISENSE_APP_LOGOUT_URL;
	}

	@RequestMapping("/logout-handler")
	public String siseneLogout(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("LogoutController logout function starts :::");
		Cookie[] cookieList = request.getCookies();
		if (cookieList != null)
			AuthUtil.deleteCookies(request, response, cookieList);
		String redirectUrl = AuthUtil.getParentAppUrl(request.getServerName());
		return "redirect:" + redirectUrl;
	}

	@RequestMapping(value = "/send-mail", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> sendMailToSisenseUser(HttpServletRequest request, HttpServletResponse response,
			SisenseMailObject mailObject) {
		MailPayload mailPayload = SisenseUtil.generateMailPayload(mailObject);
		SisenseUtil.sendMailToSisenseUser(mailPayload);
		return ResponseEntity.ok(HttpStatus.OK);
	}
}
