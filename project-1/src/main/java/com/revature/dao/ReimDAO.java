package com.revature.dao;

import java.util.List;

import org.hibernate.Session;

import com.revature.models.Reimbursement;
import com.revature.utils.HibernateUtil;

public class ReimDAO extends GenericDAO<Reimbursement>{

	@Override
	public Reimbursement selectById(int id) {
		Session ses = HibernateUtil.getSession();
		Reimbursement reim = ses.get(Reimbursement.class, id);
		return reim;
	}
	
	/*
	 * Naive Implementation - must be provided hibernate column name as param
	 */
	@Override
	public List<Reimbursement> selectAll(String param, String val) {
		Session ses = HibernateUtil.getSession();
		List<Reimbursement> reims = ses.createQuery("from Reimbursement where "+ param + "='" + val + "'", Reimbursement.class).list();
		return reims;
	}

	/*
	 * Naive Implementation, getting first element from selectAll
	 * 	- should only use when only one instance of param = val should be in the DB
	 */
	@Override
	public Reimbursement selectByParam(String param, String val) {
		List<Reimbursement> reims = selectAll(param, val);
		
		if (reims.size() == 0) {
			// log warning
			return null;
		}
		
		return reims.get(0);
	}

}
