package com.antibry.recruitment.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

import com.antibry.recruitment.utils.Gender;
import com.antibry.recruitment.web.serializer.CommonObjectListSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity(name="applicant")
public class Applicant {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotNull(message="Firstname is required")
	@NotBlank(message="Firstname is required")
	private String firstName;
	private String middleName;
	private String middleInitial;
	@NotNull(message="Lastname is required")
	@NotBlank(message="Lastname is required")
	private String lastName;
	private String address;
	@NotNull(message="Birthdate is required")
	@Past(message="Birthdate should not be future date")
	private Date birthDate;
	
	@Enumerated(EnumType.STRING)
	@NotNull(message="Gender is required")
	private Gender gender;
	
	@OneToMany(mappedBy="applicant", cascade=CascadeType.ALL, orphanRemoval=true)
	@JsonSerialize(using=CommonObjectListSerializer.class)
	private List<EducationInformation> educationInformationList = new ArrayList<EducationInformation>();
	
	@OneToMany(mappedBy="applicant", cascade=CascadeType.ALL, orphanRemoval=true)
	@JsonSerialize(using=CommonObjectListSerializer.class)
	private List<EmploymentInformation> employmentInformationList = new ArrayList<EmploymentInformation>();

	@Transient
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	public Applicant() { }

	public Applicant(String firstName, String middleName, String middleInitial, String lastName, String address,
			String birthDateStr, Gender gender) throws ParseException {
		this.firstName = firstName;
		this.middleName = middleName;
		this.middleInitial = middleInitial;
		this.lastName = lastName;
		this.address = address;
		this.birthDate = format.parse(birthDateStr);
		this.gender = gender;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getBirthDateasShort() {
		return format.format(birthDate);
	}
	
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public List<EducationInformation> getEducationInformationList() {
		return educationInformationList;
	}

	public void setEducationInformationList(List<EducationInformation> educationInformation) {
		this.educationInformationList = educationInformation;
	}

	public List<EmploymentInformation> getEmploymentInformationList() {
		return employmentInformationList;
	}

	public void setEmploymentInformationList(List<EmploymentInformation> employmentInformationList) {
		this.employmentInformationList = employmentInformationList;
	}
	
	@Override
	public String toString() {
		return "Applicant [id=" + id + ", firstName=" + firstName + ", middleName=" + middleName + ", middleInitial="
				+ middleInitial + ", lastName=" + lastName + ", address=" + address + ", birthDate=" + birthDate
				+ ", gender=" + gender + "]";
	}
}
