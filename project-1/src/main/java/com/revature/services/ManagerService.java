package com.revature.services;

import java.util.List;

import com.revature.dto.ReimDTO;
import com.revature.dto.UserDTO;

public interface ManagerService {
	
	/**
	 * To approve the given Reimbursement with the Resolver as User with username resolver
	 * Must update resolver of Reimbursement and resolved time before updating
	 * 
	 * @param rdto	- ReimDTO to be approved : must be updated appropriately
	 * @param resolver - username of User who is resolving this reimbursement
	 * @return - true if approval was successful, otherwise false
	 */
	public boolean approveReim(ReimDTO rdto, String resolver);
	
	/**
	 * To deny the given Reimbursement with the Resolver as User with username resolver
	 * Must update resolver of Reimbursement and resolved time before updating
	 * 
	 * @param rdto	- ReimDTO to be denied : must be updated appropriately
	 * @param resolver - username of User who is resolving this reimbursement
	 * @return - true if denial was successful, otherwise false
	 */
	public boolean denyReim(ReimDTO rdto, String resolver);
	
	/**
	 * Return list of all Users in DB who are Employees
	 * 
	 * @return - list of UserDTO of Users from DB
	 */
	public List<UserDTO> viewAllEmployees();
	
	
}
