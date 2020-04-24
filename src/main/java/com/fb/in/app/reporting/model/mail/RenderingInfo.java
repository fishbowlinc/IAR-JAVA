package com.fb.in.app.reporting.model.mail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "paperFormat", "paperOrientation", "layout", "showNarration" })
public class RenderingInfo {

	@JsonProperty("paperFormat")
	private String paperFormat;
	@JsonProperty("paperOrientation")
	private String paperOrientation;
	@JsonProperty("layout")
	private String layout;
	@JsonProperty("showNarration")
	private Boolean showNarration;

	@JsonProperty("paperFormat")
	public String getPaperFormat() {
		return paperFormat;
	}

	@JsonProperty("paperFormat")
	public void setPaperFormat(String paperFormat) {
		this.paperFormat = paperFormat;
	}

	@JsonProperty("paperOrientation")
	public String getPaperOrientation() {
		return paperOrientation;
	}

	@JsonProperty("paperOrientation")
	public void setPaperOrientation(String paperOrientation) {
		this.paperOrientation = paperOrientation;
	}

	@JsonProperty("layout")
	public String getLayout() {
		return layout;
	}

	@JsonProperty("layout")
	public void setLayout(String layout) {
		this.layout = layout;
	}

	@JsonProperty("showNarration")
	public Boolean getShowNarration() {
		return showNarration;
	}

	@JsonProperty("showNarration")
	public void setShowNarration(Boolean showNarration) {
		this.showNarration = showNarration;
	}

}