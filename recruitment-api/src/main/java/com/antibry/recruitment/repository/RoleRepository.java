package com.antibry.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.antibry.recruitment.domain.acl.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
