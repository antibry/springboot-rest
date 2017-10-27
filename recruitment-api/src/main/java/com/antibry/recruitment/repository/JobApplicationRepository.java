package com.antibry.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.antibry.recruitment.domain.JobApplication;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

}
