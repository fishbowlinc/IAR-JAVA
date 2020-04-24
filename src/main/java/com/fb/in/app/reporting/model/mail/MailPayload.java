package com.fb.in.app.reporting.model.mail;


import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "assetId", "assetType", "recipients", "requestTimeout", "preferences" })
public class MailPayload {

	@JsonProperty("assetId")
	private String assetId;
	@JsonProperty("assetType")
	private String assetType;
	@JsonProperty("recipients")
	private List<Recipient> recipients = null;
	@JsonProperty("requestTimeout")
	private Integer requestTimeout;
	@JsonProperty("preferences")
	private Preferences preferences;

	@JsonProperty("assetId")
	public String getAssetId() {
		return assetId;
	}

	@JsonProperty("assetId")
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	@JsonProperty("assetType")
	public String getAssetType() {
		return assetType;
	}

	@JsonProperty("assetType")
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	@JsonProperty("recipients")
	public List<Recipient> getRecipients() {
		return recipients;
	}

	@JsonProperty("recipients")
	public void setRecipients(List<Recipient> recipients) {
		this.recipients = recipients;
	}

	@JsonProperty("requestTimeout")
	public Integer getRequestTimeout() {
		return requestTimeout;
	}

	@JsonProperty("requestTimeout")
	public void setRequestTimeout(Integer requestTimeout) {
		this.requestTimeout = requestTimeout;
	}

	@JsonProperty("preferences")
	public Preferences getPreferences() {
		return preferences;
	}

	@JsonProperty("preferences")
	public void setPreferences(Preferences preferences) {
		this.preferences = preferences;
	}

}