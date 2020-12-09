package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Role")
public class Role {
	
	@Id
	@Column(name="role_id")
	private int roleId;
	
	@Column(name="role_name")
	private String role;
	
	
	public Role() {
		super();
	}
	
	public Role(String role) {
		this.role = role;
		if (role.equals("MANAGER")) {
			this.roleId = 1;
		}
		else if (role.equals("EMPLOYEE")) {
			this.roleId = 2;
		}
	}
	
	public Role(int roleId, String role) {
		this.roleId = roleId;
		this.role = role;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", role=" + role + "]";
	}
	
	
	
}
