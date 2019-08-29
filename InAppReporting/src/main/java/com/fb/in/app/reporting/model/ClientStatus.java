package com.fb.in.app.reporting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ClientStatus",schema="StoreManagement.dbo")
public class ClientStatus {

	@Id
	@GeneratedValue
	@Column(name="ClientStatusCode")
	private int clientStatusCode;
	
	@Column(name="ClientStatusDesc")
	private String clientStatusDesc;

	public int getClientStatusCode() {
		return clientStatusCode;
	}

	public void setClientStatusCode(int clientStatusCode) {
		this.clientStatusCode = clientStatusCode;
	}

	public String getClientStatusDesc() {
		return clientStatusDesc;
	}

	public void setClientStatusDesc(String clientStatusDesc) {
		this.clientStatusDesc = clientStatusDesc;
	}
	
	
}
