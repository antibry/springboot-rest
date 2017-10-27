package com.antibry.recruitment.domain.acl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import com.antibry.recruitment.web.serializer.CommonObjectListSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity(name="user_account")
public class UserAccount {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	private boolean enabled = true;
	private boolean accountExpired = false;
	private boolean accountLocked = false;
	private boolean passwordExpired = false;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="account_role", 
		joinColumns=@JoinColumn(name="account_id", referencedColumnName="id"),
		inverseJoinColumns=@JoinColumn(name="role_id", referencedColumnName="id"))
	@JsonSerialize(using=CommonObjectListSerializer.class)
	List<Role> roles = new ArrayList<Role>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountExpired() {
		return accountExpired;
	}

	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public boolean isAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public boolean isPasswordExpired() {
		return passwordExpired;
	}

	public void setPasswordExpired(boolean passwordExpired) {
		this.passwordExpired = passwordExpired;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "AdminAccount [id=" + id + ", username=" + username + "]";
	}
}
