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
		UserDetailsResponse userDetailResponse = null;
		logger.debug("getting ir cookie details...");
		try {
			// fishbowl one flow
			String domain = request.getServerName();
			logger.debug("Domain: " + domain);
			if (request.getServerName().contains("one")) {
				String soapUrl = SisenseUtil.getfishbowlOneSoapUrl(domain);
				// String userDetails = AuthUtil.getSsoUserDetails(soapUrl, appId, id,
				// fishFrameSessionId);
			}

			String userAuthStr = CookieUtil.getValue(request, AppConstants.IR_SESSION_ID_COOKIE);
			String eCubeStr = CookieUtil.getValue(request, AppConstants.IR_ECUBE_COOKIE);
			logger.debug("IR Session Cookie: " + userAuthStr);
			logger.debug("\nIR Ecube Cookie: " + eCubeStr);
			if (null != userAuthStr && null != eCubeStr) {
				logger.debug(AppConstants.IR_SESSION_ID_COOKIE + " encrypted cookie details: " + userAuthStr);
				logger.debug(AppConstants.IR_ECUBE_COOKIE + " encrypted cookie details: " + eCubeStr);

				String userDetailsStr = AuthUtil.decrypted(userAuthStr.getBytes());

				logger.debug("decrypted userAuth details: " + userDetailsStr);

				String eCubeNameStr = AuthUtil.decryptCubeCookie(eCubeStr.getBytes());

				logger.debug("decrypted eCube details: " + eCubeNameStr);

				userAuth = AuthUtil.getUserDetails(userDetailsStr);

				logger.debug("getting user id via sisense api for username: " + userAuth.getUserName());

				sisenseUserId = SisenseUtil.getUserIdByUsername(userAuth.getUserName());

				logger.debug("Collected Sisense user id: " + sisenseUserId);
				if (null == sisenseUserId) {
					logger.debug("getting user details by user id");
					userDetailResponse = userService.getUserDetails(userAuth.getUserId());
					logger.debug("adding user in sisense");
					sisenseUserId = SisenseUtil.createUserInSisense(userDetailResponse);
				}
				logger.debug("creating data security for the logged in user");

				DataSecurityPayload securityPayload = null;
				if (userAuth.getClientId().equals("-1")) {
					securityPayload = SisenseUtil.getDataSecurityPayloadForAllMembers(sisenseUserId,
							eCubeNameStr.trim());
				} else {
					BrandRequest brandRequest = new BrandRequest();
					logger.debug("userService getBrand calling to get user Brands");
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

				String token = SisenseUtil.generateToken(AppConstants.SIGNING_KEY, userAuth.getUserName());
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
				logger.debug("redirectURL : " + redirectUrl);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:" + redirectUrl;
	}
}