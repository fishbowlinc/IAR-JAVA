package com.fb.in.app.reporting.model;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Brand",schema="StoreManagement.dbo")
public class Brand {
	
	@Id
	@GeneratedValue
	@Column(name="BrandID")
	private int brandId;
	
	@Column(name="Created")
	private Date created;
	
	@Column(name="ClientID")
	private int clientId;
	
	@Column(name="SiteID")
	private BigInteger siteId;
	
	@Column(name="BrandName")
	private String brandName;
	
	@Column(name="BrandDesc")
	private String brandDesc;
	
	@Column(name="BrandAlias")
	private String brandAlias;
	
	@Column(name="BrandStatusCode")
	private String brandStatusCode;
	
	@Column(name="BrandURL")
	private String brandUrl;
	
	@Column(name="BrandStoreFinderURL")
	private String brandStoreFinderUrl;
	
	@Column(name="DefaultMailingListID")
	private BigInteger defaultMailingListId;
	
	@Column(name="DefaultDataEntryListID")
	private int defaultDataEntryListId;
	
	@Column(name="PrimaryContactTitle")
	private String primaryContactTitle;
	
	@Column(name="PrimaryContactFirstName")
	private String primaryContactFirstName;
	
	@Column(name="PrimaryContactLastName")
	private String primaryContactLastName;
	
	@Column(name="PrimaryContactPhone")
	private String primaryContactPhone;
	
	@Column(name="PrimaryContactEmail")
	private String primaryContactEmail;
	
	@Column(name="MailingAddress1")
	private String mailingAddress1;
	
	@Column(name="MailingAddress2")
	private String mailingAddress2;
	
	@Column(name="MailingCity")
	private String mailingCity;
	
	@Column(name="MailingState")
	private String mailingState;
	
	@Column(name="MailingZip")
	private String mailingZip;
	
	@Column(name="MailingCountry")
	private String mailingCountry;
	
	@Column(name="ImageManagerAccess")
	private int imageManagerAccess;
	
	@Column(name="BitMaskFlags")
	private int bitMaskFlags;
	
	@Column(name="BrandCulture")
	private String brandCulture;
	
	@Column(name="LastUpdated")
	private Date lastUpdated;

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public BigInteger getSiteId() {
		return siteId;
	}

	public void setSiteId(BigInteger siteId) {
		this.siteId = siteId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandDesc() {
		return brandDesc;
	}

	public void setBrandDesc(String brandDesc) {
		this.brandDesc = brandDesc;
	}

	public String getBrandAlias() {
		return brandAlias;
	}

	public void setBrandAlias(String brandAlias) {
		this.brandAlias = brandAlias;
	}

	public String getBrandStatusCode() {
		return brandStatusCode;
	}

	public void setBrandStatusCode(String brandStatusCode) {
		this.brandStatusCode = brandStatusCode;
	}

	public String getBrandUrl() {
		return brandUrl;
	}

	public void setBrandUrl(String brandUrl) {
		this.brandUrl = brandUrl;
	}

	public String getBrandStoreFinderUrl() {
		return brandStoreFinderUrl;
	}

	public void setBrandStoreFinderUrl(String brandStoreFinderUrl) {
		this.brandStoreFinderUrl = brandStoreFinderUrl;
	}

	public BigInteger getDefaultMailingListId() {
		return defaultMailingListId;
	}

	public void setDefaultMailingListId(BigInteger defaultMailingListId) {
		this.defaultMailingListId = defaultMailingListId;
	}

	public int getDefaultDataEntryListId() {
		return defaultDataEntryListId;
	}

	public void setDefaultDataEntryListId(int defaultDataEntryListId) {
		this.defaultDataEntryListId = defaultDataEntryListId;
	}

	public String getPrimaryContactTitle() {
		return primaryContactTitle;
	}

	public void setPrimaryContactTitle(String primaryContactTitle) {
		this.primaryContactTitle = primaryContactTitle;
	}

	public String getPrimaryContactFirstName() {
		return primaryContactFirstName;
	}

	public void setPrimaryContactFirstName(String primaryContactFirstName) {
		this.primaryContactFirstName = primaryContactFirstName;
	}

	public String getPrimaryContactLastName() {
		return primaryContactLastName;
	}

	public void setPrimaryContactLastName(String primaryContactLastName) {
		this.primaryContactLastName = primaryContactLastName;
	}

	public String getPrimaryContactPhone() {
		return primaryContactPhone;
	}

	public void setPrimaryContactPhone(String primaryContactPhone) {
		this.primaryContactPhone = primaryContactPhone;
	}

	public String getPrimaryContactEmail() {
		return primaryContactEmail;
	}

	public void setPrimaryContactEmail(String primaryContactEmail) {
		this.primaryContactEmail = primaryContactEmail;
	}

	public String getMailingAddress1() {
		return mailingAddress1;
	}

	public void setMailingAddress1(String mailingAddress1) {
		this.mailingAddress1 = mailingAddress1;
	}

	public String getMailingAddress2() {
		return mailingAddress2;
	}

	public void setMailingAddress2(String mailingAddress2) {
		this.mailingAddress2 = mailingAddress2;
	}

	public String getMailingCity() {
		return mailingCity;
	}

	public void setMailingCity(String mailingCity) {
		this.mailingCity = mailingCity;
	}

	public String getMailingState() {
		return mailingState;
	}

	public void setMailingState(String mailingState) {
		this.mailingState = mailingState;
	}

	public String getMailingZip() {
		return mailingZip;
	}

	public void setMailingZip(String mailingZip) {
		this.mailingZip = mailingZip;
	}

	public String getMailingCountry() {
		return mailingCountry;
	}

	public void setMailingCountry(String mailingCountry) {
		this.mailingCountry = mailingCountry;
	}

	public int getImageManagerAccess() {
		return imageManagerAccess;
	}

	public void setImageManagerAccess(int imageManagerAccess) {
		this.imageManagerAccess = imageManagerAccess;
	}

	public int getBitMaskFlags() {
		return bitMaskFlags;
	}

	public void setBitMaskFlags(int bitMaskFlags) {
		this.bitMaskFlags = bitMaskFlags;
	}

	public String getBrandCulture() {
		return brandCulture;
	}

	public void setBrandCulture(String brandCulture) {
		this.brandCulture = brandCulture;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	
}
