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
import com.revature.utils.PasswordHash;

@Entity
@Table(name="App_User")
public class User implements PasswordHash {
	
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int userId;
	
	@Column(name="username", unique=true, nullable=false)
	private String username;
	
	@Column(name="password", nullable=false)
	private String password;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="email", unique=true, nullable=false)
	private String email;
	
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="Role_FK")
	@JsonIgnore
	private Role role;
	
	public User() {
		super();
	}

	public User(String username, String password, String firstName, String lastName, String email, Role role) {
		super();
		this.username = username;
		setPassword(password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
	}
	
	public User(int userId, String username, String password, String firstName, String lastName, String email, Role role) {
		super();
		this.userId = userId;
		this.username = username;
		setPassword(password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}

	// hash password before assigning
	public void setPassword(String password) {
		this.password = PasswordHash.generateHash(password);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email + ", role=" + role + "]";
	}	
	
}
