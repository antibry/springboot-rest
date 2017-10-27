package com.antibry.recruitment.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.antibry.recruitment.domain.Applicant;
import com.antibry.recruitment.domain.EducationInformation;
import com.antibry.recruitment.domain.EmploymentInformation;
import com.antibry.recruitment.repository.ApplicantRepository;
import com.antibry.recruitment.repository.EducationInformationRepository;
import com.antibry.recruitment.repository.EmploymentInformationRepository;
import com.antibry.recruitment.utils.Gender;

@Service
public class ApplicantService {

	private static final Logger log = LoggerFactory.getLogger(ApplicantService.class);
	
	@Autowired
	ApplicantRepository applicantRepo;
	
	@Autowired
	EducationInformationRepository educInfoRepo;
	
	@Autowired
	EmploymentInformationRepository employmentInfoRepo;
	
	public void insertData() throws ParseException {
		log.info("> Inserting data...");
		
		applicantRepo.save(new Applicant("Jenjuro", "Joko", "J.", "Jamon", "Manila", "1989-09-06", Gender.MALE));
		applicantRepo.save(new Applicant("Galvin", "Golokov", "G.", "Golovkin", "Malabon", "1990-10-10", Gender.MALE));
		applicantRepo.save(new Applicant("Mamoru", "Mako", "M.", "Memo", "Palawan", "1961-01-06", Gender.MALE));
		
		log.info("> Done.");
	}
	
	public List<Applicant> findAll() {
		return applicantRepo.findAll();
	}
	
	@Transactional
	public void addApplicant(Applicant applicant) {
		applicantRepo.save(applicant);
	}
	
	public Applicant findById(Long id) {
		Applicant applicant = applicantRepo.findOne(id);
		
		return applicant;
	}
	
	@Transactional
	public void updateApplicant(Applicant srcApplicant, Applicant targetApplicant) {
		targetApplicant.setFirstName(srcApplicant.getFirstName());
		targetApplicant.setMiddleName(srcApplicant.getMiddleName());
		targetApplicant.setMiddleInitial(srcApplicant.getMiddleInitial());
		targetApplicant.setLastName(srcApplicant.getLastName());
		targetApplicant.setAddress(srcApplicant.getAddress());
		targetApplicant.setBirthDate(srcApplicant.getBirthDate());
		targetApplicant.setGender(srcApplicant.getGender());
		
		applicantRepo.save(targetApplicant);
	}
	
	@Transactional
	public void deleteApplicant(Applicant applicant) {
		applicantRepo.delete(applicant);
	}
	
	@Transactional
	public void deleteApplicantById(Long id) {
		applicantRepo.delete(id);
	}
	
	public List<EducationInformation> getAllEducInfo(Applicant applicant) {
		return educInfoRepo.findAllByApplicant(applicant);
	}
	
	public EducationInformation getEducInfoById(Long id) {
		return educInfoRepo.findOne(id);
	}
	
	public List<EducationInformation> getEducInfo(Long applicantId, Long id) {
		Applicant applicant = findById(applicantId);
		List<EducationInformation> educinfoList = new ArrayList<EducationInformation>();
		
		if (!(applicant == null)) return null;
		
		educinfoList = educInfoRepo.findByApplicantAndId(applicant, id);
		
		return educinfoList;
	}
	
	@Transactional
	public void addEducInfo(Applicant applicant, EducationInformation educInfo) {
		educInfo.setApplicant(applicant);
		educInfoRepo.save(educInfo);
	}
	
	@Transactional
	public void updateEducInfo(Long id, EducationInformation newEducInfo) {
		EducationInformation educInfo = educInfoRepo.findOne(id);
		
		educInfo.setSchoolName(newEducInfo.getSchoolName());
		educInfo.setEducationalLevel(newEducInfo.getEducationalLevel());
		educInfo.setStartDate(newEducInfo.getStartDate());
		educInfo.setEndDate(newEducInfo.getEndDate());
		
		educInfoRepo.save(educInfo);
	}
	
	@Transactional
	public void updateEducInfo(EducationInformation target, EducationInformation source) {
		target.setSchoolName(source.getSchoolName());
		target.setEducationalLevel(source.getEducationalLevel());
		target.setStartDate(source.getStartDate());
		target.setEndDate(source.getEndDate());
		
		educInfoRepo.save(target);
	}
	
	@Transactional
	public void deleteEducInfo(Long id) {
		educInfoRepo.delete(id);
	}
	
	@Transactional
	public void deleteAllEducInfo(Applicant applicant) {
		educInfoRepo.deleteByApplicant(applicant);
	}
	
	public List<EmploymentInformation> getAllEmploymentInfo(Applicant applicant) {
		return employmentInfoRepo.findAllByApplicant(applicant);
	}
	
	public EmploymentInformation getEmploymentInfoById(Long id) {
		return employmentInfoRepo.findOne(id);
	}
	
	@Transactional
	public void addEmploymentInfo(Applicant applicant, EmploymentInformation employmentInfo) {
		employmentInfo.setApplicant(applicant);
		employmentInfoRepo.save(employmentInfo);
	}
	
	@Transactional
	public void updateEmploymentInfo(Long id, EmploymentInformation newEmploymentInfo) {
		EmploymentInformation employmentInfo = employmentInfoRepo.findOne(id);
		
		employmentInfo.setCompanyName(newEmploymentInfo.getCompanyName());
		employmentInfo.setCompanyAddress(newEmploymentInfo.getCompanyAddress());
		employmentInfo.setPosition(newEmploymentInfo.getPosition());
		employmentInfo.setStartDate(newEmploymentInfo.getStartDate());
		employmentInfo.setEndDate(newEmploymentInfo.getEndDate());
		
		employmentInfoRepo.save(employmentInfo);
	}

	@Transactional
	public void updateEmploymentInfo(EmploymentInformation target, EmploymentInformation source) {
		target.setCompanyName(source.getCompanyName());
		target.setCompanyAddress(source.getCompanyAddress());
		target.setPosition(source.getPosition());
		target.setStartDate(source.getStartDate());
		target.setEndDate(source.getEndDate());
		
		employmentInfoRepo.save(target);
	}
	
	@Transactional
	public void deleteEmploymentInfo(Long id) {
		employmentInfoRepo.delete(id);
	}
	
	@Transactional
	public void deleteAllEmploymentInfo(Applicant applicant) {
		employmentInfoRepo.deleteByApplicant(applicant);
	}
}
