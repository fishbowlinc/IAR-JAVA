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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fb.in.app.reporting.constants.AppConstants;
import com.fb.in.app.reporting.model.auth.DataSecurityPayload;
import com.fb.in.app.reporting.model.auth.Share;
import com.fb.in.app.reporting.model.auth.SisenseUser;
import com.fb.in.app.reporting.model.mail.MailPayload;
import com.fb.in.app.reporting.model.mail.Preferences;
import com.fb.in.app.reporting.model.mail.Recipient;
import com.fb.in.app.reporting.model.mail.SisenseMailObject;
import com.fb.in.app.reporting.model.vo.BrandVo;
import com.fb.in.app.reporting.response.UserDetailsResponse;
import com.google.gson.Gson;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class SisenseUtil {
	private final static Logger logger = LoggerFactory.getLogger(SisenseUtil.class);
	private static HttpURLConnection con;

	public static void logoutFromSisense() throws IOException {
		try {
			URL myurl = new URL(AppConstants.SISENSE_LOGOUT_URL);
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
		RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
		try (CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();) {
			HttpPost httpPost = new HttpPost(AppConstants.SISENSE_ACCESS_TOKEN_URL);
			String authEncodedString = encodeValue(AppConstants.ADMIN_USERNAME) + "&"
					+ encodeValue(AppConstants.ADMIN_PASSWORD);
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
			exception.printStackTrace();
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

	public static String getUserIdByEmail(String email) {
		RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
		try (CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();) {
			String authUrl = AppConstants.SISENSE_USER_API_URL + "?email=" + email.trim();
			HttpGet httpGet = new HttpGet(authUrl);
			httpGet.setHeader("Authorization", "Bearer " + AppConstants.SISENSE_API_ACCESS_TOKEN);
			httpGet.setHeader("Accept", "application/json");
			httpGet.setHeader("Content-type", "application/json");
			try (CloseableHttpResponse response = client.execute(httpGet);) {
				if (response.getStatusLine().getStatusCode() == 200) {
					String result = EntityUtils.toString(response.getEntity());
					JSONArray array = new JSONArray(result);
					JSONObject obj = array.getJSONObject(0);
					return obj.get("_id").toString();
				} else {
					logger.info("couldn't find user" + response.getStatusLine());
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info(exception.getMessage());
		}
		return null;
	}

	public static String getUserIdByUsername(String userName) {
		RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
		try (CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();) {
			String authUrl = AppConstants.SISENSE_USER_API_URL + "?userName=" + userName.trim();
			HttpGet httpGet = new HttpGet(authUrl);
			httpGet.setHeader("Authorization", "Bearer " + AppConstants.SISENSE_API_ACCESS_TOKEN);
			httpGet.setHeader("Accept", "application/json");
			httpGet.setHeader("Content-type", "application/json");
			try (CloseableHttpResponse response = client.execute(httpGet);) {
				if (response.getStatusLine().getStatusCode() == 200) {
					String result = EntityUtils.toString(response.getEntity());
					JSONArray array = new JSONArray(result);
					JSONObject obj = array.getJSONObject(0);
					return obj.get("_id").toString();
				} else {
					logger.info("couldn't find user" + response.getStatusLine());
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info(exception.getMessage());
		}
		return null;
	}

	public static String createUserInSisense(UserDetailsResponse userDetailsResponse) {
		RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
		try (CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();) {
			HttpPost httpPost = new HttpPost(AppConstants.SISENSE_USER_API_URL);
			SisenseUser sisenseUser = new SisenseUser();
			sisenseUser.setUserName(userDetailsResponse.getUserName());
			sisenseUser.setFirstName(userDetailsResponse.getFirstName());
			sisenseUser.setLastName(userDetailsResponse.getLastName());
			sisenseUser.setEmail(userDetailsResponse.getEmail());
			sisenseUser.setRoleId(AppConstants.SISENSE_VIEWER_ROLE_ID);
			Gson gson = new Gson();
			StringEntity postingString = new StringEntity(gson.toJson(sisenseUser));
			logger.info("creating user details: " + postingString);
			httpPost.setEntity(postingString);
			httpPost.setHeader("Authorization", "Bearer " + AppConstants.SISENSE_API_ACCESS_TOKEN);
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
			exception.printStackTrace();
			logger.info(exception.getMessage());
		}
		return null;
	}

	public static List<DataSecurityPayload> getDataSecurityPayload(List<BrandVo> brands, String sisenseUserId,
			String eCubeName) {
		List<DataSecurityPayload> dataSecurityPayloads = new ArrayList<>();
		DataSecurityPayload securityPayload = new DataSecurityPayload();
		securityPayload.setServer(AppConstants.SISENSE_DATA_SECURITY_SERVER_URL);
		securityPayload.setElasticube(eCubeName);
		securityPayload.setTable("Dim_Brand");
		securityPayload.setColumn("Brand ID");
		securityPayload.setDatatype("numeric");
		List<Share> shares = new ArrayList<>();
		Share share = new Share();
		share.setParty(sisenseUserId);
		share.setType("user");
		shares.add(share);
		securityPayload.setShares(shares);
		List<String> members = new ArrayList<>();
		brands.forEach(brand -> 
		{
			logger.info("getDataSecurityPayload brand "+ brand.getBrandId());
			members.add(String.valueOf(brand.getBrandId()));}
		);
		securityPayload.setMembers(members);
		dataSecurityPayloads.add(securityPayload);
		return dataSecurityPayloads;
	}

	public static List<DataSecurityPayload> getDataSecurityPayloadForAllMembers(String sisenseUserId,
			String eCubeName) {
		List<DataSecurityPayload> dataSecurityPayloads = new ArrayList<>();
		DataSecurityPayload securityPayload = new DataSecurityPayload();
		securityPayload.setServer(AppConstants.SISENSE_DATA_SECURITY_SERVER_URL);
		securityPayload.setElasticube(eCubeName);
		securityPayload.setTable("Dim_Brand");
		securityPayload.setColumn("Brand ID");
		securityPayload.setDatatype("numeric");
		List<Share> shares = new ArrayList<>();
		Share share = new Share();
		share.setParty(sisenseUserId);
		share.setType("user");
		shares.add(share);
		securityPayload.setShares(shares);
		securityPayload.setAllMembers(true);
		securityPayload.setMembers(new ArrayList<String>());
		dataSecurityPayloads.add(securityPayload);
		return dataSecurityPayloads;
	}

	public static void createDataSecuirtyInSisenseElasticCube(String sisenseUserId,
			List<DataSecurityPayload> dataSecurityPayloads) {
		RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
		try (CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();) {
			HttpPost httpPost = new HttpPost(AppConstants.SISENSE_DATA_SECURITY_URL);
			Gson gson = new Gson();
			StringEntity postingString = new StringEntity(gson.toJson(dataSecurityPayloads));
			httpPost.setEntity(postingString);
			httpPost.setHeader("Authorization", "Bearer " + AppConstants.SISENSE_API_ACCESS_TOKEN);
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
			exception.printStackTrace();
			logger.info(exception.getMessage());
		}
	}

	public static String getFishbownOneSoapUrl(String domain) {
		String soapUrl = null;

		if (domain != null) {
			if (domain.contains("qa")) {
				soapUrl = "oneqa.fishbowl.com/Login";
			} else if (domain.contains("int")) {
				soapUrl = "oneint.fishbowl.com/Login";
			} else {
				soapUrl = "one.fishbowl.com/Login";
			}
		}
		return soapUrl;

	}

	public static String getFishbowlOneSoapUrl(String domain) {
		String soapUrl = null;

		if (domain != null) {
			if (domain.contains("qa") | domain.contains("localhost")) {
				soapUrl = "oneqa.fishbowl.com";
			} else if (domain.contains("staging")) {
				soapUrl = "onestg.fishbowl.com";
			} else {
				soapUrl = "one.fishbowl.com";
			}
		}
		return soapUrl;
	}

	public static String getFishbowlSessionId(HttpServletRequest request) {
		String fbSessionId = null;
		Cookie[] cookies = request.getCookies();
		if (null != cookies && cookies.length > 0) {
			String fbSessionCookie = AuthUtil.getFishFrameSessionEnv(request.getServerName());
			for (Cookie ck : cookies) {
				logger.info("Cookie Name : " + ck.getName());
				if (ck.getName().equalsIgnoreCase(fbSessionCookie)) {
					fbSessionId = ck.getValue().split("=")[1];
					logger.info("fishFrameSessionId : " + fbSessionId);
				}
			}
		}
		return fbSessionId;
	}

	public static List<DataSecurityPayload> getFbOneDataSecurityPayloadForAllMembers(String sisenseUserId,
			List<String> ecubes) {
		List<DataSecurityPayload> dataSecurityPayloads = new ArrayList<>();
		ecubes.forEach(ecube -> {
			DataSecurityPayload securityPayload = new DataSecurityPayload();
			securityPayload.setServer(AppConstants.SISENSE_DATA_SECURITY_SERVER_URL);
			securityPayload.setElasticube(ecube);
			securityPayload.setTable("Dim_Brand");
			securityPayload.setColumn("Brand ID");
			securityPayload.setDatatype("numeric");
			List<Share> shares = new ArrayList<>();
			Share share = new Share();
			share.setParty(sisenseUserId);
			share.setType("user");
			shares.add(share);
			securityPayload.setShares(shares);
			securityPayload.setAllMembers(true);
			securityPayload.setMembers(new ArrayList<String>());
			dataSecurityPayloads.add(securityPayload);
		});
		return dataSecurityPayloads;
	}

	public static List<DataSecurityPayload> getFbOneDataSecurityPayload(List<BrandVo> brands, String sisenseUserId,
			List<String> ecubes) {
		List<DataSecurityPayload> dataSecurityPayloads = new ArrayList<>();
		ecubes.forEach(ecube -> {
			DataSecurityPayload securityPayload = new DataSecurityPayload();
			securityPayload.setServer(AppConstants.SISENSE_DATA_SECURITY_SERVER_URL);
			securityPayload.setElasticube(ecube);
			securityPayload.setTable("Dim_Brand");
			securityPayload.setColumn("Brand ID");
			securityPayload.setDatatype("numeric");
			List<Share> shares = new ArrayList<>();
			Share share = new Share();
			share.setParty(sisenseUserId);
			share.setType("user");
			shares.add(share);
			securityPayload.setShares(shares);
			List<String> members = new ArrayList<>();
			brands.forEach(brand -> {
				logger.info("getFbOneDataSecurityPayload brand = "+brand.getBrandId());
				members.add(String.valueOf(brand.getBrandId()));
			});
			securityPayload.setMembers(members);
			dataSecurityPayloads.add(securityPayload);
		});
		return dataSecurityPayloads;
	}

	public static void sendMailToSisenseUser(MailPayload mailPayload) {
		RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
		try (CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();) {
			HttpPost httpPost = new HttpPost(AppConstants.SISENSE_SEND_MAIL_URL);

			Gson gson = new Gson();
			StringEntity postingString = new StringEntity(gson.toJson(mailPayload));
			httpPost.setEntity(postingString);
			httpPost.setHeader("Authorization", "Bearer " + AppConstants.SISENSE_API_ACCESS_TOKEN);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			try (CloseableHttpResponse response = client.execute(httpPost);) {
				if (response.getStatusLine().getStatusCode() == 200) {
					logger.info("mail sent successfully");
				} else {
					logger.info("couldn't send mail");
					logger.info("response status code: " + response.getStatusLine().getReasonPhrase());
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info(exception.getMessage());
		}

	}

	public static MailPayload generateMailPayload(SisenseMailObject mailObject) {
		MailPayload mailPayload = new MailPayload();
		mailPayload.setAssetId(mailObject.getDashboardId());
		mailPayload.setAssetType("dashboard");
		Recipient recipient = new Recipient();
		recipient.setRecipient(mailObject.getUserId());
		recipient.setType("user");
		List<Recipient> recipients = new ArrayList<>();
		recipients.add(recipient);
		mailPayload.setRequestTimeout(0);
		mailPayload.setRecipients(recipients);
		Preferences preferences = new Preferences();
		preferences.setInline(true);
		mailPayload.setPreferences(preferences);
		return mailPayload;
	}

}
