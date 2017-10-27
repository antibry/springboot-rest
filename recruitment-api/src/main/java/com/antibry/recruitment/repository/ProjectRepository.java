package com.antibry.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.antibry.recruitment.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}