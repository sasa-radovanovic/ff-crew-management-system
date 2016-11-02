package com.frequentflyer.cms.models;

import org.springframework.data.annotation.Id;

public class AirplaneType  {

	@Id
    private String id;

    private String manufacturer;
    private String type;

    public AirplaneType() {}

    public AirplaneType(String manufacturer, String type) {
        this.manufacturer = manufacturer;
        this.type = type;
    }
    
    

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	@Override
    public String toString() {
        return String.format(
                "AirplaneType[id=%s, manufacturer='%s', type='%s']",
                id, manufacturer, type);
    }

}
