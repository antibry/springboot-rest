package com.antibry.recruitment.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.antibry.recruitment.utils.ApplicationResult;
import com.antibry.recruitment.utils.ApplicationStatus;

@Entity(name="job_app")
public class JobApplication {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Date created;
	private Date interviewDate;
	private byte[] resumeData;
	@Min(1)
	private int waitingRank;
	
	@Enumerated(EnumType.STRING)
	private ApplicationStatus status;
	
	@Enumerated(EnumType.STRING)
	private ApplicationResult result;
	
	@ManyToOne
	@NotNull
	Applicant applicant;
	
	@Transient
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public JobApplication() { }

	public JobApplication(Date created, String interviewDateStr, byte[] resumeData, ApplicationStatus status,
			ApplicationResult result, Applicant applicant) throws ParseException {
		super();
		this.created = created;
		this.interviewDate = format.parse(interviewDateStr);
		this.resumeData = resumeData;
		this.status = ApplicationStatus.FOR_EVALUATION;
		this.applicant = applicant;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}

	public String getInterviewDateAsShort() {
		return format.format(this.interviewDate);
	}
	
	public byte[] getResumeData() {
		return resumeData;
	}

	public void setResumeData(byte[] resumeData) {
		this.resumeData = resumeData;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}

	public ApplicationResult getResult() {
		return result;
	}

	public void setResult(ApplicationResult result) {
		this.result = result;
	}

	public Applicant getApplicant() {
		return applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}

	public int getWaitingRank() {
		return waitingRank;
	}

	public void setWaitingRank(int waitingRank) {
		this.waitingRank = waitingRank;
	}
}
