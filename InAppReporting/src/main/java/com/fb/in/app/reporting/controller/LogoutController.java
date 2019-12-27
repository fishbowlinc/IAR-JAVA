/**
 * 
 */
package com.fb.in.app.reporting.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fb.in.app.reporting.constants.AppConstants;

/**
 * @author SKumar7
 *
 */
@Controller
public class LogoutController {

	@RequestMapping("/logout")
	public String getReport(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		return "redirect:" + AppConstants.SISENSE_APP_LOGOUT_URL;
	}
}
