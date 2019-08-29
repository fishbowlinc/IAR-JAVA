package com.fb.in.app.reporting.sso.auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fb.in.app.reporting.model.auth.DataSecurityPayload;
import com.fb.in.app.reporting.model.auth.Share;
import com.fb.in.app.reporting.model.auth.SisenseUser;
import com.fb.in.app.reporting.model.vo.BrandVo;
import com.fb.in.app.reporting.response.UserDetailsResponse;
import com.google.gson.Gson;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class SisenseUtil {
	private final static Logger logger = LoggerFactory.getLogger(SisenseUtil.class);

	private static final String sisenseApiAccessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjoiNWQwYmU2ZTA0ZDI5OGIxMTgwZTZiMDE3IiwiYXBpU2VjcmV0IjoiNmZmN2UwYjAtNjRlMi0yNDVlLTUxNzgtZGJmNTAyNGE4NjdkIiwiaWF0IjoxNTY2ODkxNzU0fQ.C_p3TwO7hT0TUwNIJeLwnucN_9QvMftkLzKycptmpmk";
	private static HttpURLConnection con;
	private static final String username = "sisense@fishbowl.com";
	private static final String password = "GetMeIn123!";

	public static void logoutFromSisense() throws IOException {
		String url = "http://10.200.10.21:8081/api/auth/logout";
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
				.setId(UUID.randomUUID().toString()).setHeaderParams(headerParams).signWith(signatureAlgorithm, b);

		return builder.compact();
	}

	public static String getApiAccessTokenFromSisense(String username, String password) {
		try (CloseableHttpClient client = HttpClients.createDefault();) {
			String authUrl = "http://10.200.10.21:8081/api/v1/authentication/login";
			HttpPost httpPost = new HttpPost(authUrl);
			String authEncodedString = encodeValue(username) + "&" + encodeValue(password);
			StringEntity entity = new StringEntity(authEncodedString);
			httpPost.setEntity(entity);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
			try (CloseableHttpResponse response = client.execute(httpPost);) {
				if (response.getStatusLine().getStatusCode() == 200) {
					String result = EntityUtils.toString(response.getEntity());
					JSONObject obj = new JSONObject(result);
					if (obj.getBoolean("success")) {
						return obj.getString("access_token");
					}
				}
			}
		} catch (Exception exception) {
			logger.info(exception.getMessage());
		}
		return null;
	}

	private static String encodeValue(String value) {
		try {
			return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getUserIdByUsername(String userName) {
		try (CloseableHttpClient client = HttpClients.createDefault();) {
			String authUrl = "http://10.200.10.21:8081/api/users/" + userName.trim();
			HttpGet httpGet = new HttpGet(authUrl);
			httpGet.setHeader("Authorization", "Bearer " + sisenseApiAccessToken);
			httpGet.setHeader("Accept", "application/json");
			httpGet.setHeader("Content-type", "application/json");
			try (CloseableHttpResponse response = client.execute(httpGet);) {
				if (response.getStatusLine().getStatusCode() == 200) {
					String result = EntityUtils.toString(response.getEntity());
					JSONObject obj = new JSONObject(result);
					return obj.get("_id").toString();
				} else {
					logger.info("couldn't find user" + response.getStatusLine());
				}
			}
		} catch (Exception exception) {
			logger.info(exception.getMessage());
		}
		return null;
	}

	public static String createUserInSisense(UserDetailsResponse userDetailsResponse) {
		try (CloseableHttpClient client = HttpClients.createDefault();) {
			String authUrl = "http://10.200.10.21:8081/api/v1/users";
			HttpPost httpPost = new HttpPost(authUrl);
			SisenseUser sisenseUser = new SisenseUser();
			sisenseUser.setUserName(userDetailsResponse.getUserName());
			sisenseUser.setFirstName(userDetailsResponse.getFirstName());
			sisenseUser.setLastName(userDetailsResponse.getLastName());
			sisenseUser.setEmail(userDetailsResponse.getEmail());
			sisenseUser.setRoleId("5cda4d81b8238941605e533e");
			Gson gson = new Gson();
			StringEntity postingString = new StringEntity(gson.toJson(sisenseUser));
			httpPost.setEntity(postingString);
			httpPost.setHeader("Authorization", "Bearer " + sisenseApiAccessToken);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			try (CloseableHttpResponse response = client.execute(httpPost);) {
				if (response.getStatusLine().getStatusCode() == 201) {
					String result = EntityUtils.toString(response.getEntity());
					JSONObject obj = new JSONObject(result);
					logger.info("user created in sisese with user_id: " + obj.getString("_id"));
					return obj.getString("_id");
				} else {
					logger.info("couldn't create user...");
					logger.info(response.getStatusLine().getReasonPhrase());
				}
			}
		} catch (Exception exception) {
			logger.info(exception.getMessage());
		}
		return null;
	}

	public static DataSecurityPayload getDataSecurityPayload(List<BrandVo> brands, String sisenseUserId) {
		DataSecurityPayload securityPayload = new DataSecurityPayload();
		securityPayload.setServer("LocalHost");
		securityPayload.setElasticube("Sample ECommerce");
		securityPayload.setTable("Brand");
		securityPayload.setColumn("Brand ID");
		securityPayload.setDatatype("numeric");
		List<Share> shares = new ArrayList<>();
		Share share = new Share();
		share.setParty(sisenseUserId);
		share.setType("user");
		shares.add(share);
		securityPayload.setShares(shares);
		List<String> members = new ArrayList<>();
		brands.forEach(brand -> members.add(String.valueOf(brand.getBrandId())));
		securityPayload.setMembers(members);
		return securityPayload;

	}

	public static void createDataSecuirtyInSisenseElasticCube(String sisenseUserId,
			DataSecurityPayload securityPayload) {
		try (CloseableHttpClient client = HttpClients.createDefault();) {
			String authUrl = "http://10.200.10.21:8081/api/elasticubes/datasecurity";
			HttpPost httpPost = new HttpPost(authUrl);
			Gson gson = new Gson();
			List<DataSecurityPayload>dataSecurityPayloads=new ArrayList<>();
			dataSecurityPayloads.add(securityPayload);
			logger.info("data security payload: " + gson.toJson(dataSecurityPayloads));
			StringEntity postingString = new StringEntity(gson.toJson(dataSecurityPayloads));
			httpPost.setEntity(postingString);
			httpPost.setHeader("Authorization", "Bearer " + sisenseApiAccessToken);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			try (CloseableHttpResponse response = client.execute(httpPost);) {
				if (response.getStatusLine().getStatusCode() == 200) {
					logger.info("data secuirty created successfully...");
				} else {
					logger.info("data secuirty couldn't created...");
					logger.info("response status code: " + response.getStatusLine().getReasonPhrase());
				}
			}
		} catch (Exception exception) {
			logger.info(exception.getMessage());
		}
	}

}
