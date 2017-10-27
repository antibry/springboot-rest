package com.antibry.recruitment.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity(name="project")
public class Project {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotNull
	private String name;
	private Date startDate;
	private Date endDate;
	private boolean isGIA;

	@Transient
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	public Project() { }

	public Project(String name, String startDateStr, String endDateStr, boolean isGIA) throws ParseException {
		super();
		this.name = name;
		this.startDate = format.parse(startDateStr);
		this.endDate = format.parse(endDateStr);
		this.isGIA = isGIA;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public boolean isGIA() {
		return isGIA;
	}

	public void setGIA(boolean isGIA) {
		this.isGIA = isGIA;
	}
	
	
}