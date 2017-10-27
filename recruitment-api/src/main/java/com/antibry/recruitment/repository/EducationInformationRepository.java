package com.antibry.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.antibry.recruitment.domain.Applicant;
import com.antibry.recruitment.domain.EducationInformation;

public interface EducationInformationRepository extends JpaRepository<EducationInformation, Long> {

	public List<EducationInformation> findAllByApplicant(Applicant applicant);
	
	public Long deleteByApplicant(Applicant applicant);
	
	public List<EducationInformation> findByApplicantAndId(Applicant applicant, Long id);
}
