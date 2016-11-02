package com.frequentflyer.cms.models;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

public class ScheduledRoute {
	
	@Id
    private String id;
	
	private String name;
	
	private AirplaneType airplaneType;
	
	private ArrayList<Integer> daysOfTheWeek;
	
	private String departure;
	
	private String arrival;
	
	private String localDepartureTime;
	
	private String localArrivalTime;
	
	private double length;
	
	public ScheduledRoute() {
		super();
	}

	public ScheduledRoute(String name, AirplaneType airplaneType, ArrayList<Integer> daysOfTheWeek) {
		super();
		this.name = name;
		this.airplaneType = airplaneType;
		this.daysOfTheWeek = daysOfTheWeek;
	}
	
	

	public ScheduledRoute(String name, AirplaneType airplaneType, ArrayList<Integer> daysOfTheWeek,
			String localDepartureTime, String localArrivalTime) {
		super();
		this.name = name;
		this.airplaneType = airplaneType;
		this.daysOfTheWeek = daysOfTheWeek;
		this.localDepartureTime = localDepartureTime;
		this.localArrivalTime = localArrivalTime;
	}
	
	

	public ScheduledRoute(String name, AirplaneType airplaneType, ArrayList<Integer> daysOfTheWeek,
			String localDepartureTime, String localArrivalTime, double length) {
		super();
		this.name = name;
		this.airplaneType = airplaneType;
		this.daysOfTheWeek = daysOfTheWeek;
		this.localDepartureTime = localDepartureTime;
		this.localArrivalTime = localArrivalTime;
		this.length = length;
	}
	
	public ScheduledRoute(String name, AirplaneType airplaneType, ArrayList<Integer> daysOfTheWeek, String departure,
			String arrival, String localDepartureTime, String localArrivalTime, double length) {
		super();
		this.name = name;
		this.airplaneType = airplaneType;
		this.daysOfTheWeek = daysOfTheWeek;
		this.departure = departure;
		this.arrival = arrival;
		this.localDepartureTime = localDepartureTime;
		this.localArrivalTime = localArrivalTime;
		this.length = length;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AirplaneType getAirplaneType() {
		return airplaneType;
	}

	public void setAirplaneType(AirplaneType airplaneType) {
		this.airplaneType = airplaneType;
	}

	public ArrayList<Integer> getDaysOfTheWeek() {
		return daysOfTheWeek;
	}

	public void setDaysOfTheWeek(ArrayList<Integer> daysOfTheWeek) {
		this.daysOfTheWeek = daysOfTheWeek;
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

	@Override
	public String toString() {
		return "ScheduledRoute [id=" + id + ", name=" + name + ", airplaneType=" + airplaneType + ", daysOfTheWeek="
				+ daysOfTheWeek + ", departure=" + departure + ", arrival=" + arrival + ", localDepartureTime="
				+ localDepartureTime + ", localArrivalTime=" + localArrivalTime + ", length=" + length + "]";
	}
	
	
}
