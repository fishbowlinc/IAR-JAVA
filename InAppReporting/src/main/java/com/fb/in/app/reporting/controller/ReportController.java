/**
 * 
 */
package com.fb.in.app.reporting.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fb.in.app.reporting.sso.auth.JwtUtil;

/**
 * @author SKumar7
 *
 */
@Controller
public class ReportController {
	private static final String jwtTokenCookieName = "JWT-TOKEN";
	private static final String signingKey = "d652385f5169a19ba3739a0a803396f6e4a5e6f076e3d80f435d6be2324220a8";

	@RequestMapping("/report")
	public String getReport(HttpServletRequest request, Model model) {
		String username = JwtUtil.getSubject(request, jwtTokenCookieName, signingKey);
		if (username == null) {
			model.addAttribute("error", "user is not authorised");
			return "login";
		} else
			return "reportDashboard";
	}
}
