package com.fb.in.app.reporting.model.auth;

import java.io.Serializable;

/**
 * @Fishbowl
 * @author Sanjeev
 */

public class UserAuthRole implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String tenantName;
	private String domainName;
	private String roleId;
	private String roleName;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "UserAuthRole [tenantId=" + tenantId + ", tenantName=" + tenantName + ", domainName=" + domainName
				+ ", roleId=" + roleId + ", roleName=" + roleName + "]";
	}
}