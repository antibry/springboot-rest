package com.antibry.recruitment.web;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antibry.recruitment.domain.Applicant;
import com.antibry.recruitment.service.ApplicantService;

@RestController
@RequestMapping("/api")
public class ApplicantController {

	@Autowired
	ApplicantService applicantService;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping("/applicant/all")
	public List<Applicant> getAll() {
		return applicantService.findAll();
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@Transactional
	@RequestMapping(value="/applicant", method=RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody Applicant applicant) {
		
		if (!applicant.getEducationInformationList().isEmpty()) {
			applicant.getEducationInformationList().forEach(educinfo -> educinfo.setApplicant(applicant));
		}
		
		if (!applicant.getEmploymentInformationList().isEmpty()) {
			applicant.getEmploymentInformationList().forEach(employmentinfo -> employmentinfo.setApplicant(applicant));
		}
		
		applicantService.addApplicant(applicant);
		
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@Transactional
	@RequestMapping(value="/applicant/batch", method=RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody List<Applicant> applicants) {
		applicants.stream().forEach(applicant -> {
			if (!applicant.getEducationInformationList().isEmpty()) {
				applicant.getEducationInformationList().forEach(educinfo -> educinfo.setApplicant(applicant));
			}
			
			if (!applicant.getEmploymentInformationList().isEmpty()) {
				applicant.getEmploymentInformationList().forEach(employmentinfo -> employmentinfo.setApplicant(applicant));
			}
			
			applicantService.addApplicant(applicant);
		});
		
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@Transactional
	@RequestMapping(value="/applicant/{id}", method=RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Applicant applicant) {
		Applicant applicantQry = applicantService.findById(id);
		
		if (applicantQry == null) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
		applicantService.updateApplicant(applicant, applicantQry);
		
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@Transactional
	@RequestMapping(value="/applicant/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		Applicant applicant = applicantService.findById(id);
		
		if (applicant == null) return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		
		applicantService.deleteApplicantById(id);
		
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping("/applicant/{id}")
	public ResponseEntity<?> get(@PathVariable("id") Long id) {
		Applicant applicant = applicantService.findById(id);
		
		if (applicant == null) return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<Applicant>(applicant, HttpStatus.FOUND);
	}
}
