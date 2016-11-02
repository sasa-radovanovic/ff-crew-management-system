package webrequests;

import java.io.Serializable;
import java.util.ArrayList;

public class CreateScheduledRouteRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1480036850115406996L;
	
	private String name;
	
	private String departure;
	
	private String arrival;
	
	private ArrayList<Integer> daysOfTheWeek;
	
	private String localDepartureTime;
	
	private String localArrivalTime;
	
	private double length;
	
	private String airplaneTypeId;
	
	private String airplaneTypeManufacturer;

	public CreateScheduledRouteRequest() {
		super();
	}

	public CreateScheduledRouteRequest(String name, String departure, String arrival, ArrayList<Integer> daysOfTheWeek,
			String localDepartureTime, String localArrivalTime, double length, String airplaneTypeId, String airplaneTypeManufacturer) {
		super();
		this.name = name;
		this.departure = departure;
		this.arrival = arrival;
		this.daysOfTheWeek = daysOfTheWeek;
		this.localDepartureTime = localDepartureTime;
		this.localArrivalTime = localArrivalTime;
		this.length = length;
		this.airplaneTypeId = airplaneTypeId;
		this.airplaneTypeManufacturer = airplaneTypeManufacturer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getAirplaneTypeId() {
		return airplaneTypeId;
	}

	public void setAirplaneTypeId(String airplaneTypeId) {
		this.airplaneTypeId = airplaneTypeId;
	}

	public String getAirplaneTypeManufacturer() {
		return airplaneTypeManufacturer;
	}

	public void setAirplaneTypeManufacturer(String airplaneTypeManufacturer) {
		this.airplaneTypeManufacturer = airplaneTypeManufacturer;
	}

	@Override
	public String toString() {
		return "CreateScheduledRouteRequest [name=" + name + ", departure=" + departure + ", arrival=" + arrival
				+ ", daysOfTheWeek=" + daysOfTheWeek + ", localDepartureTime=" + localDepartureTime
				+ ", localArrivalTime=" + localArrivalTime + ", length=" + length + ", airplaneTypeId=" + airplaneTypeId
				+ ", airplaneTypeManufacturer=" + airplaneTypeManufacturer + "]";
	}
	
	
}
