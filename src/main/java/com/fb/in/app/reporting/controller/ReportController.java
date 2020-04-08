/**
 * 
 */
package com.fb.in.app.reporting.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fb.in.app.reporting.model.auth.UserAuth;
import com.fb.in.app.reporting.request.BrandRequest;
import com.fb.in.app.reporting.response.BrandListResponse;
import com.fb.in.app.reporting.service.UserService;
import com.fb.in.app.reporting.sso.auth.AuthUtil;

/**
 * @author SKumar7
 *
 */
@Controller
@RequestMapping("/iar/ui")
public class ReportController {
	@Autowired
	UserService userService;

	private final static Logger logger = LoggerFactory.getLogger(ReportController.class);
	private static final String fbLoginCookieName = "ASPXFORMSAUTH";

	@RequestMapping("/report")
	public String getReport(@RequestParam(name = "Id", required = false) String ID,
			@RequestParam(name = "ID", required = false) String Id,
			@RequestParam(name = "ReturnUrl", required = false) String returnUrl,
			@RequestParam(name = "SiteId", required = false) String SiteId, @RequestParam(name = "bid") int bid,
			@RequestParam(name = "SessionId", required = false) String sessionId, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

		final String tileId = ID == null ? Id : ID;
		logger.debug("Tile ID : " + tileId);
		logger.debug("SiteId : " + SiteId);
		logger.debug("bid : " + bid);

		boolean isAspxCookiePresent = false;
		// Need to fetch from properties file
		int appId = 1000;

		String fishFrameSessionEnv = null;
		String fishFrameSessionId = null;
		String irDomain = null;
		String redirectServerName = null;
		String soapUrl = null;
		String redirectURL = null;

		Cookie[] cookies = request.getCookies();
		String domain = request.getServerName();
		logger.debug("domain : " + domain);
		fishFrameSessionEnv = AuthUtil.getFishFrameSessionEnv(domain);
	//	irDomain = AuthUtil.getDomain(domain);
		redirectServerName = AuthUtil.getRedirectServerName(domain);
		soapUrl = AuthUtil.getSoapUrl(domain);

		logger.debug("fishFrameSessionEnv : " + fishFrameSessionEnv);
		logger.debug("redirectServerName : " + redirectServerName);
		logger.debug("soapUrl : " + soapUrl);
		logger.debug("domain : " + domain);

		if (tileId == null || tileId.isEmpty()) {
			redirectURL = "https://" + soapUrl + "/SSO/Navigator/InitializeTargetApp?bid=34&ReturnUrl=/report";
			if (SiteId != null && SiteId.trim().length() > 0) {
				redirectURL = redirectURL + "&SiteId=" + SiteId;
			}
			logger.debug("redirectURL : " + redirectURL);
			return "redirect:" + redirectURL;
		}
		if (cookies != null) {
			for (Cookie ck : cookies) {
				logger.debug("Cookie Name : " + ck.getName());
				logger.debug("Cookie Domain : " + ck.getDomain());
				if (ck.getName().contains(fbLoginCookieName)) {
					logger.debug("AspxCookie is Present and hence proceeding");
					isAspxCookiePresent = true;
				}
				if (fishFrameSessionEnv.equalsIgnoreCase(ck.getName())) {
					fishFrameSessionId = ck.getValue().split("=")[1];
					logger.debug("fishFrameSessionId : " + fishFrameSessionId);
				}
			}
			if (!isAspxCookiePresent) {
				logger.debug("AspxCookie is not present and hence redirecting to login page");
				redirectURL = "https://" + soapUrl + "/Public/Login.aspx?ReturnUrl=%2f";
				logger.debug("redirectURL : " + redirectURL);
				return "redirect:" + redirectURL;
			}
		} else {
			logger.debug("There is no Cookie. Hence redirecting to login page");
			redirectURL = "https://" + soapUrl + "/Public/Login.aspx?ReturnUrl=%2f";
			logger.debug("redirectURL : " + redirectURL);
			return "redirect:" + redirectURL;
		}

		logger.debug("AppId : " + appId);
		logger.debug("Session Value : " + Id);
		logger.debug("Fish Frame SessionId : " + fishFrameSessionId);

		String userDetails = AuthUtil.getSsoUserDetails(soapUrl, appId, Id, fishFrameSessionId);
		if (null != userDetails) {
			String encryptedStr = AuthUtil.encrypted(userDetails);
			logger.debug("Encrypted User Details  : " + encryptedStr);
			Cookie cookie = AuthUtil.getIRSessionCookie(irDomain, encryptedStr);
			httpServletResponse.addCookie(cookie);
			if (SiteId != null && SiteId.trim().length() > 0) {
				redirectURL = "https://" + redirectServerName + "/#/reportList?ID=" + Id + "&bid=34&SiteId=" + SiteId;
			} else {
				logger.debug("Site ID is null");
				logger.debug("Checking if user will have only one brand");
				UserAuth userAuth = AuthUtil.getUserDetails(userDetails);
				BrandRequest brandRequest = new BrandRequest();
				logger.debug("userService  getBrand calling to get user Brands");
				BrandListResponse response = null;
				try {
					response = userService.getBrand(userAuth.getUserId(), userAuth.getClientId(), brandRequest);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (response != null && response.getSuccessFlag() == true && response.getBrandsCount() == 1) {
					logger.debug("user is having single brand..");
					SiteId = response.getBrandList().get(0).getSiteId().toString();
				} else {
					logger.debug("user is having multiple brands..");
					SiteId = response.getBrandList().get(response.getBrandList().size() - 1).getSiteId().toString();
				}
				redirectURL = "https://" + redirectServerName + "/#/reportList?ID=" + Id + "&bid=34&SiteId=" + SiteId;
			}
			logger.debug("redirectURL : " + redirectURL);
			return "redirect:" + redirectURL;

		} else {
			redirectURL = "https://" + soapUrl + "/Public/Login.aspx?ReturnUrl=%2f";
			logger.debug("redirectURL : " + redirectURL);
			return "redirect:" + redirectURL;
		}
	}
}
