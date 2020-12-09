package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Reim_Status")
public class ReimStatus {
	
	@Id
	@Column(name="status_id")
	private int statusId;
	
	@Column(name="status_name")
	private String status;
	
	public ReimStatus() {
		super();
	}
	
	public ReimStatus(int statusId) {
		super();
		this.statusId = statusId;
	}
	
	public ReimStatus(String status) {
		super();
		this.status = status;
		if (status.equals("PENDING")) {
			this.statusId = 1;
		} else if (status.equals("APPROVED")) {
			this.statusId = 2;
		} else if (status.equals("DENIED")) {
			this.statusId = 3;
		}
	}
	
	public ReimStatus(int statusId, String status) {
		super();
		this.statusId = statusId;
		this.status = status;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ReimStatus [statusId=" + statusId + ", status=" + status + "]";
	}
	
	
	
}
