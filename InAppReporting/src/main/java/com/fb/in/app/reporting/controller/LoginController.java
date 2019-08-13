package com.fb.in.app.reporting.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.fb.in.app.reporting.sso.auth.CookieUtil;
import com.fb.in.app.reporting.sso.auth.JwtUtil;

@Controller
public class LoginController {
	private static final String jwtTokenCookieName = "JWT-TOKEN";
	private static final String signingKey = "d652385f5169a19ba3739a0a803396f6e4a5e6f076e3d80f435d6be2324220a8";
	private static final Map<String, String> credentials = new HashMap<>();

	public LoginController() {
		credentials.put("demo", "demo@123");
		credentials.put("sisense@fishbowl.com", "GetMeIn123!");
	}

	@GetMapping("/")
	public String processRequest(HttpServletRequest request, Model model) {
		return "redirect:home";
	}

	@GetMapping("/login")
	public String processLogin(HttpServletRequest request, Model model) {
		return "login";
	}

	@PostMapping("/login")
	public String processLoginRequest(HttpServletRequest request, HttpServletResponse response, Model model)
			throws UnsupportedEncodingException {
		String username = request.getParameter("uname");
		String password = request.getParameter("pass");
		if (username == null || !credentials.containsKey(username) || !credentials.get(username).equals(password)) {
			model.addAttribute("error", "Invalid username or password!");
			return "login";
		} else {
			String token = JwtUtil.generateToken(signingKey, username);
			CookieUtil.create(response, jwtTokenCookieName, token, false, -1, "az-dev-linux-apps1.fishbowlcloud.com");
			return "redirect:home";
		}
	}
}
