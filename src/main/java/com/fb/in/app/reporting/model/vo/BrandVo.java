package com.fb.in.app.reporting.model.vo;

import java.math.BigInteger;

public class BrandVo {

	private int brandId;
	private String brandName;
	private BigInteger siteId;
	private String brandStatus;
	private String clientName;
	private int clientId;
	private String cmpCustomerId;
	private String clientStatus;
	
	public BrandVo() {
		
	}
	
	public BrandVo(int brandId,String brandName, BigInteger siteId, String brandStatus, String clientName, int clientId, String cmpCustomerId, String clientStatus) {
		this.brandId = brandId;
		this.brandName = brandName;
		this.siteId = siteId;
		this.brandStatus = brandStatus;
		this.clientName = clientName;
		this.clientId = clientId;
		this.cmpCustomerId = cmpCustomerId;
		this.clientStatus = clientStatus;
	}
	
	public int getBrandId() {
		return brandId;
	}
	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public BigInteger getSiteId() {
		return siteId;
	}
	public void setSiteId(BigInteger siteId) {
		this.siteId = siteId;
	}
	public String getBrandStatus() {
		return brandStatus;
	}
	public void setBrandStatus(String brandStatus) {
		this.brandStatus = brandStatus;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public String getCmpCustomerId() {
		return cmpCustomerId;
	}
	public void setCmpCustomerId(String cmpCustomerId) {
		this.cmpCustomerId = cmpCustomerId;
	}
	public String getClientStatus() {
		return clientStatus;
	}
	public void setClientStatus(String clientStatus) {
		this.clientStatus = clientStatus;
	}
	
	@Override
	public String toString() {
		return "BrandVo [brandId=" + brandId + ", brandName=" + brandName + ", siteId=" + siteId + ", brandStatus="
				+ brandStatus + ", clientName=" + clientName + ", clientId=" + clientId + ", cmpCustomerId="
				+ cmpCustomerId + ", clientStatus=" + clientStatus + "]";
	}
	
	
}
