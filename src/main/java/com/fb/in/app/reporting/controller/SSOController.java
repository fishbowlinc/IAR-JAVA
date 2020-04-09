package com.fb.in.app.reporting.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fb.in.app.reporting.constants.AppConstants;
import com.fb.in.app.reporting.model.auth.DataSecurityPayload;
import com.fb.in.app.reporting.model.auth.UserAuth;
import com.fb.in.app.reporting.request.BrandRequest;
import com.fb.in.app.reporting.response.BrandListResponse;
import com.fb.in.app.reporting.response.UserDetailsResponse;
import com.fb.in.app.reporting.service.UserService;
import com.fb.in.app.reporting.sso.auth.AuthUtil;
import com.fb.in.app.reporting.sso.auth.CookieUtil;
import com.fb.in.app.reporting.sso.auth.SisenseUtil;

@Controller

@RequestMapping("/iar/userauth")
public class SSOController {
	@Autowired
	UserService userService;
	private final static Logger logger = LoggerFactory.getLogger(SSOController.class);

	@RequestMapping("/sso-handler")
	public String processRequest(HttpServletRequest request) throws UnsupportedEncodingException {
		UserAuth userAuth = null;
		String sisenseUserId = null;
		String redirectUrl = null;
		String emailAddress = null;
		String subject = null;
		UserDetailsResponse userDetailResponse = null;
		try {
			String domain = request.getServerName();
			logger.info("domain: " + request.getServerName());

			String irSessionCookieName = AuthUtil.getIRSessionCookieName(domain);
			logger.info("ir session cookie name: " + irSessionCookieName);

			String irECubeCookieName = AuthUtil.getIREcubeCookieName(domain);
			logger.info("ir ecube cookie name: " + irECubeCookieName);

			String userAuthStr = CookieUtil.getValue(request, irSessionCookieName);

			String eCubeStr = CookieUtil.getValue(request, irECubeCookieName);

			if (null != userAuthStr && null != eCubeStr) {
				logger.info(irSessionCookieName + " encrypted cookie details: " + userAuthStr);
				logger.info(irECubeCookieName + " encrypted cookie details: " + eCubeStr);

				String userDetailsStr = AuthUtil.decrypted(userAuthStr.getBytes());

				logger.info("decrypted userAuth details: " + userDetailsStr);

				String eCubeNameStr = AuthUtil.decryptCubeCookie(eCubeStr);

				logger.info("decrypted eCube details: " + eCubeNameStr);

				userAuth = AuthUtil.getUserDetails(userDetailsStr);

				logger.info("getting user id via sisense api for username: " + userAuth.getUserName());

				sisenseUserId = SisenseUtil.getUserIdByUsername(userAuth.getUserName());

				logger.info("Collected Sisense user id: " + sisenseUserId);
				if (sisenseUserId == null) {
					logger.info("getting fishbowl user details by user id");
					userDetailResponse = userService.getUserDetails(userAuth.getUserId());
					emailAddress = userDetailResponse.getEmail();
					logger.info("getting user details by email address: " + emailAddress);
					if (emailAddress != null) {
						subject = emailAddress;
						sisenseUserId = SisenseUtil.getUserIdByEmail(userDetailResponse.getEmail().trim());
					}
					logger.info("found id: " + sisenseUserId);
					if (sisenseUserId == null) {
						logger.info("user doesnt exist in sisense so adding user");
						sisenseUserId = SisenseUtil.createUserInSisense(userDetailResponse);
					}
				}
				logger.info("creating data security for the logged in user");
				DataSecurityPayload securityPayload = null;
				if (userAuth.getClientId().equals("-1")) {
					securityPayload = SisenseUtil.getDataSecurityPayloadForAllMembers(sisenseUserId,
							eCubeNameStr.trim());
				} else {
					BrandRequest brandRequest = new BrandRequest();
					logger.info("userService getBrand calling to get user Brands");
					BrandListResponse response = null;
					try {
						response = userService.getBrand(userAuth.getUserId(), userAuth.getClientId(), brandRequest);
						securityPayload = SisenseUtil.getDataSecurityPayload(response.getBrandList(), sisenseUserId,
								eCubeNameStr.trim());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				SisenseUtil.createDataSecuirtyInSisenseElasticCube(sisenseUserId, securityPayload);
				if (subject == null)
					subject = userAuth.getUserName();
				String token = SisenseUtil.generateToken(AppConstants.SIGNING_KEY, subject);
				// This is the Sisense URL which can handle (decode and process) the JWT token
				redirectUrl = AppConstants.SISENSE_JWT_REDIRECT_URL + token;
				// Which URL the user was initially trying to open
				String returnTo = request.getParameter("return_to");
				if (returnTo != null) {
					redirectUrl += "&return_to=" + URLEncoder.encode(returnTo, "UTF-8");
				}
			} else {
				String soapUrl = SisenseUtil.getSoapUrl(request.getServerName());
				redirectUrl = "https://" + soapUrl + "/Public/Login.aspx?ReturnUrl=%2f";
				logger.info("redirectURL : " + redirectUrl);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:" + redirectUrl;
	}
}