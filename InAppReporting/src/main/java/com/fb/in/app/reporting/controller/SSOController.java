package com.fb.in.app.reporting.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fb.in.app.reporting.sso.auth.JwtUtil;
import com.fb.in.app.reporting.sso.auth.SisenseUtil;

@Controller
public class SSOController {
	private static final String jwtTokenCookieName = "JWT-TOKEN";
	private static final String signingKey = "d652385f5169a19ba3739a0a803396f6e4a5e6f076e3d80f435d6be2324220a8";

	@RequestMapping("/sso-handler")
	public String processRequest(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		String username = JwtUtil.getSubject(request, jwtTokenCookieName, signingKey);
		if (username == null) {
			model.addAttribute("error", "user is not authorised");
			return "index";
		} else {
			String token = SisenseUtil.generateToken(signingKey, username);
			// This is the Sisense URL which can handle (decode and process) the JWT token
			String redirectUrl = "http://10.200.10.21:8081/jwt?jwt=" + token;
			// Which URL the user was initially trying to open
			String returnTo = request.getParameter("return_to");
			if (returnTo != null) {
				redirectUrl += "&return_to=" + URLEncoder.encode(returnTo, "UTF-8");
			}

			return "redirect:" + redirectUrl;
		}
	}
}
