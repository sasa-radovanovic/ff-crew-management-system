package com.frequentflyer.cms.models.helpers;

public class CrewRoute {

	private String routeId;
	
	private String localDepartureTime;
	
	private double length;
	
	private String departure;
	
	private String arrival;
	
	private String flightNumber;

	public CrewRoute() {
		super();
	}

	public CrewRoute(String routeId, String localDepartureTime, 
			double length, String departure, String arrival, String flightNumber) {
		super();
		this.routeId = routeId;
		this.localDepartureTime = localDepartureTime;
		this.length = length;
		this.departure = departure;
		this.arrival = arrival;
		this.flightNumber = flightNumber;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getLocalDepartureTime() {
		return localDepartureTime;
	}

	public void setLocalDepartureTime(String localDepartureTime) {
		this.localDepartureTime = localDepartureTime;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
}
