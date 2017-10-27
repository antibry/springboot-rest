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
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity(name="employment_info")
public class EmploymentInformation {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotNull
	private String companyName;
//	update address to region / city
	private String companyAddress;
	private String position;
	@Past
	private Date startDate;
	private Date endDate;
	private boolean isRegular;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="applicant_id")
	@JsonIgnore
	Applicant applicant;
	
	@Transient
	private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	public EmploymentInformation() { }

	public EmploymentInformation(String companyName, String companyAddress, String position, String startDateStr,
			String endDateStr, boolean isRegular, Applicant applicant) throws ParseException {
		super();
		this.companyName = companyName;
		this.companyAddress = companyAddress;
		this.position = position;
		this.startDate = format.parse(startDateStr);
		this.endDate = format.parse(endDateStr);
		this.isRegular = isRegular;
		this.applicant = applicant;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isRegular() {
		return isRegular;
	}

	public void setRegular(boolean isRegular) {
		this.isRegular = isRegular;
	}

	public Applicant getApplicant() {
		return applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}

	@Override
	public String toString() {
		return "EmploymentInformation [id=" + id + ", companyName=" + companyName + ", companyAddress=" + companyAddress
				+ ", position=" + position + ", startDate=" + startDate + ", endDate=" + endDate + ", isRegular="
				+ isRegular + ", applicant=" + applicant + "]";
	}
}
