package com.fb.in.app.reporting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BrandStatus",schema="StoreManagement.dbo")
public class BrandStatus {
	
	@Id
	@GeneratedValue
	@Column(name="BrandStatusCode")
	private int brandStatusCode;

	@Column(name="BrandStatusDesc")
	private String brandStatusDesc;

	public int getBrandStatusCode() {
		return brandStatusCode;
	}

	public void setBrandStatusCode(int brandStatusCode) {
		this.brandStatusCode = brandStatusCode;
	}

	public String getBrandStatusDesc() {
		return brandStatusDesc;
	}

	public void setBrandStatusDesc(String brandStatusDesc) {
		this.brandStatusDesc = brandStatusDesc;
	}
	
	
}
