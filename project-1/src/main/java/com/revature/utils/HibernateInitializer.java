package com.revature.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.revature.dao.GenericDAO;
import com.revature.dao.ReimDAO;
import com.revature.dao.RoleDAO;
import com.revature.dao.StatusDAO;
import com.revature.dao.UserDAO;
import com.revature.dto.ReimDTO;
import com.revature.models.ReimStatus;
import com.revature.models.ReimType;
import com.revature.models.Reimbursement;
import com.revature.models.Role;
import com.revature.models.User;

public class HibernateInitializer {
	
	public static void main(String[] args) {
		//initializeValues();
		//getValues();	
		//addUsers();
	}

	private static void addUsers() {
		Role man = RoleDAO.selectById(1);
		Role emp = RoleDAO.selectById(2);
		
		
		
		User employ5 = new User("MyspaceTom", "myspace", "Myspace", "Tom", "myspace@email.com", emp);
		User employ6 = new User("Jeans", "jeans", "Blue", "Jeans", "levis@email.com", emp);
		User employ7 = new User("Witch", "witch", "Wicked", "Witch", "imawitch@email.com", emp);
		User employ8 = new User("Sandwhich", "sandwhich", "Sand", "Witch", "sandwhich@email.com", emp);
		User employ9 = new User("wizard", "wizard", "Wizard", "Merlin", "merlinsbeard@email.com", emp);
		
		GenericDAO<User> udao = new UserDAO();
		
		System.out.println(udao.insert(employ5));
		System.out.println(udao.insert(employ6));
		System.out.println(udao.insert(employ7));
		System.out.println(udao.insert(employ8));
		System.out.println(udao.insert(employ9));
		
	}

	private static void getValues() {
		// TODO Auto-generated method stub
		GenericDAO<User> userd = new UserDAO();
		System.out.println(userd.selectAll("user_id!", "0"));
		User man = userd.selectById(1);
		System.out.println(man);
		System.out.println(userd.selectAll("Role_FK", "2"));
		
		GenericDAO<Reimbursement> reimd = new ReimDAO();
		List<Reimbursement> reims = reimd.selectAll("Status_FK", "1");
		System.out.println(reims);
		System.out.println(reimd.selectById(6));
		
		Reimbursement r = reims.get(0);
		ReimDTO rdto = new ReimDTO(r);
		rdto.setStatus("APPROVED");
		Reimbursement r2 = rdto.getReimInstance();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date d = new Date();
		r2.setResolvedTime(formatter.format(d));
		r2.setResolver(man);
		r2.setStatus(StatusDAO.selectById(2));
		reimd.update(r2);
	}

	private static void initializeValues() {
		Role man = new Role(1, "MANAGER");
		Role emp = new Role(2, "EMPLOYEE");
		
		ReimType lodge = new ReimType(1, "LODGE");
		ReimType food = new ReimType(2, "FOOD");
		ReimType travel = new ReimType(3, "TRAVEL");
		ReimType other = new ReimType(4, "OTHER");
		
		ReimStatus pend = new ReimStatus(1, "PENDING");
		ReimStatus approved = new ReimStatus(2, "APPROVED");
		ReimStatus denied = new ReimStatus(3, "DENIED");
		
		User manager = new User("jeremy", "passvvord", "Jeremy", "Critchley", "jeremy@email.com", man);
		User employ = new User("employee", "password", "Good", "Employee", "goodemployee@email.com", emp);
		
		GenericDAO<User> udao = new UserDAO();
		System.out.println(udao.insert(manager));
		System.out.println(udao.insert(employ));
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();
	    
		Reimbursement reimLodge = new Reimbursement(500.00, formatter.format(date), null, "base lodging reim", employ, null, pend, lodge);
		Reimbursement reimFood = new Reimbursement(500.00, formatter.format(date), null, "base food reim", employ, null, pend, food);
		User temp = new User();
		temp.setUserId(1);
		Date dateRes = new Date();
		Reimbursement reimTravel = new Reimbursement(500.0, formatter.format(date), formatter.format(dateRes), "base travel reim", employ, manager, approved, travel);
		Reimbursement reimOther = new Reimbursement(500.0, formatter.format(date), formatter.format(dateRes), "base other reim", employ, manager, denied, other);
		
		GenericDAO<Reimbursement> reimd = new ReimDAO();
		
		System.out.println(reimd.insert(reimLodge));
		System.out.println(reimd.insert(reimFood));
		System.out.println(reimd.insert(reimTravel));
		System.out.println(reimd.insert(reimOther));
		
		//dateRes = new Date();
		//Reimbursement reimFoodUpdate = new Reimbursement(3, 500.00, formatter.format(date), formatter.format(dateRes), "base food reim", employ, manager, pend, food);
		//reimd.update(reimFoodUpdate);
	}
	
}
