package com.frequentflyer.cms.models;

import org.springframework.data.annotation.Id;

/**
 * 
 * Airplane model
 * 
 * @author Sasa Radovanovic
 *
 */
public class Airplane {
	
	@Id
    private String id;
	
	private AirplaneType airplaneType;
	
	private String registration;
	
	private int seatCapacity;
	
	private String deliveryDate;
	
	private String msnNumber; 
	
	public Airplane() {
		super();
	}

	public Airplane(AirplaneType airplaneType, String registration, int seatCapacity,
			String deliveryDate, String msnNumber) {
		super();
		this.airplaneType = airplaneType;
		this.registration = registration;
		this.seatCapacity = seatCapacity;
		this.deliveryDate = deliveryDate;
		this.msnNumber = msnNumber;
	}

	
	public Airplane(AirplaneType airplaneType, String registration) {
		super();
		this.airplaneType = airplaneType;
		this.registration = registration;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AirplaneType getAirplaneType() {
		return airplaneType;
	}

	public void setAirplaneType(AirplaneType airplaneType) {
		this.airplaneType = airplaneType;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public int getSeatCapacity() {
		return seatCapacity;
	}

	public void setSeatCapacity(int seatCapacity) {
		this.seatCapacity = seatCapacity;
	}
	
	
	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getMsnNumber() {
		return msnNumber;
	}

	public void setMsnNumber(String msnNumber) {
		this.msnNumber = msnNumber;
	}

	@Override
	public String toString() {
		return "Airplane [id=" + id + ", airplaneType=" + airplaneType + ", registration=" + registration
				+ ", seatCapacity=" + seatCapacity + "]";
	}
	
	
}
