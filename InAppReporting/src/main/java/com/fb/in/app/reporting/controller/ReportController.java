/**
 * 
 */
package com.fb.in.app.reporting.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author SKumar7
 *
 */
@Controller
public class ReportController {
	@RequestMapping("/report")
	public String getReport(HttpServletResponse httpServletResponse) {
		return "reports";
	}
}
