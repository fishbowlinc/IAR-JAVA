package com.fb.in.app.reporting.sso.auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class SisenseUtil {
	private static HttpURLConnection con;

	public static void logoutFromSisense() throws IOException {
		String url = "http://10.200.10.21:8081/api/v1/authentication/logout";
		try {
			URL myurl = new URL(url);
			con = (HttpURLConnection) myurl.openConnection();
			con.setRequestMethod("GET");
			StringBuilder content;
			try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
				String line;
				content = new StringBuilder();
				while ((line = in.readLine()) != null) {
					content.append(line);
					content.append(System.lineSeparator());
				}
			}
			System.out.println(content.toString());
		} finally {
			con.disconnect();
		}
	}

	public static String generateToken(String signingKey, String subject) throws UnsupportedEncodingException {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		byte[] b = signingKey.getBytes("UTF-8");
		Date now = new Date(nowMillis);
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("typ", "JWT");
		headerParams.put("alg", "HS256");
		JwtBuilder builder = Jwts.builder().setSubject(subject).setIssuedAt(now).setAudience("fishbowl")
				.setHeaderParams(headerParams).signWith(signatureAlgorithm, b);

		return builder.compact();
	}
}
