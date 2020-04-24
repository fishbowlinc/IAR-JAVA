package com.fb.in.app.reporting.model.mail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "type", "recipient" })
public class Recipient {

	@JsonProperty("type")
	private String type;
	@JsonProperty("recipient")
	private String recipient;

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("recipient")
	public String getRecipient() {
		return recipient;
	}

	@JsonProperty("recipient")
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

}
