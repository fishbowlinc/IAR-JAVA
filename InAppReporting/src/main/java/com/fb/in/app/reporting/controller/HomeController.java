/**
 * 
 */
package com.fb.in.app.reporting.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fb.in.app.reporting.sso.auth.CookieUtil;
import com.fb.in.app.reporting.sso.auth.JwtUtil;

/**
 * @author SKumar7
 *
 */
@Controller
public class HomeController {

	private static final String jwtTokenCookieName = "JWT-TOKEN";
	private static final String signingKey = "d652385f5169a19ba3739a0a803396f6e4a5e6f076e3d80f435d6be2324220a8";

	@RequestMapping("/home")
	public String viewHome(HttpServletRequest request, HttpServletResponse httpServletResponse, Model model) {
		String username = JwtUtil.getSubject(request, jwtTokenCookieName, signingKey);
		CookieUtil.create(httpServletResponse, "QA.ASPXFORMSAUTH",
				"8B8FBFA57DA3BA512FF2B4C1CFEBC23E46A8646D847D385F20E794053D81B0AEB5F97A53F1CC6C0A84A94DF9570EC1E82553E135B65B1B2AFA92642585A8CD5A4B2D3115A58C28759A28250CE4F1AEB2236CCF3548E45CEC84C4A4A34FA2D3F1641DE8BFFAA35C198405906C08DEDAA8220497C707EC440540BA893D619AAD1AD2E1FC47BD2047571762BAD53DBBBCBECD6DA266659CF53B74EEF297",
				false, -1, "localhost");
		CookieUtil.create(httpServletResponse, "FishbowlQA", "SessionID=25597843", false, -1, "localhost");
		if (username == null) {
			model.addAttribute("error", "user is not authorised");
			return "redirect:login";
		} else {
			return "appSelect";
		}
	}
}
