package com.fb.in.app.reporting.model.vo;

public class UserDeailsVo {

	private String userName;
	private String firstName;
	private String lastName;
	private String title;
	private String phone;
	private String email;
	private int clientId;
	private String timeZone;
	private int roleId;
	
	public UserDeailsVo() {
		
	}
	
	public UserDeailsVo(String userName, String firstName, String lastName, String title, String phone, String email, int clientId, String timeZone, int roleId) {
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
		this.phone = phone;
		this.email = email;
		this.clientId = clientId;
		this.timeZone = timeZone;
		this.roleId = roleId;
	}
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	
}
