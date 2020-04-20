/**
 * 
 */
package com.fb.in.app.reporting.controller;

import java.math.BigInteger;
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

import com.fb.in.app.reporting.constants.AppConstants;
import com.fb.in.app.reporting.model.auth.UserAuth;
import com.fb.in.app.reporting.model.vo.BrandVo;
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

	@RequestMapping("/report")
	public String getReport(@RequestParam(name = "Id", required = false) String ID,
			@RequestParam(name = "ID", required = false) String Id,
			@RequestParam(name = "ReturnUrl", required = false) String returnUrl,
			@RequestParam(name = "SiteId", required = false) String SiteId, @RequestParam(name = "bid") int bid,
			@RequestParam(name = "SessionId", required = false) String sessionId, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

		final String tileId = ID == null ? Id : ID;
		logger.info("Tile ID : " + tileId);
		logger.info("SiteId : " + SiteId);
		logger.info("bid : " + bid);

		boolean isAspxCookiePresent = false;

		String fishFrameSessionEnv = null;

		String redirectServerName = null;
		String soapUrl = null;
		String redirectURL = null;
		String aspxFormsAuthValue = null;

		Cookie[] cookies = request.getCookies();
		String domain = request.getServerName();
		logger.info("domain : " + domain);
		fishFrameSessionEnv = AuthUtil.getFishFrameSessionEnv(domain);
		// irDomain = AuthUtil.getDomain(domain);
		redirectServerName = AuthUtil.getIRDomain(domain);
		soapUrl = AuthUtil.getSoapUrl(domain);

		logger.info("fishFrameSessionEnv : " + fishFrameSessionEnv);
		logger.info("redirectServerName : " + redirectServerName);
		logger.info("soapUrl : " + soapUrl);
		logger.info("domain : " + domain);

		if (tileId == null || tileId.isEmpty()) {
			redirectURL = soapUrl + AppConstants.FISHBOWL_IAR_SSO_NAVIGATOR_EXTENSION;
			if (SiteId != null && SiteId.trim().length() > 0) {
				redirectURL = redirectURL + "&SiteId=" + SiteId;
			}
			logger.info("redirectURL : " + redirectURL);
			return "redirect:" + redirectURL;
		}
		if (cookies != null) {
			String aspxFormsAuthCookie = AuthUtil.getaSPXFORMSAUTHString(domain);
			for (Cookie ck : cookies) {
				logger.info("Cookie Name : " + ck.getName());
				logger.info("Cookie Domain : " + ck.getDomain());
				if (ck.getName().contains(aspxFormsAuthCookie)) {
					logger.info("AspxCookie is Present and hence proceeding");
					aspxFormsAuthValue = ck.getValue();
					isAspxCookiePresent = true;
				}
			}
			if (!isAspxCookiePresent) {
				logger.info("AspxCookie is not present and hence redirecting to login page");
				redirectURL = "https://" + soapUrl + "/Public/Login.aspx?ReturnUrl=%2f";
				logger.info("redirectURL : " + redirectURL);
				return "redirect:" + redirectURL;
			}
		} else {
			logger.info("There is no Cookie. Hence redirecting to login page");
			redirectURL = "https://" + soapUrl + "/Public/Login.aspx?ReturnUrl=%2f";
			logger.info("redirectURL : " + redirectURL);
			return "redirect:" + redirectURL;
		}

		String userDetails = AuthUtil.getSsoUserDetails(soapUrl, aspxFormsAuthValue);
		if (null != userDetails) {
			String encryptedStr = AuthUtil.encrypted(userDetails);
			logger.info("Encrypted User Details  : " + encryptedStr);
			Cookie cookie = AuthUtil.getIRSessionCookie(domain, encryptedStr);
			httpServletResponse.addCookie(cookie);
			if (SiteId != null && SiteId.trim().length() > 0) {
				redirectURL = redirectServerName + AppConstants.FISHBOWL_IN_APP_REPORING_ANGULAR_APP_EXTENSION + "?ID="
						+ Id + "&bid=34&SiteId=" + SiteId;
			} else {
				logger.info("Site ID is null");
				logger.info("Checking if user will have only one brand");
				UserAuth userAuth = AuthUtil.getUserDetails(userDetails);
				logger.info("userService  getBrand calling to get user Brands");
				Integer mostRecentBrandId = null;
				try {
					mostRecentBrandId = userService.getMostRecentBrandId(userAuth.getUserId());
					if (null != mostRecentBrandId) {
						BrandVo brandVo = userService.getBrandRecord(mostRecentBrandId.toString());
						BigInteger mostResentSiteId = brandVo.getSiteId();
						logger.info("Most Recent Site ID: " + mostResentSiteId);
						if (null != mostResentSiteId)
							redirectURL = redirectServerName
									+ AppConstants.FISHBOWL_IN_APP_REPORING_ANGULAR_APP_EXTENSION + "?ID=" + Id
									+ "&bid=34&SiteId=" + mostResentSiteId;
						else
							redirectURL = redirectServerName
									+ AppConstants.FISHBOWL_IN_APP_REPORING_ANGULAR_APP_EXTENSION + "?ID=" + Id
									+ "&bid=34";
					}
				} catch (Exception e) {
					logger.info(e.getMessage());
					e.printStackTrace();
				}

			}
			logger.info("redirectURL : " + redirectURL);
			return "redirect:" + redirectURL;

		} else {
			redirectURL = soapUrl + AppConstants.ENTERPRIZE_LOGIN_URL_EXTENSION + "?ReturnUrl=%2f";
			logger.info("redirectURL : " + redirectURL);
			return "redirect:" + redirectURL;
		}
	}
}
