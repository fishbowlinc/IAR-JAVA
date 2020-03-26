package com.fb.in.app.reporting.util;

import javax.xml.bind.annotation.XmlRootElement;



//This is a super class which needs to be used to send error or success messages as REST response.
//All other rest responses need to inherit from this class.

@XmlRootElement
public class FBRestResponse {
	
	protected boolean successFlag = true;
	protected String message = "Success";
	
	public FBRestResponse(){};
	
	public FBRestResponse(boolean successFlag, String message) {
		
		this.successFlag = successFlag;
		this.message = message;
	}
			
	public boolean getSuccessFlag() {
		return successFlag;
	}
	
	public void setSuccessFlag(boolean successFlag) {
		this.successFlag = successFlag;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setResponse(boolean successFlag, String message) {
		this.setSuccessFlag(successFlag);
		this.setMessage(message);
	}
}
