/**
 * 
 */
package com.fb.in.app.reporting.model.mail;

/**
 * @author SKumar7
 *
 */
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "inline", "pdf" })
public class Preferences {

	@JsonProperty("inline")
	private Boolean inline;
	@JsonProperty("pdf")
	private Pdf pdf;

	@JsonProperty("inline")
	public Boolean getInline() {
		return inline;
	}

	@JsonProperty("inline")
	public void setInline(Boolean inline) {
		this.inline = inline;
	}

	@JsonProperty("pdf")
	public Pdf getPdf() {
		return pdf;
	}

	@JsonProperty("pdf")
	public void setPdf(Pdf pdf) {
		this.pdf = pdf;
	}

}