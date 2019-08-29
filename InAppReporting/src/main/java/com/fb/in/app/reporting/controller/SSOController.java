package com.fb.in.app.reporting.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fb.in.app.reporting.model.auth.DataSecurityPayload;
import com.fb.in.app.reporting.model.auth.UserAuth;
import com.fb.in.app.reporting.model.vo.BrandVo;
import com.fb.in.app.reporting.request.BrandRequest;
import com.fb.in.app.reporting.request.PagingRequest;
import com.fb.in.app.reporting.response.BrandListResponse;
import com.fb.in.app.reporting.response.UserDetailsResponse;
import com.fb.in.app.reporting.service.UserService;
import com.fb.in.app.reporting.sso.auth.AuthUtil;
import com.fb.in.app.reporting.sso.auth.CookieUtil;
import com.fb.in.app.reporting.sso.auth.SisenseUtil;

@Controller
public class SSOController {
	@Autowired
	UserService userService;
	private final static Logger logger = LoggerFactory.getLogger(SSOController.class);
	private static final String irSessionCookieName = "IR_SessionId";
	private static final String brandCookieName = "BrandID";
	private static final String signingKey = "d652385f5169a19ba3739a0a803396f6e4a5e6f076e3d80f435d6be2324220a8";

	@RequestMapping("/sso-handler")
	public String processRequest(HttpServletRequest request) throws UnsupportedEncodingException {
		UserAuth userAuth = null;
		String sisenseUserId = null;
		String redirectUrl = null;
		logger.info("getting ir cookie details...");
		try {
			String userAuthStr = CookieUtil.getValue(request, irSessionCookieName);
			if (null != userAuthStr) {
				logger.info(irSessionCookieName + " encrypted cookie details: " + userAuthStr);
				logger.info("decrypted cookie value");
				String userDetailsStr = AuthUtil.decrypted(userAuthStr.getBytes());
				logger.info("decrypted userAuth details: " + userDetailsStr);
				userAuth = AuthUtil.getUserDetails(userDetailsStr);
				logger.info("getting user id via sisense api for username: " + userAuth.getUserName());
				sisenseUserId = SisenseUtil.getUserIdByUsername(userAuth.getUserName());
				logger.info("Collected Sisense user id: " + sisenseUserId);
				if (null == sisenseUserId) {
					logger.info("getting user details by user id");
					UserDetailsResponse response = userService.getUserDetails(userAuth.getUserId());
					logger.info("adding user in sisense");
					sisenseUserId = SisenseUtil.createUserInSisense(response);
				}
				logger.info("creating data security for the logged in user");
				String brandId = CookieUtil.getValue(request, brandCookieName);
				if (null == brandId) {
					BrandRequest brandRequest = new BrandRequest();
					PagingRequest paging = new PagingRequest();
					paging.setPageNo(1);
					paging.setPageSize(10);
					brandRequest.setPaging(paging);
					logger.info("userService  getBrand calling to get user Brands");
					BrandListResponse response = null;
					try {
						response = userService.getBrand(userAuth.getUserId(), userAuth.getClientId(), brandRequest);
						DataSecurityPayload securityPayload = SisenseUtil
								.getDataSecurityPayload(response.getBrandList(), sisenseUserId);
						SisenseUtil.createDataSecuirtyInSisenseElasticCube(sisenseUserId, securityPayload);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					BrandVo brandVo = new BrandVo();
					brandVo.setBrandId(Integer.valueOf(brandId));
					List<BrandVo> brands = new ArrayList<BrandVo>();
					brands.add(brandVo);
					DataSecurityPayload securityPayload = SisenseUtil.getDataSecurityPayload(brands, sisenseUserId);
					SisenseUtil.createDataSecuirtyInSisenseElasticCube(sisenseUserId, securityPayload);
				}
				String token = SisenseUtil.generateToken(signingKey, userAuth.getUserName());
				// This is the Sisense URL which can handle (decode and process) the JWT token
				redirectUrl = "http://10.200.10.21:8081/jwt?jwt=" + token;
				// Which URL the user was initially trying to open
				String returnTo = request.getParameter("return_to");
				if (returnTo != null) {
					redirectUrl += "&return_to=" + URLEncoder.encode(returnTo, "UTF-8");
				}
			} else {
				String soapUrl = "loginqa.fishbowl.com";
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