/**
 * Class to aid in mapping Login Information (Username & Password)
 * From Object mapper to Java
 */
package com.revature.models;

public class LoginHelper {
	
	
	private String username;
	private String password;

	public LoginHelper() {
		
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
