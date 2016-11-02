package webrequests;

import java.util.ArrayList;

public class CreateCrewRequest {
	
	private String firstName;
	
	private String lastName;
	
	private String username;
	
	private String password;
	
	private String mail;
	
	private String crewType;
	
	private ArrayList<String> languagesSpoken;
	
	private String countryOfOrigin;
	
	private String companyId;

	public CreateCrewRequest() {
		super();
	}

	public CreateCrewRequest(String firstName, String lastName, String username, String password, String mail,
			String crewType, ArrayList<String> languagesSpoken, String countryOfOrigin, String companyId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.mail = mail;
		this.crewType = crewType;
		this.languagesSpoken = languagesSpoken;
		this.countryOfOrigin = countryOfOrigin;
		this.companyId = companyId;
	}
	
	
	public CreateCrewRequest(String firstName, String lastName, String username, String mail,
			String crewType, ArrayList<String> languagesSpoken, String countryOfOrigin, String companyId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = companyId;
		this.mail = mail;
		this.crewType = crewType;
		this.languagesSpoken = languagesSpoken;
		this.countryOfOrigin = countryOfOrigin;
		this.companyId = companyId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @return the crewType
	 */
	public String getCrewType() {
		return crewType;
	}

	/**
	 * @param crewType the crewType to set
	 */
	public void setCrewType(String crewType) {
		this.crewType = crewType;
	}

	/**
	 * @return the languagesSpoken
	 */
	public ArrayList<String> getLanguagesSpoken() {
		return languagesSpoken;
	}

	/**
	 * @param languagesSpoken the languagesSpoken to set
	 */
	public void setLanguagesSpoken(ArrayList<String> languagesSpoken) {
		this.languagesSpoken = languagesSpoken;
	}

	/**
	 * @return the countryOfOrigin
	 */
	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}

	/**
	 * @param countryOfOrigin the countryOfOrigin to set
	 */
	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}

	/**
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	

}
