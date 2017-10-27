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
import com.antibry.recruitment.domain.EmploymentInformation;
import com.antibry.recruitment.service.ApplicantService;

@RestController
@RequestMapping("/api")
public class EmploymentInformationController {

	@Autowired
	ApplicantService applicantService;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping("/applicant/{applicantId}/employmentinfo")
	public List<EmploymentInformation> getAll(@PathVariable Long applicantId) {
		Applicant applicant = applicantService.findById(applicantId);
		
		return applicantService.getAllEmploymentInfo(applicant);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping("/applicant/{applicantId}/employmentinfo/{id}")
	public ResponseEntity<EmploymentInformation> get(@PathVariable Long id, @PathVariable Long applicantId) {
		HttpStatus status;
		
		EmploymentInformation empInfo = applicantService.getEmploymentInfoById(id);
		
		if (empInfo != null) {
			if (empInfo.getApplicant().getId() != applicantId) status = HttpStatus.NOT_FOUND;
			else status = HttpStatus.OK;
		} else status = HttpStatus.NOT_FOUND;
		
		return new ResponseEntity<EmploymentInformation>(empInfo, status);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@Transactional
	@RequestMapping(value="/applicant/{applicantId}/employmentinfo", method=RequestMethod.POST)
	public ResponseEntity<?> add(@PathVariable Long applicantId, @RequestBody EmploymentInformation employmentInfo) {
		Applicant applicant = applicantService.findById(applicantId);
		
		if (applicant != null) applicantService.addEmploymentInfo(applicant, employmentInfo);
		
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}
	@PreAuthorize("hasAuthority('ADMIN')")
	@Transactional
	@RequestMapping(value="/applicant/{applicantId}/employmentinfo/batch", method=RequestMethod.POST)
	public ResponseEntity<?> add(@PathVariable Long applicantId, @RequestBody List<EmploymentInformation> employmentInfoList) {
		Applicant applicant = applicantService.findById(applicantId);
		
		if (applicant == null) 
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		
		employmentInfoList.stream().forEach( empInfo -> {
			applicantService.addEmploymentInfo(applicant, empInfo);
		});
		
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@Transactional
	@RequestMapping(value="/applicant/{applicantId}/employmentinfo/{id}", method=RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable Long applicantId, @PathVariable Long id, 
			@RequestBody EmploymentInformation employmentInfo) {
		Applicant applicant = applicantService.findById(applicantId);
		
		if (applicant == null) return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
	
		EmploymentInformation target = applicantService.getEmploymentInfoById(id);
		
		if (target != null) {
			if (target.getApplicant().getId() == applicantId) {
				applicantService.updateEmploymentInfo(target, employmentInfo);
			} else return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		} else return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@Transactional
	@RequestMapping(value="/applicant/{applicantId}/employmentinfo/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Long applicantId, @PathVariable Long id) {
		Applicant applicant = applicantService.findById(applicantId);
		
		if (applicant == null) return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		
		EmploymentInformation employmentInfo = applicantService.getEmploymentInfoById(id);
		
		if (employmentInfo == null) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);			
		} else {
			if (employmentInfo.getApplicant().getId() != applicantId) 
				return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		
		applicantService.deleteEmploymentInfo(id);
		
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@Transactional
	@RequestMapping(value="/applicant/{applicantId}/employmentinfo", method=RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Long applicantId) {
		Applicant applicant = applicantService.findById(applicantId);
		
		if (applicant == null) return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		
		applicantService.deleteAllEmploymentInfo(applicant);
		
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
}
