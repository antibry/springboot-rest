package com.antibry.recruitment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name="job_position")
public class JobPosition {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private double salary;
	private int requiredYearsExperience;
	
	@ManyToOne
	Project project;
	
	public JobPosition() { }

	public JobPosition(String name, double salary, int requiredYearsExperience, Project project) {
		super();
		this.name = name;
		this.salary = salary;
		this.requiredYearsExperience = requiredYearsExperience;
		this.project = project;
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

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public int getRequiredYearsExperience() {
		return requiredYearsExperience;
	}

	public void setRequiredYearsExperience(int requiredYearsExperience) {
		this.requiredYearsExperience = requiredYearsExperience;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
