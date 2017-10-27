package com.antibry.recruitment.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="educ_info")
public class EducationInformation {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String schoolName;
//	replace by domain
	private String educationalLevel;
	private Date startDate;
	private Date endDate;
	
	@JoinColumn(name="applicant_id")
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	private Applicant applicant;
	
	@Transient
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	public EducationInformation() { }

	public EducationInformation(String schoolName, String educationalLevel, String startDateStr, String endDateStr,
			Applicant applicant) throws ParseException {
		super();
		this.schoolName = schoolName;
		this.educationalLevel = educationalLevel;
		this.startDate = format.parse(startDateStr);
		this.endDate = format.parse(endDateStr);
		this.applicant = applicant;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getEducationalLevel() {
		return educationalLevel;
	}

	public void setEducationalLevel(String educationalLevel) {
		this.educationalLevel = educationalLevel;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStartDateAsShort() {
		return format.format(this.startDate);
	}
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getEndDateAsShort() {
		return format.format(this.endDate);
	}
	
	@NotNull
	public Applicant getApplicant() {
		return applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}
	
}
