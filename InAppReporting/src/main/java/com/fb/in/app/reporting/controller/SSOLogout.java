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

import com.fb.in.app.reporting.sso.auth.AuthUtil;
import com.fb.in.app.reporting.util.FBRestResponse;

@Controller
public class SSOLogout {
//	private static final String redirectURL = "https://loginqa.fishbowl.com";
	private static Logger logger = LoggerFactory.getLogger(LogoutController.class);

	@RequestMapping("/sso-logout")
	public ResponseEntity<?> siseneLogout(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("LogoutController logout function starts :::");
		Cookie[] cookieList = request.getCookies();
		if (cookieList != null) {
			response = AuthUtil.deleteCookies(request, response);
		} else {
			return new ResponseEntity<FBRestResponse>(new FBRestResponse(false, "Session already expired"),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<FBRestResponse>(
				new FBRestResponse(true,
						"http://" + AuthUtil.getParentAppUrl(request.getServerName()) + "/Public/Login.aspx"),
				HttpStatus.OK);
	}
}
