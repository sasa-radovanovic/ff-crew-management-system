package webrequests;

import java.io.Serializable;

public class AirplaneTypeRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1880076352977433064L;
	
	private String manufacturer;
	
	private String type;
	
	

	public AirplaneTypeRequest() {
		super();
	}

	public AirplaneTypeRequest(String manufacturer, String type) {
		super();
		this.manufacturer = manufacturer;
		this.type = type;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
