package com.antibry.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.antibry.recruitment.domain.JobPosition;

public interface JobPositionRepository extends JpaRepository<JobPosition, Long> {

}
