package com.antibry.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.antibry.recruitment.domain.Applicant;
import com.antibry.recruitment.domain.EmploymentInformation;

public interface EmploymentInformationRepository extends JpaRepository<EmploymentInformation, Long> {

	public List<EmploymentInformation> findAllByApplicant(Applicant applicant);

	public Long deleteByApplicant(Applicant applicant);
	
	public List<EmploymentInformation> findByApplicantAndId(Applicant applicant, Long id);
}
