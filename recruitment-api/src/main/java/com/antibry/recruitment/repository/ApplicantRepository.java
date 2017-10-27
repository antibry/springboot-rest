package com.antibry.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.antibry.recruitment.domain.Applicant;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

}
