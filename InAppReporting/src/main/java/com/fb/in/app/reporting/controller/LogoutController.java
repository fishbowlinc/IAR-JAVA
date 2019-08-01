/**
 * 
 */
package com.fb.in.app.reporting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author SKumar7
 *
 */
@Controller
public class LogoutController {
	private static final String sisense_logout_url = "http://10.200.10.21:8081/api/auth/logout";

	@GetMapping("/app-logout")
	public RedirectView processRequest() {
		return new RedirectView(sisense_logout_url);
	}
}
