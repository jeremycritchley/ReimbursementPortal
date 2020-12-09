package com.revature.services;

import com.revature.dto.UserDTO;

public interface LoginService {
	
	/**
	 * get User whose username is equal to username and check if the password entered
	 * is the same as the User's password
	 * 
	 * @param username
	 * @param password
	 * @return - 	UserDTO of user with username and password
	 * 			OR  null if username does not exist, or password is incorrect
	 */
	public UserDTO login(String username, String password);
	
	/**
	 * Insert a User into DB and give them their generated ID
	 * 
	 * @param u - User to insert into the DB
	 * @return  - UserDTO with updated ID or null if failed
	 */
	public UserDTO insert(UserDTO u);
	
}
