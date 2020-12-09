package com.revature.dao;

import org.hibernate.Session;
import com.revature.models.Role;
import com.revature.utils.HibernateUtil;

public class RoleDAO {
	/**
	 * Necessary for Hibernate
	 * 
	 * @param id - 	ID of Role
	 * @return - 	Persistent Hibernate Role
	 */
	public static final Role selectById(int id) {
		Session ses = HibernateUtil.getSession();
		Role rs = ses.get(Role.class, id);
		return rs;
	}
	
}
