package com.revature.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.dao.GenericDAO;
import com.revature.dao.ReimDAO;
import com.revature.dao.UserDAO;
import com.revature.models.ReimStatus;
import com.revature.models.ReimType;
import com.revature.models.Reimbursement;

public class ReimDTO {
	
	/**
	 * Meant for lighter transfer of data and Object Mapping to JSON
	 */
	
	private String reimId;
	private String amount;
	private String submittedTime;
	private String resolvedTime;
	private String description;
	private String author;
	private String resolver;
	private String status;
	private String type;
	
	public ReimDTO() {
		super();
	}
	
	public ReimDTO(String reimId, String amount, String submittedTime, String resolvedTime,
			String description, String author, String resolver, String status, String type) {
		this.amount = amount;
		this.reimId = reimId;
		this.submittedTime = submittedTime;
		this.resolvedTime = resolvedTime;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
		
	}
	
	public ReimDTO(String amount, String submittedTime, String resolvedTime,
			String description, String author, String resolver, String status, String type) {
		this.amount = amount;
		this.submittedTime = submittedTime;
		this.resolvedTime = resolvedTime;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
		
	}
	
	public ReimDTO(Reimbursement reim) {
		
		this.amount = Double.toString(reim.getAmount());
		this.reimId = Integer.toString(reim.getReimId());
		this.submittedTime = reim.getSubmittedTime();
		
		if (reim.getResolvedTime() == null) {
			this.resolvedTime = "N/A";
		} else {
			this.resolvedTime = reim.getResolvedTime();
		}
		
		if (reim.getDescription() == null){
			this.description = "None";
		} else {
			this.description = reim.getDescription();
		}
		
		this.author = reim.getAuthor().getUsername();
		
		if (reim.getResolver() == null) {
			this.resolver = "N/A";
		} else {
			this.resolver = reim.getResolver().getUsername();
		}
		
		this.status = reim.getStatus().getStatus();
		this.type = reim.getType().getType();
	}
	
	/**
	 * 
	 * @return	Returns Reimbursement Object of this ReimDTO instance or null
	 */
	@JsonIgnore
	public Reimbursement getReimInstance() {
		GenericDAO<Reimbursement> reimd = new ReimDAO();
		return getReimInstance(reimd);
	}
	
	/**
	 * Used for Testing or already instantiated DAO
	 * 	- Used for Mocking in this project
	 * 
	 * @param rd - DAO to get Reimbursement From
	 * @return	 - Reimbursement Object of this ReimDTO instance or null
	 */
	@JsonIgnore
	public Reimbursement getReimInstance(GenericDAO<Reimbursement> rd) {
		GenericDAO<Reimbursement> reimd = rd;
		return reimd.selectById(Integer.parseInt(reimId));
	}
	
	/**
	 * Used when creating/inserting Reimbursements into DB
	 * Creates a new Reimbursement object without a generated ID and not yet in DB
	 * 
	 * @return	Reimbursement object created from this ReimDTO
	 */
	public Reimbursement createReimInstance(UserDAO userd) {
		Reimbursement reim = new Reimbursement();
		
		reim.setAmount(Double.parseDouble(this.amount));
		reim.setSubmittedTime(this.submittedTime);
		
		if (!this.resolvedTime.equals("N/A")) {
			reim.setResolvedTime(this.resolvedTime);
		}
		
		reim.setDescription(this.description);
		
		reim.setAuthor(userd.selectById(Integer.parseInt(this.author)));
		
		if (!this.resolver.equals("N/A")) {
			reim.setResolver(userd.selectByParam("user_id", this.resolver));
		}
		
		ReimType rt = new ReimType(this.type);
		reim.setType(rt);
		
		ReimStatus rs = new ReimStatus(this.status);
		reim.setStatus(rs);
		
		return reim;
	}

	public String getReimId() {
		return reimId;
	}

	public void setReimId(String reimId) {
		this.reimId = reimId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getSubmittedTime() {
		return submittedTime;
	}

	public void setSubmittedTime(String submittedTime) {
		this.submittedTime = submittedTime;
	}

	public String getResolvedTime() {
		return resolvedTime;
	}

	public void setResolvedTime(String resolvedTime) {
		this.resolvedTime = resolvedTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getResolver() {
		return resolver;
	}

	public void setResolver(String resolver) {
		this.resolver = resolver;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
