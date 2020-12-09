package com.revature.dao;

import org.hibernate.Session;

import com.revature.models.ReimStatus;
import com.revature.utils.HibernateUtil;

public class StatusDAO {
	/**
	 * Necessary for Hibernate
	 * 
	 * @param id - 	ID of Reimbursement Status
	 * @return - 	Persistent Hibernate Role
	 */
	public static final ReimStatus selectById(int id) {
		Session ses = HibernateUtil.getSession();
		ReimStatus rs = ses.get(ReimStatus.class, id);
		return rs;
	}
}
