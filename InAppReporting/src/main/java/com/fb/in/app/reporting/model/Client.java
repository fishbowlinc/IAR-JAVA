package com.fb.in.app.reporting.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Client",schema="StoreManagement.dbo")
public class Client {
	
	@Id
	@GeneratedValue
	@Column(name="ClientID")
	private int clientId;
	
	@Column(name="Created")
	private Date created;
	
	@Column(name="ClientName")
	private String clientName;
	
	@Column(name="ClientStatusCode")
	private String clientStatusCode;
	
	@Column(name="CMPServiceID")
	private int cmpServiceId;
	
	@Column(name="CMPCustomerID")
	private String cmpCustomerId;
	
	@Column(name="Platform")
	private String platform;


	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientStatusCode() {
		return clientStatusCode;
	}

	public void setClientStatusCode(String clientStatusCode) {
		this.clientStatusCode = clientStatusCode;
	}

	public int getCmpServiceId() {
		return cmpServiceId;
	}

	public void setCmpServiceId(int cmpServiceId) {
		this.cmpServiceId = cmpServiceId;
	}

	public String getCmpCustomerId() {
		return cmpCustomerId;
	}

	public void setCmpCustomerId(String cmpCustomerId) {
		this.cmpCustomerId = cmpCustomerId;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	

}
