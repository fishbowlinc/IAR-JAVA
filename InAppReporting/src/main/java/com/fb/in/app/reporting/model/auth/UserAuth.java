package com.fb.in.app.reporting.model.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

/**
 * @Fishbowl
 * @author sanjeev
 */

public class UserAuth implements Serializable {
	private static final long serialVersionUID = 1L;
	String userId;
	String userName;
	int tenantId;
	String tenantName;
	List<UserAuthRole> userTenantRoles;
	String accessToken;
	String accessTokenIssued;
	String accessTokenExpires;
	String refreshToken;
	String refreshTokenExpires;
	String domain;
	String requestURL;
	String siteId;
	boolean isPersistant;
	String brandId;
	String clientId;

	private List<Map<String, String>> cookies = new ArrayList<Map<String, String>>();

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public List<UserAuthRole> getUserTenantRoles() {
		return userTenantRoles;
	}

	public void setUserTenantRoles(List<UserAuthRole> userTenantRoles) {
		this.userTenantRoles = userTenantRoles;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessTokenIssued() {
		return accessTokenIssued;
	}

	public void setAccessTokenIssued(String accessTokenIssued) {
		this.accessTokenIssued = accessTokenIssued;
	}

	public String getAccessTokenExpires() {
		return accessTokenExpires;
	}

	public void setAccessTokenExpires(String accessTokenExpires) {
		this.accessTokenExpires = accessTokenExpires;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getRefreshTokenExpires() {
		return refreshTokenExpires;
	}

	public void setRefreshTokenExpires(String refreshTokenExpires) {
		this.refreshTokenExpires = refreshTokenExpires;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getRequestURL() {
		return requestURL;
	}

	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	public List<Map<String, String>> getCookies() {
		return cookies;
	}

	public void setCookies(List<Map<String, String>> cookies) {
		this.cookies = cookies;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public boolean isPersistant() {
		return isPersistant;
	}

	public void setPersistant(boolean isPersistant) {
		this.isPersistant = isPersistant;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public boolean isUserAdmin() {
		if (this.userTenantRoles != null && this.userTenantRoles.size() > 0) {
			for (UserAuthRole userAuthRole : this.userTenantRoles) {
				if (!StringUtils.isEmpty(userAuthRole.getRoleName())
						&& userAuthRole.getRoleName().toLowerCase().indexOf("admin") >= 0) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isInternalFBUser() {
		if (this.userTenantRoles != null && this.userTenantRoles.size() > 0) {
			for (UserAuthRole userAuthRole : this.userTenantRoles) {
				if (!StringUtils.isEmpty(userAuthRole.getRoleName())
						&& userAuthRole.getRoleName().toLowerCase().indexOf("FBInternalUser") >= 0) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "UserAuth [userId=" + userId + ", userName=" + userName + ", tenantId=" + tenantId + ", tenantName="
				+ tenantName + ", userTenantRoles=" + userTenantRoles + ", accessToken=" + accessToken
				+ ", accessTokenIssued=" + accessTokenIssued + ", accessTokenExpires=" + accessTokenExpires
				+ ", refreshToken=" + refreshToken + ", refreshTokenExpires=" + refreshTokenExpires + ", domain="
				+ domain + ", requestURL=" + requestURL + ", siteId=" + siteId + ", cookies=" + cookies + "]";
	}
}
