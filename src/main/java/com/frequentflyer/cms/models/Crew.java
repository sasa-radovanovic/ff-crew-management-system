package com.frequentflyer.cms.models;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.frequentflyer.cms.models.helpers.CrewRoute;

public class Crew {
	
	@Id
    private String id;
	
	private String firstName;
	
	private String lastName;
	
	@Indexed(unique = true)
	private String username;
	
	private String password;
	
	@Indexed(unique = true)
	private String mail;
	
	private boolean active;
	
	private ArrayList<CrewRoute> assignedRoutes;
	
	private ArrayList<CrewRoute> pastRoutes;
	
	private String crewType;
	
	private ArrayList<String> languagesSpoken;
	
	private String countryOfOrigin;
	
	private String companyId;

	public Crew() {
		super();
	}

	public Crew(String firstName, String lastName, String username, String password, String mail, boolean active,
			ArrayList<CrewRoute> assignedRoutes, String crewType, ArrayList<String> languagesSpoken,
			String countryOfOrigin, String companyId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.mail = mail;
		this.active = active;
		this.assignedRoutes = assignedRoutes;
		this.crewType = crewType;
		this.languagesSpoken = languagesSpoken;
		this.countryOfOrigin = countryOfOrigin;
		this.companyId = companyId;
		this.pastRoutes = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public ArrayList<CrewRoute> getAssignedRoutes() {
		return assignedRoutes;
	}

	public void setAssignedRoutes(ArrayList<CrewRoute> assignedRoutes) {
		this.assignedRoutes = assignedRoutes;
	}

	public String getCrewType() {
		return crewType;
	}

	public void setCrewType(String crewType) {
		this.crewType = crewType;
	}

	public ArrayList<String> getLanguagesSpoken() {
		return languagesSpoken;
	}

	public void setLanguagesSpoken(ArrayList<String> languagesSpoken) {
		this.languagesSpoken = languagesSpoken;
	}

	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}

	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public ArrayList<CrewRoute> getPastRoutes() {
		return pastRoutes;
	}

	public void setPastRoutes(ArrayList<CrewRoute> pastRoutes) {
		this.pastRoutes = pastRoutes;
	}

	@Override
	public String toString() {
		return "Crew [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", password=" + password + ", mail=" + mail + ", active=" + active + ", assignedRoutes="
				+ assignedRoutes + ", crewType=" + crewType + ", languagesSpoken=" + languagesSpoken
				+ ", countryOfOrigin=" + countryOfOrigin + ", companyId=" + companyId + "]";
	}
}
