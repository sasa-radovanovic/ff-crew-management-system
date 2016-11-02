package webrequests;

public class AdvancedSearchCrew {
	
	private String criteriaParam;
	
	private String criteriaValue;
	
	

	public AdvancedSearchCrew() {
		super();
	}

	public AdvancedSearchCrew(String criteriaParam, String criteriaValue) {
		super();
		this.criteriaParam = criteriaParam;
		this.criteriaValue = criteriaValue;
	}

	/**
	 * @return the criteriaParam
	 */
	public String getCriteriaParam() {
		return criteriaParam;
	}

	/**
	 * @param criteriaParam the criteriaParam to set
	 */
	public void setCriteriaParam(String criteriaParam) {
		this.criteriaParam = criteriaParam;
	}

	/**
	 * @return the criteriaValue
	 */
	public String getCriteriaValue() {
		return criteriaValue;
	}

	/**
	 * @param criteriaValue the criteriaValue to set
	 */
	public void setCriteriaValue(String criteriaValue) {
		this.criteriaValue = criteriaValue;
	}
	
}
