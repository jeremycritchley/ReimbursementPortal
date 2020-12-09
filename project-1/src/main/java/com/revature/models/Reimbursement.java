package com.revature.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Reim")
public class Reimbursement {
	
	@Id
	@Column(name="reim_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int reimId;
	
	@Column(name="reim_amount", nullable=false)
	private double amount;
	
	@Column(name="reim_submitted", nullable=false)
	private String submittedTime;
	
	@Column(name="reim_resolved")
	private String resolvedTime;
	
	@Column(name="reim_description")
	private String description;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="Author_FK")
	@JsonIgnore
	private User author;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="Resolver_FK")
	@JsonIgnore
	private User resolver;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="Status_FK")
	@JsonIgnore
	private ReimStatus status;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="Type_FK")
	@JsonIgnore
	private ReimType type;
	
	public Reimbursement() {
		super();
	}
	
	public Reimbursement(double amount, String submittedTime, String resolvedTime, String description,
			User author, User resolver, ReimStatus status, ReimType type) {
		super();
		this.amount = amount;
		this.submittedTime = submittedTime;
		this.resolvedTime = resolvedTime;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
	}

	public Reimbursement(int reimId, double amount, String submittedTime, String resolvedTime, String description,
			User author, User resolver, ReimStatus status, ReimType type) {
		super();
		this.reimId = reimId;
		this.amount = amount;
		this.submittedTime = submittedTime;
		this.resolvedTime = resolvedTime;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
	}

	public int getReimId() {
		return reimId;
	}

	public void setReimId(int reimId) {
		this.reimId = reimId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
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

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public User getResolver() {
		return resolver;
	}

	public void setResolver(User resolver) {
		this.resolver = resolver;
	}

	public ReimStatus getStatus() {
		return status;
	}

	public void setStatus(ReimStatus status) {
		this.status = status;
	}

	public ReimType getType() {
		return type;
	}

	public void setType(ReimType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Reimbursement [reimId=" + reimId + ", amount=" + amount + ", submittedTime=" + submittedTime
				+ ", resolvedTime=" + resolvedTime + ", description=" + description + ", author=" + author
				+ ", resolver=" + resolver + ", status=" + status + ", type=" + type + "]";
	}
	
	
	
}
