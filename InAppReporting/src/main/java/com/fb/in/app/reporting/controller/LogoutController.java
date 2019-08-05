/**
 * 
 */
package com.fb.in.app.reporting.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author SKumar7
 *
 */
@Controller
public class LogoutController {
	private static final String Logout_Url = "http://10.200.10.21:8081/api/v1/authentication/logout";

	@RequestMapping("/logout")
	public String getReport(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

		return "redirect:" + Logout_Url;
	}
}
