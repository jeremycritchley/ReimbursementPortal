package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import com.revature.dao.GenericDAO;
import com.revature.dao.ReimDAO;
import com.revature.dao.StatusDAO;
import com.revature.dao.TypeDAO;
import com.revature.dao.UserDAO;
import com.revature.dto.ReimDTO;
import com.revature.dto.UserDTO;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.utils.DateStringify;

public class ManagerServiceImpl extends EmployeeServiceImpl implements ManagerService {
	
	private static Logger log = Logger.getLogger(EmployeeServiceImpl.class);
	private GenericDAO<User> userd;
	private GenericDAO<Reimbursement> reimd;
	
	public ManagerServiceImpl() {
		super();
	}
	
	public ManagerServiceImpl(ReimDAO r) {
		super(r);
		reimd = r;
	}

	public ManagerServiceImpl(UserDAO d) {
		super(d);
		userd = d;
	}
	
	public ManagerServiceImpl(UserDAO d, ReimDAO r) {
		super();
		userd = d;
		reimd = r;
	}
	
	
	
	@Override
	public boolean approveReim(ReimDTO rdto, String resolver) {
		return updateReim(rdto, resolver, 2);
	}

	@Override
	public boolean denyReim(ReimDTO rdto, String resolver) {
		
		return updateReim(rdto, resolver, 3);
	}
	
	private boolean updateReim(ReimDTO rdto, String resolver, int status) {
		if (reimd == null) {
			reimd = new ReimDAO();
		}
		
		if (userd == null) {
			userd = new UserDAO();
		}
		int res = Integer.parseInt(resolver);
		boolean ret = false;
		Reimbursement reim = rdto.getReimInstance(reimd);
		User u = userd.selectById(res);
		if (u != null) {
			reim.setResolver(u);
			reim.setResolvedTime(DateStringify.stringifyNow());
			reim.setStatus(StatusDAO.selectById(status));
			if (rdto.getType().equalsIgnoreCase("lodge")) {
				reim.setType(TypeDAO.selectById(1));
			} else if (rdto.getType().equalsIgnoreCase("food")) {
				reim.setType(TypeDAO.selectById(2));
			} else if (rdto.getType().equalsIgnoreCase("travel")) {
				reim.setType(TypeDAO.selectById(3));
			} else if (rdto.getType().equalsIgnoreCase("other")) {
				reim.setType(TypeDAO.selectById(4));
			}
			ret = reimd.update(reim);
			if (ret) {
				log.info("UPDATED REIMBURSEMENT " + rdto.getReimId());
			} else {
				log.warn("FAILED TO UPDATE REIMBURSEMENT " + rdto.getReimId());
			}
		} else {
			log.warn("FAILED TO UPDATE REIMBURSEMENT " + rdto.getReimId());
		}

		return ret;
	}

	@Override
	public List<UserDTO> viewAllEmployees() {
		if (userd == null) {
			userd = new UserDAO();
		}
		
		List<User> employees = userd.selectAll("Role_FK", "2");
		List<UserDTO> dtos = null;
		if (employees != null) {
			dtos = new ArrayList<UserDTO>();
			for (User e: employees) {
				dtos.add(new UserDTO(e));
			}
		}
		return dtos;
	}
	
	@Override
	public List<ReimDTO> viewRiemsByStatus(String ownerId, boolean resolved) {
		if (reimd == null) {
			reimd = new ReimDAO();
		}
		
		List<Reimbursement> reims = null;
		List<ReimDTO> dtos = null;
		
		if (ownerId.equals("0")) { 
			String param = "Status_FK";
			
			
			
			if (resolved)
				param = "Status_FK!";
			
			reims = reimd.selectAll(param, "1");
			
			if (reims != null) {
				dtos = new ArrayList<ReimDTO>();
				for (Reimbursement r: reims) {
					dtos.add(new ReimDTO(r));
				}
			}
		} else {
			
			String param = "Author_FK";
			String val = ownerId;
			
			reims = reimd.selectAll(param, val);
			
			if (reims != null) {
				dtos = new ArrayList<ReimDTO>();
				for (Reimbursement r: reims) {
					dtos.add(new ReimDTO(r));
				}
			}
		}
		return dtos;
		
	}
	
	public ReimDTO viewReimsById(String reim_id) {
		if (reimd == null) {
			reimd = new ReimDAO();
		}
		
		Reimbursement reim = reimd.selectById(Integer.parseInt(reim_id));
		ReimDTO rdto = null;
		if (reim != null) {
			rdto = new ReimDTO(reim);
		}
		
		return rdto;
	}
	
	public List<ReimDTO> viewAllReims() {
		if (reimd == null) {
			reimd = new ReimDAO();
		}
		
		List<Reimbursement> reims = reimd.selectAll("reim_id!", "0");
		List<ReimDTO> rdtos = null;
		if (reims != null) {
			rdtos = new ArrayList<ReimDTO>();
			for (Reimbursement i: reims) {
				rdtos.add(new ReimDTO(i));
			}
			
		}
		
		return rdtos;
	}

	
}
