package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Reim_Type")
public class ReimType {
	
	@Id
	@Column(name="type_id")
	private int typeId;
	
	@Column(name="type_name")
	private String type;
	
	public ReimType() {
		super();
	}
	
	public ReimType(int typeId) {
		super();
		this.setTypeId(typeId);
	}
	
	public ReimType(String type) {
		super();
		this.setType(type);
		
		if (type.equalsIgnoreCase("LODGE")) {
			this.typeId = 1;
		} else if (type.equalsIgnoreCase("FOOD")){
			this.typeId = 2;
		} else if (type.equalsIgnoreCase("TRAVEL")){
			this.typeId = 3;
		} else if (type.equalsIgnoreCase("OTHER")){
			this.typeId = 4;
		}
	}
	
	public ReimType(int typeId, String type) {
		super();
		this.setTypeId(typeId);
		this.setType(type);
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ReimType [typeId=" + typeId + ", type=" + type + "]";
	}
	
	
}
