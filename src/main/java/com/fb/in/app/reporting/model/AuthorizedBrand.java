package com.fb.in.app.reporting.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "v_AuthorizedBrand", schema = "StoreManagement.dbo")
public class AuthorizedBrand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3466106277652020889L;

	@Id
	@Column(name = "UserId")
	private int userId;

	@Id
	@Column(name = "ClientId")
	private int clientId;

	@Id
	@Column(name = "BrandId")
	private int brandId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

}
