package com.antibry.recruitment.web;
import java.util.ArrayList;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antibry.recruitment.domain.Applicant;
import com.antibry.recruitment.domain.EducationInformation;
import com.antibry.recruitment.service.ApplicantService;

@RestController
@RequestMapping("/api")
public class EducationInformationController {
	
	@Autowired
	ApplicantService applicantService;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/applicant/{applicantId}/educinfo", method=RequestMethod.GET)
	public List<EducationInformation> getAllByApplicant(@PathVariable Long applicantId) {
		List<EducationInformation> educInfoList = new ArrayList<EducationInformation>();
		
		Applicant applicant = applicantService.findById(applicantId);
		
		if (applicant != null) {
			educInfoList = applicantService.getAllEducInfo(applicant);
		}
		
		return educInfoList;
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/applicant/{applicantId}/educinfo/{id}", method=RequestMethod.GET)
	public ResponseEntity<EducationInformation> get(@PathVariable Long id, @PathVariable Long applicantId) {
		HttpStatus status;
		List<EducationInformation> educInfoList = applicantService.getEducInfo(applicantId, id);
		
		if (!educInfoList.isEmpty()) status = HttpStatus.NOT_FOUND;
		else status = HttpStatus.OK;
		
		return new ResponseEntity<EducationInformation>(educInfoList.get(0), status);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@Transactional
	@RequestMapping(value="/applicant/{applicantId}/educinfo", method=RequestMethod.POST)
	public ResponseEntity<EducationInformation> add(@PathVariable Long applicantId, @RequestBody EducationInformation educInfo) {
		Applicant applicant = applicantService.findById(applicantId);
		
		if (applicant != null) applicantService.addEducInfo(applicant, educInfo);
		
		return new ResponseEntity<EducationInformation>(educInfo, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@Transactional
	@RequestMapping(value="/applicant/{applicantId}/educinfo/batch", method=RequestMethod.POST)
	public ResponseEntity<?> add(@PathVariable Long applicantId, @RequestBody List<EducationInformation> educInfoList) {
		Applicant applicant = applicantService.findById(applicantId);
		
		if (applicant != null) {
			educInfoList.stream().forEach(educInfo -> {
				applicantService.addEducInfo(applicant, educInfo);
			});
		}
		
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@Transactional
	@RequestMapping(value="/applicant/{applicantId}/educinfo/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @PathVariable Long applicantId,@RequestBody EducationInformation newEducInfo) {
		HttpStatus status;
		
		EducationInformation educInfo = applicantService.getEducInfoById(id);
		
		if (educInfo != null) {
			if (educInfo.getApplicant().getId() == applicantId) {
				applicantService.updateEducInfo(educInfo, newEducInfo);
				
				status = HttpStatus.OK;
			} else status = HttpStatus.NOT_FOUND;
		} else status = HttpStatus.NOT_FOUND;
		
		return new ResponseEntity<Object>(status);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@Transactional
	@RequestMapping(value="/applicant/{applicantId}/educinfo/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Long applicantId, @PathVariable Long id) {
		Applicant applicant = applicantService.findById(applicantId);
		if (applicant == null) return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		
		EducationInformation educInfo = applicantService.getEducInfoById(id);
		
		if (educInfo == null) return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		else {
			if (educInfo.getApplicant().getId() != applicantId)
				return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
		applicantService.deleteEducInfo(id);
		
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@Transactional
	@RequestMapping(value="/applicant/{applicantId}/educinfo", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteAll(@PathVariable Long applicantId) {
		Applicant applicant = applicantService.findById(applicantId);
		
		if (applicant == null) return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		
		applicantService.deleteAllEducInfo(applicant);
		
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
}
