package com.fb.in.app.reporting.sso.auth;

import java.io.IOException;
import java.net.URLDecoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.BindingProvider;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fb.in.app.reporting.constants.AppConstants;
import com.fb.in.app.reporting.constants.CookieConstants;
import com.fb.in.app.reporting.generated.FishbowlSSO;
import com.fb.in.app.reporting.generated.FishbowlSSOSoap;
import com.fb.in.app.reporting.model.auth.UserAuth;
import com.fb.in.app.reporting.model.vo.BrandVo;
import com.google.gson.Gson;

import fb.pricingAnalytics.utils.AuthUtils;

public class AuthUtil {
	private static Logger logger = LoggerFactory.getLogger(AuthUtil.class);

	public static HttpServletResponse deleteCookies(HttpServletRequest request, HttpServletResponse response,
			Cookie[] cookieList) {
		String domain = request.getServerName();
		
		String aSPXFORMSAUTH= getaSPXFORMSAUTHString(domain);;
		for (Cookie cookie : cookieList) {
			String cookieName = cookie.getName();
			logger.info("Cookie domain name: " + domain);
			
			if(domain.contains(AppConstants.APP_ENVIRONMENT_QA)){
				if(cookie.getName().equals(CookieConstants.FISHBOWL_FORM_AUTH_QA_COOKIE) 
						|| cookie.getName().equals(CookieConstants.FISHBOWL_QA_COOKIE)
						|| cookie.getName().equals(CookieConstants.IR_QA_SESSION_ID_COOKIE)
						||cookie.getName().equals(CookieConstants.IR_QA_ECUBE_COOKIE)
				)
				{

					cookie.setDomain(AppConstants.FISHBOWL_DOMAIN);
					cookie.setPath("/");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					logger.info("Deleting cookie: " + cookieName);
				}
				
			}else if (domain.contains(AppConstants.APP_ENVIRONMENT_STG)){
				if(
				cookie.getName().equals(CookieConstants.FISHBOWL_FORM_AUTH_STG_COOKIE) 
						|| cookie.getName().equals(CookieConstants.FISHBOWL_STG_COOKIE)
						|| cookie.getName().equals(CookieConstants.IR_STG_SESSION_ID_COOKIE)
						||cookie.getName().equals(CookieConstants.IR_STG_ECUBE_ID_COOKIE)
				)
				{
					cookie.setDomain(AppConstants.FISHBOWL_DOMAIN);
					cookie.setPath("/");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					logger.info("Deleting cookie: " + cookieName);
				}
			
				
			}else {
				if(
						cookie.getName().equals(CookieConstants.FISHBOWL_FORM_AUTH_PROD_COOKIE) 
								|| cookie.getName().equals(CookieConstants.FISHBOWL_PROD_COOKIE)
								|| cookie.getName().equals(CookieConstants.IR_PROD_SESSION_ID_COOKIE)
								||cookie.getName().equals(CookieConstants.IR_PROD_ECUBE_COOKIE)
					)
						{
							cookie.setDomain(AppConstants.FISHBOWL_DOMAIN);
							cookie.setPath("/");
							cookie.setMaxAge(0);
							response.addCookie(cookie);
							logger.info("Deleting cookie: " + cookieName);
						}
					
			}
			
			
		
		}
		return response;
	}

	public static String getIREcubeCookieName(String domain) {
		if (domain.contains(AppConstants.APP_ENVIRONMENT_QA))
			return CookieConstants.IR_QA_ECUBE_COOKIE;
		else if (domain.contains(AppConstants.APP_ENVIRONMENT_STG))
			return CookieConstants.IR_STG_ECUBE_ID_COOKIE;
		else
			return CookieConstants.IR_PROD_ECUBE_COOKIE;

	}

	public static HttpServletResponse getJsonFromObjectWithResponse(HttpServletResponse response, Object object,
			int responsecode) throws IOException {
		response.setContentType("application/json");
		response.setStatus(responsecode);
		response.getWriter().write(getJsonFromObject(object));
		return response;
	}

	public static String getJsonFromObject(Object object) {
		String json = null;
		Gson gson = new Gson();
		try {
			if (object != null) {
				json = gson.toJson(object);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e.fillInStackTrace());
		}
		return json;
	}

	public static String getFbOneLoginUrl(String domain) {
		if (domain.contains(AppConstants.APP_ENVIRONMENT_QA)){
			return getFBOneQaLoginUrl(domain);
		}
		else if (domain.contains(AppConstants.APP_ENVIRONMENT_INT))
			return AppConstants.FISHBOWL_ONE_INT_LOGIN_URL;
		else{
			return getFBOneProdLoginUrl(domain);
		}
			

	}

	public static String getEnterprizeLoginUrl(String domain) {
		String loginUrl = null;
		if (domain.contains(AppConstants.APP_ENVIRONMENT_QA)){
			loginUrl = AppConstants.ENTERPRIZE_QA_LOGIN_URL;
		}
		else if (domain.contains(AppConstants.APP_ENVIRONMENT_STG))
			loginUrl = AppConstants.ENTERPRIZE_STG_LOGIN_URL;
		else{
			loginUrl =  AppConstants.ENTERPRIZE_PRD_LOGIN_URL;
		}
			

		if (null != loginUrl)
			return loginUrl + AppConstants.ENTERPRIZE_LOGIN_URL_EXTENSION;
		else
			return loginUrl;
	}

	public static String getParentAppUrl(String domain) {
		if (domain != null) {
			if (isFishbowlOneDomain(domain))
				return getFbOneLoginUrl(domain);
			else
				return getEnterprizeLoginUrl(domain);
		} else
			return null;
	}

	public static boolean isFishbowlCookiesPresent(Cookie[] cookieList, String domain) {
		boolean isaSPXFORMSAUTHCookiesPresent = false;
		boolean isfishFrameSessionEnvCookiesPresent = false;
		if (domain != null && cookieList != null) {
			String fishFrameSessionEnv = getFishFrameSessionEnv(domain);
			String aSPXFORMSAUTH = getaSPXFORMSAUTHString(domain);
			logger.info("domain " + domain);
			logger.info("aSPXFORMSAUTH " + aSPXFORMSAUTH);
			logger.info("fishFrameSessionEnv" + fishFrameSessionEnv);
			for (Cookie ck : cookieList) {
				logger.info("Cookie Name : " + ck.getName());
				logger.info("Cookie Domain : " + ck.getDomain());
				if (aSPXFORMSAUTH.equalsIgnoreCase(ck.getName())) {
					logger.info("AspxCookie is Present and hence proceeding");
					isaSPXFORMSAUTHCookiesPresent = true;
				}
				if (fishFrameSessionEnv.equalsIgnoreCase(ck.getName())) {
					logger.info("fishFrameSessionId : " + ck.getValue());
					isfishFrameSessionEnvCookiesPresent = true;
				}
			}
		}
		if (isfishFrameSessionEnvCookiesPresent && isaSPXFORMSAUTHCookiesPresent)
			return true;
		else
			return false;
	}

	public static boolean isBrandIDCookiesPresent(Cookie[] cookieList, String domain) {
		logger.info("isBrandIDCookiesPresent method start");
		if (domain != null && cookieList != null) {

			for (Cookie ck : cookieList) {
				if ("BrandID".equalsIgnoreCase(ck.getName())) {
					logger.info("BrandID is Present and hence proceeding");
					return true;
				}
			}
		}
		return false;
	}

	public static String encrypted(String t) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException {
		byte[] dataToSend = t.getBytes();
		byte[] key = new byte[16];
		Cipher c = Cipher.getInstance("AES");
		SecretKeySpec k = new SecretKeySpec(key, "AES");
		c.init(Cipher.ENCRYPT_MODE, k);
		byte[] encryptedData = c.doFinal(dataToSend);
		return new String(Base64.encodeBase64(encryptedData));
	}

	public static String decrypted(byte[] kr) throws IllegalBlockSizeException, BadPaddingException,
			InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		byte[] key = new byte[16];
		SecretKeySpec k = new SecretKeySpec(key, "AES");
		byte[] encryptedData = Base64.decodeBase64(kr);
		Cipher c2 = Cipher.getInstance("AES");
		c2.init(Cipher.DECRYPT_MODE, k);
		byte[] data = c2.doFinal(encryptedData);
		return new String(data);
	}

	public static String decryptCubeCookie(String eCube) {
		String urlDecodedValue = null;
		String encryptedValue = null;
		try {
			urlDecodedValue = URLDecoder.decode(eCube, "utf8");
			encryptedValue = new String(java.util.Base64.getDecoder().decode(urlDecodedValue));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return encryptedValue;
	}

	public static UserAuth getUserDetails(String userDetailsStr) {
		UserAuth userAuth = new UserAuth();
		logger.info("userDetailsStr : " + userDetailsStr);
		String[] userDetails = userDetailsStr.split("\\|");
		logger.info("userDetails[0] : " + userDetails[0]);
		logger.info("userDetails[1] : " + userDetails[1]);
		logger.info("userDetails[2] : " + userDetails[2]);
		logger.info("userDetails[3] : " + userDetails[3]);
		logger.info("userDetails[4] : " + userDetails[4]);
		logger.info("userDetails[5] : " + userDetails[5]);
		logger.info("userDetails[5][0] : " + userDetails[5].split(",")[0]);
		logger.info("userDetails[5][1] : " + userDetails[5].split(",")[1]);
		userAuth.setUserName(userDetails[1]);
		userAuth.setAccessTokenIssued(userDetails[2]);
		userAuth.setAccessTokenExpires(userDetails[3]);
		userAuth.setPersistant(Boolean.valueOf(userDetails[4]));
		userAuth.setUserId(userDetails[5].split(",")[0]);
		userAuth.setClientId(userDetails[5].split(",")[1]);
		return userAuth;
	}

	public static UserAuth getUserAuthData(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		UserAuth userAuth = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				logger.info("Cookie Name : " + cookie.getName());
				logger.info("Cookie Domain : " + cookie.getDomain());
				if (cookie.getName().contains("IR_SessionId")) {
					String decryptedKey = null;
					try {
						decryptedKey = AuthUtil.decrypted(cookie.getValue().getBytes());
						userAuth = getUserDetails(decryptedKey);
						logger.info("------decryptedKey--------" + decryptedKey);
					} catch (InvalidKeyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalBlockSizeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (BadPaddingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchPaddingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					logger.info("------decryptedKey--------         NULL");

				}
				if (cookie.getName().contains("BrandID")) {
					logger.info("Cookie Name : " + cookie.getName());
					logger.info("Cookie Domain : " + cookie.getDomain());
					logger.info("Cookie Value : " + cookie.getValue());

					userAuth = updateBrandId(userAuth, cookie.getValue());

					logger.info("BrandID Value : " + userAuth.getBrandId());

				}

			}
		}
		logger.info("BrandID Value : " + userAuth.getBrandId());
		return userAuth;

	}

	public static UserAuth updateBrandId(UserAuth userAuth, String brandID) {
		logger.info(" updateBrandId BrandID Value : " + brandID);
		userAuth.setBrandId(brandID);
		return userAuth;

	}

	public static String getFishFrameSessionEnv(String domain) {
		String fishFrameSessionEnv = null;
		if (domain != null) {
			if (domain.contains(AppConstants.APP_ENVIRONMENT_QA) || domain.contains(AppConstants.APP_ENVIRONMENT_LOCAL))
				return CookieConstants.FISHBOWL_QA_COOKIE;
			else if (domain.contains(AppConstants.APP_ENVIRONMENT_STG))
				return CookieConstants.FISHBOWL_STG_COOKIE;
			else
				return CookieConstants.FISHBOWL_PROD_COOKIE;
		}
		return fishFrameSessionEnv;

	}

	public static String getIRDomain(String domain) {
		String redirectServerName = null;
		if (domain != null) {
			if (domain.contains(AppConstants.APP_ENVIRONMENT_QA)
					|| domain.equalsIgnoreCase(AppConstants.APP_ENVIRONMENT_LOCAL))
				return AppConstants.IN_APP_REPORTING_DOMAIN_QA;
			else if (domain.contains(AppConstants.APP_ENVIRONMENT_STG))
				return AppConstants.IN_APP_REPORTING_DOMAIN_STG;
			else
				return AppConstants.IN_APP_REPORTING_DOMAIN_PRD;
		}
		return redirectServerName;

	}

	public static String getSoapUrl(String domain) {
		logger.info("getSoapUrl domain= "+domain);
		String soapUrl = null;
		if (domain != null) {
			if (domain.contains(AppConstants.APP_ENVIRONMENT_QA) || domain.contains(AppConstants.APP_ENVIRONMENT_LOCAL)){
				return AppConstants.ENTERPRIZE_QA_LOGIN_URL;
			}
			else if (domain.contains(AppConstants.APP_ENVIRONMENT_STG))
				return AppConstants.ENTERPRIZE_STG_LOGIN_URL;
			else{
				return  AppConstants.ENTERPRIZE_PRD_LOGIN_URL;
			}
				
		}
		return soapUrl;

	}

	public static String getaSPXFORMSAUTHString(String domain) {
		if (domain.contains(AppConstants.APP_ENVIRONMENT_QA) || domain.contains(AppConstants.APP_ENVIRONMENT_LOCAL))
			return CookieConstants.FISHBOWL_FORM_AUTH_QA_COOKIE;
		else if (domain.contains(AppConstants.APP_ENVIRONMENT_STG))
			return CookieConstants.FISHBOWL_FORM_AUTH_STG_COOKIE;
		else
			return CookieConstants.FISHBOWL_FORM_AUTH_PROD_COOKIE;

	}

	public static Cookie getBrandIdCookies(String brandList, String serverName) {
		Cookie brandCookie = new Cookie(CookieConstants.IR_BRAND_LIST_COOKIE, brandList);
		brandCookie.setDomain(AuthUtil.getIRDomain(serverName));
		brandCookie.setPath("/");
		return brandCookie;

	}

	public static String encryptedBrandDetials(String str) {
		String encryptedData = java.util.Base64.getEncoder().encodeToString(str.getBytes());
		return encryptedData;
	}

	public static String getJsonFromObjectList(BrandVo brandVo) {
		Gson gson = new Gson();
		// convert your list to json
		String jsonBrandList = gson.toJson(brandVo);
		return jsonBrandList;
	}

	public static String getIRSessionCookieName(String domain) {
		if (domain.contains(AppConstants.APP_ENVIRONMENT_QA))
			return CookieConstants.IR_QA_SESSION_ID_COOKIE;
		else if (domain.contains(AppConstants.APP_ENVIRONMENT_STG))
			return CookieConstants.IR_STG_SESSION_ID_COOKIE;
		else
			return CookieConstants.IR_PROD_SESSION_ID_COOKIE;

	}

	public static Cookie getIRSessionCookie(String domain, String encryptedStr) {
		String cookieName = AuthUtil.getIRSessionCookieName(domain);
		Cookie cookie = new Cookie(cookieName, encryptedStr);
		cookie.setDomain(AppConstants.FISHBOWL_DOMAIN);
		cookie.setMaxAge(getIRSessionCookieAge(CookieConstants.IN_APP_REPORTING_SESSION_COOKIE_AGE));
		cookie.setPath("/");
		cookie.setSecure(true);
		return cookie;
	}

	private static int getIRSessionCookieAge(int hours) {
		return hours * 60 * 60;
	}

	public static String getSsoUserDetails(String soapUrl, String aspxFormsAuth) {
		String userDetails = null;
		FishbowlSSO fishbowlSSO = new FishbowlSSO();
		FishbowlSSOSoap port = fishbowlSSO.getPort(FishbowlSSOSoap.class);
		BindingProvider provider = (BindingProvider) port;

		provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				soapUrl + AppConstants.FISHBOWL_SSO_URL_EXTENSION);
		String userDetailsStr = port.getTicketedUserName(null, aspxFormsAuth);
		logger.info("GetTicketedUserName Response : " + userDetailsStr);
		userDetails = StringUtils.substringBetween(userDetailsStr, ">", "<");
		logger.info("User Details : " + userDetails);
		return userDetails;
	}
	
	public static boolean isFishbowlOneDomain(String domain){
		boolean isDomainContains =false;
		try {
			logger.info("isFishbowlOneDomain  domain = "+domain);
			 isDomainContains =   domain.contains(AppConstants.FISHBOWL_ONE_PRODUCT_NAME) || domain.contains(AppConstants.FISHBOWL_ENGAGE_PRODUCT_NAME) ;
			logger.info("isFishbowlOneDomain  isDomainContains = "+isDomainContains);
		} catch (Exception e) {
			
		}
		return isDomainContains;
	}
	public static String getFBOneQaLoginUrl(String domain){
		if(domain.indexOf(AppConstants.FISHBOWL_ONE_PRODUCT_NAME)>-1)
			return AppConstants.FISHBOWL_ONE_QA_LOGIN_URL;
		else
			return AppConstants.FISHBOWL_ONE_QA_ENGAGE_LOGIN_URL;
		
	}
	
	
	public static String getFBOneProdLoginUrl(String domain){
		if(domain.indexOf(AppConstants.FISHBOWL_ONE_PRODUCT_NAME)>-1)
			return AppConstants.FISHBOWL_ONE_PRD_LOGIN_URL;
		else
			return AppConstants.FISHBOWL_ONE_PRD_ENGAGE_LOGIN_URL;
		
	}
	
}

/*
 * String responseString = port.getAuthTicket(appId, id, new
 * BigDecimal(fishFrameSessionId)); logger.info("GetAuthTicket Response : " +
 * responseString); if (responseString != null) { String tokenString =
 * StringUtils.substringBetween(responseString, ">", "<");
 * logger.info("Auth Token : " + tokenString); if (tokenString != null &&
 * !StringUtils.isEmpty(tokenString)) { String userDetailsStr =
 * port.getTicketedUserName(null, tokenString);
 * logger.info("GetTicketedUserName Response : " + userDetailsStr); userDetails
 * = StringUtils.substringBetween(userDetailsStr, ">", "<");
 * logger.info("User Details : " + userDetails); return userDetails; } } return
 * userDetails; }
 */
