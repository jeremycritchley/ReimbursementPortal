package com.revature.services;


import org.apache.log4j.Logger;
import com.revature.dao.GenericDAO;
import com.revature.dao.UserDAO;
import com.revature.dto.UserDTO;
import com.revature.models.User;
import com.revature.utils.PasswordHash;

public class LoginServiceImpl implements LoginService{
	private static Logger log = Logger.getLogger(LoginServiceImpl.class);
	
	private GenericDAO<User> userd;
	
	public LoginServiceImpl(GenericDAO<User> userd) {
		this.userd = userd;
	}
	
	public LoginServiceImpl() {
		super();
		this.userd = new UserDAO();
	}
	
	@Override
	public UserDTO login(String username, String password) {

		if (username == null || password == null) {
			return null;
		}
		
		
		String un = "username";
		
		User user = userd.selectByParam(un, username);
		UserDTO ret = null;
		
		if (user != null) {
			// hash password before checking equality
			if (PasswordHash.checkMatch(password, user.getPassword())) {
				ret = new UserDTO(user);
				log.info("USER " + ret.getUserId() + " LOGGING IN");
			}
		}
		
		
		
		return ret;
	}

	@Override
	public UserDTO insert(UserDTO u) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
