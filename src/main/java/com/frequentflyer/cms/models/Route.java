package com.frequentflyer.cms.models;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.data.annotation.Id;

import com.frequentflyer.cms.Constants;

public class Route {

	@Id
    private String id;
	
	private String airplaneId;
	
	private String departure;
	
	private String arrival;
	
	private double length;
	
	private HashMap<String, ArrayList<String>> assignedCrew;
	
	private String localDepartureTime;
	
	private String localArrivalTime;
	
	private String flightNumber;

	public Route() {
		super();
	}

	public Route(String airplaneId, String departure, String arrival, double length, HashMap<String, ArrayList<String>> assignedCrew,
			String localDepartureTime, String localArrivalTime, String flightNumber) {
		super();
		this.airplaneId = airplaneId;
		this.departure = departure;
		this.arrival = arrival;
		this.length = length;
		this.assignedCrew = assignedCrew;
		this.localDepartureTime = localDepartureTime;
		this.localArrivalTime = localArrivalTime;
		this.flightNumber = flightNumber;
	}

	public Route(String airplaneId, String departure, String arrival, double length, String localDepartureTime,
			String localArrivalTime, ArrayList<String> flightCrew, ArrayList<String> cabinCrew,
			String flightNumber) {
		super();
		this.airplaneId = airplaneId;
		this.departure = departure;
		this.arrival = arrival;
		this.length = length;
		this.localDepartureTime = localDepartureTime;
		this.localArrivalTime = localArrivalTime;
		this.assignedCrew = new HashMap<>();
		this.assignedCrew.put(Constants.CREW_FLIGHT_CREW, flightCrew);
		this.assignedCrew.put(Constants.CREW_CABIN_CREW, cabinCrew);
		this.flightNumber = flightNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAirplaneId() {
		return airplaneId;
	}

	public void setAirplaneId(String airplaneId) {
		this.airplaneId = airplaneId;
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

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public HashMap<String, ArrayList<String>> getAssignedCrew() {
		return assignedCrew;
	}

	public void setAssignedCrew(HashMap<String, ArrayList<String>> assignedCrew) {
		this.assignedCrew = assignedCrew;
	}

	public String getLocalDepartureTime() {
		return localDepartureTime;
	}

	public void setLocalDepartureTime(String localDepartureTime) {
		this.localDepartureTime = localDepartureTime;
	}

	public String getLocalArrivalTime() {
		return localArrivalTime;
	}

	public void setLocalArrivalTime(String localArrivalTime) {
		this.localArrivalTime = localArrivalTime;
	}

	@Override
	public String toString() {
		return "Route [id=" + id + ", airplaneId=" + airplaneId + ", departure=" + departure + ", arrival=" + arrival
				+ ", length=" + length + ", assignedCrew=" + assignedCrew + ", localDepartureTime=" + localDepartureTime
				+ ", localArrivalTime=" + localArrivalTime + "]";
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	
	
	
}
