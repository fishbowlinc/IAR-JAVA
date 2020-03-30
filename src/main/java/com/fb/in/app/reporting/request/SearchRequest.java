package com.fb.in.app.reporting.request;


public class SearchRequest {
	
	private String freeText;
	private Integer stage;
	private String lastUpdated;
	private String requestedDateFrom;
	private String requestedDateTo;
	private String availableDateFrom;
	private String availableDateTo;
	
	public String getFreeText() {
		return freeText;
	}
	public void setFreeText(String freeText) {
		this.freeText = freeText;
	}
	public Integer getStage() {
		return stage;
	}
	public void setStage(Integer stage) {
		this.stage = stage;
	}
	public String getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getRequestedDateFrom() {
		return requestedDateFrom;
	}
	public void setRequestedDateFrom(String requestedDateFrom) {
		this.requestedDateFrom = requestedDateFrom;
	}
	public String getRequestedDateTo() {
		return requestedDateTo;
	}
	public void setRequestedDateTo(String requestedDateTo) {
		this.requestedDateTo = requestedDateTo;
	}
	public String getAvailableDateFrom() {
		return availableDateFrom;
	}
	public void setAvailableDateFrom(String availableDateFrom) {
		this.availableDateFrom = availableDateFrom;
	}
	public String getAvailableDateTo() {
		return availableDateTo;
	}
	public void setAvailableDateTo(String availableDateTo) {
		this.availableDateTo = availableDateTo;
	}

	
	
	

}
