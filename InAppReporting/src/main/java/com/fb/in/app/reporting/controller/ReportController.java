/**
 * 
 */
package com.fb.in.app.reporting.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author SKumar7
 *
 */
@Controller
public class ReportController {
	@GetMapping("/report")
	public void getReport(HttpServletResponse httpServletResponse) throws IOException{
		httpServletResponse.sendRedirect("reports.html");
		return;
	}
}
