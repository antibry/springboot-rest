package com.antibry.recruitment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.antibry.recruitment.domain.acl.Role;
import com.antibry.recruitment.domain.acl.UserAccount;
import com.antibry.recruitment.repository.RoleRepository;
import com.antibry.recruitment.repository.UserAccountRepository;

@Service
public class AclService {

	@Autowired
	UserAccountRepository userRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	public void addAccount(UserAccount account) {
		userRepo.save(account);
	}
	
	public UserAccount getAccount(Long id) {
		return userRepo.findOne(id);
	}
	
	public List<UserAccount> getAllAccounts() {
		return userRepo.findAll();
	}
	
	public void updateAccountUsername(UserAccount source, UserAccount target) {
		target.setUsername(source.getUsername());
	}
	
	public void updateAccountPassword(UserAccount account, String password) {
		account.setPassword(passwordEncoder.encode(password));
		userRepo.save(account);
	}
	
	public void disableAccount(UserAccount account, String password) {
		account.setEnabled(false);
		userRepo.save(account);
	}
	
	public void enableAccount(UserAccount account, String password) {
		account.setEnabled(true);
		userRepo.save(account);
	}
	
	public void deleteAccount(UserAccount account) {
		userRepo.delete(account);
	}
	
	public void addRole(Role role) {
		roleRepo.save(role);
	}
	
	public Role getRole(Long id) {
		return roleRepo.findOne(id);
	}
	
	public List<Role> getAllRoles() {
		return roleRepo.findAll();
	}
	
	public void updateRole(Role source, Role target) {
		target.setAuthority(target.getAuthority());
		target.setDescription(source.getDescription());
	}
	
	public void deleteRole(Role role) {
		roleRepo.delete(role);
	}
	
	public void assignRolesToAccount(UserAccount account, List<Role> roles) {
		account.setRoles(roles);
	}
	
	public void assignRoleToAccount(UserAccount account, Role role) {
		List<Role> assignedRoles = account.getRoles();
		
		Role duplicateRole = assignedRoles.stream().filter( r -> r.getId() == role.getId()).findAny().orElse(null);
		
		if (duplicateRole != null) {
			assignedRoles.add(role);
			account.setRoles(assignedRoles);
			
			userRepo.save(account);
		}
	}
}
