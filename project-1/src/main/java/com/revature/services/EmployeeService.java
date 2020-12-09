package com.revature.services;

import java.util.List;

import com.revature.dto.ReimDTO;
import com.revature.dto.UserDTO;

public interface EmployeeService {
	
	/**
	 * View a User by their ID
	 * 
	 * @param user_id -	String representation of the ID of the Application User
	 * @return - 		UserDTO of user with ID = user_id or null if one does not exist
	 */
	public UserDTO viewByUser(String user_id);
	
	/**
	 * Update User's Info with new Information
	 * 	
	 * @param udto - UserDTO with the already updated info
	 * @return	   - true if update was successful, false if update was not successful
	 */
	public boolean updateInfo(UserDTO udto);
	
	/**
	 * Persist a new Reimbursement to the DB
	 * Must update Reimbursements author and submitted time before persisting
	 * 
	 * @param rdto -	ReimDTO of Reimbursement to be submitted
	 * 					Does not yet have an author or submitted time
	 * @param author - 	Username of the User submitting this Reimbursement Request
	 * @return -		newly generated int ID or 0 if fails
	 */
	public int submitReim(ReimDTO rdto, String author);
	
	/**
	 * Returns a list of all ReimDTO's from where author's ID = ownerId by status
	 * 
	 * @param ownerId -		String representation of all Reimbursements' Author's ID to be returned	
	 * @param resolved - 	false if ReimDTO's to be returned should have a status of PENDING
	 * 						true if ReimDTO's to be returned should have a status not equal to PENDING
	 * @return -			List of ReimDTO's where ownerId is equal to the Reimbursements' Author's ID with status of PENDING or not
	 * 					OR  null if none or author doesn't exist
	 */
	public List<ReimDTO> viewRiemsByStatus(String ownerId, boolean resolved);
	
	/**
	 * Return all Reimbursements where the Author ID is equal to ownerId
	 * 
	 * @param ownerId - ID of the Reimbursement Author whose ReimDTO's we want to return
	 * @return - 	  	List of ReimDTOs of Author or null if none or author doesn't exist
	 */
	public List<ReimDTO> viewReimsByEmployee(String ownerId);
	
}
