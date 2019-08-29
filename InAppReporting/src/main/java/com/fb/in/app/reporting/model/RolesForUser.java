package com.fb.in.app.reporting.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RolesForUser", schema = "UserManagement.dbo")
public class RolesForUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3019769641875982984L;

	@Id
	@Column(name = "UserID")
	private int userId;

	@Id
	@Column(name = "RoleID")
	private int roleId;

	@Id
	@Column(name = "BrandID")
	private int brandId;

	@Column(name = "ProductID")
	private int productId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

}
