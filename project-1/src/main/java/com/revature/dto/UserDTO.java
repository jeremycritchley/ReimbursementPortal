package com.revature.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.dao.GenericDAO;
import com.revature.dao.UserDAO;
import com.revature.models.Role;
import com.revature.models.User;

public class UserDTO {
	
	/**
	 * Meant for lighter transfer of data and Object Mapping to JSON
	 */
	
	private String userId;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String role;
	
	public UserDTO() {
		super();
	}

	public UserDTO(String userId, String username, String password, String firstName, String lastName, String email,
			String role) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
	}
	
	public UserDTO(User u) {
		super();
		this.userId = Integer.toString(u.getUserId());
		this.username = u.getUsername();
		this.password = u.getPassword();
		this.firstName = u.getFirstName();
		this.lastName = u.getLastName();
		this.email = u.getEmail();
		this.role = u.getRole().getRole();
	}
	
	/**
	 * 
	 * @return	Returns User Object of this UserDTO instance or null
	 */
	@JsonIgnore
	public User getUserInstance() {
		GenericDAO<User> userd = new UserDAO();
		return getUserInstance(userd);
	}
	
	/**
	 * Used for Testing or already instantiated DAO
	 * 	- Used for Mocking in this project
	 * 
	 * @param rd - DAO to get User from
	 * @return	 - User Object of this UserDTO instance or null
	 */
	@JsonIgnore
	public User getUserInstance(GenericDAO<User> ud) {
		GenericDAO<User> userd = ud;
		return userd.selectById(Integer.parseInt(this.userId));
	}
	
	/**
	 * Used when creating/inserting Users into DB
	 * Creates a new User object without a generated ID and not yet in DB
	 * 
	 * @return	User object created from this UserDTO
	 */
	@JsonIgnore
	public User createUserInstance() {
		User u = new User();
		u.setUsername(username);
		u.setFirstName(firstName);
		u.setLastName(lastName);
		u.setPassword(password);
		u.setEmail(email);
		Role role = new Role(this.role);
		u.setRole(role);
		return u;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	
}
