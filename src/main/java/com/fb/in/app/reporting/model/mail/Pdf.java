package com.fb.in.app.reporting.model.mail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "includeTitle", "includeFilters", "includeDS", "renderingInfo" })
public class Pdf {

	@JsonProperty("includeTitle")
	private Boolean includeTitle;
	@JsonProperty("includeFilters")
	private Boolean includeFilters;
	@JsonProperty("includeDS")
	private Boolean includeDS;
	@JsonProperty("renderingInfo")
	private RenderingInfo renderingInfo;

	@JsonProperty("includeTitle")
	public Boolean getIncludeTitle() {
		return includeTitle;
	}

	@JsonProperty("includeTitle")
	public void setIncludeTitle(Boolean includeTitle) {
		this.includeTitle = includeTitle;
	}

	@JsonProperty("includeFilters")
	public Boolean getIncludeFilters() {
		return includeFilters;
	}

	@JsonProperty("includeFilters")
	public void setIncludeFilters(Boolean includeFilters) {
		this.includeFilters = includeFilters;
	}

	@JsonProperty("includeDS")
	public Boolean getIncludeDS() {
		return includeDS;
	}

	@JsonProperty("includeDS")
	public void setIncludeDS(Boolean includeDS) {
		this.includeDS = includeDS;
	}

	@JsonProperty("renderingInfo")
	public RenderingInfo getRenderingInfo() {
		return renderingInfo;
	}

	@JsonProperty("renderingInfo")
	public void setRenderingInfo(RenderingInfo renderingInfo) {
		this.renderingInfo = renderingInfo;
	}

}