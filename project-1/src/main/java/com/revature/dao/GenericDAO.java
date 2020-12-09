package com.revature.dao;

import java.util.List;

import javax.persistence.RollbackException;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.utils.HibernateUtil;


public abstract class GenericDAO<T> {
	
	private static Logger log = Logger.getLogger(GenericDAO.class);
	
	
	/**
	 * Generic Method for inserting into the DB  using Hibernate
	 * 
	 * 
	 * @param t - 	Reimbursement or User object to be inserted
	 * @return - 	new int ID generated for the new T
	 * 			OR	0 if failed
	 */
	public int insert(T t) {
		int ret = 0;
		
		Session ses = HibernateUtil.getSession();
		
		Transaction tx = ses.beginTransaction();
		
		ret = (Integer) ses.save(t);
		
		try {
			tx.commit();
		} catch (IllegalStateException e) {
			log.warn("EXCEPTION INSERTING REIM \n" + e.getMessage());
			e.printStackTrace();
			ret = 0;
		} catch (RollbackException e) {
			e.printStackTrace();
			log.warn("EXCEPTION INSERTING REIM \n" + e.getMessage());
			ret = 0;
		} catch (Exception e) {
			ret = 0;
			log.warn("EXCEPTION INSERTING REIM \n" + e.getMessage());
		}
		
		
		return ret;
	}
	
	/**
	 * Generic Method for Updating Reimbursements and Users with Hibernate
	 * 
	 * 
	 * @param t - 	T object to be updated
	 * @return - 	ID of T generated
	 * 			OR 	0 if not found 
	 */
	public boolean update(T t) {
		boolean ret = true;
		
		Session ses = HibernateUtil.getSession();
		
		Transaction tx = ses.beginTransaction();
		
		ses.update(t);
		
		try {
			tx.commit();
		} catch (IllegalStateException e) {
			e.printStackTrace();
			log.warn("EXCEPTION UPDATING\n " + e.getMessage());
			ret = false;
		} catch (RollbackException e) {
			e.printStackTrace();
			log.warn("EXCEPTION UPDATING \n" + e.getMessage());
			ret = false;
		} catch (Exception e) {
			ret = false;
			log.warn("EXCEPTION UPDATING \n" + e.getMessage());
		}
		return ret;
	}
	
	
	/**
	 * Select T by its ID generated from insert
	 * 
	 * @param id - 	ID of T to be selected
	 * @return	- 	The T with id as id
	 * 			OR	null if not found
	 */
	public abstract T selectById(int id);
	
	/**
	 * Select All T where param of T is equal to val
	 * 
	 * @param param - 	attribute of T : String must exactly match that of the Column 
	 * 					name denoted in JPA annotations in Models classes
	 * 					
	 * @param val -		The value of attribute param
	 * @return - 		List of all T where param = va
	 */
	public abstract List<T> selectAll(String param, String val);
	
	/**
	 * Select All T where param of T is equal to val
	 * Use for instances where there should only be one case that param = val
	 * 
	 * @param param - 	attribute of T : String must exactly match that of the Column 
	 * 					name denoted in JPA annotations in Models classes
	 * 					
	 * @param val -		The value of attribute param
	 * @return - 		First T where param = val
	 */
	public abstract T selectByParam(String param, String val);
}
