package com.antibry.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.antibry.recruitment.domain.acl.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

	UserAccount findByUsername(String username);
}
