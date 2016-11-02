package webrequests;

public class UpdateAirplaneRequest {
	
	String id;
	
	String registration;
	
	int seatCapacity;

	public UpdateAirplaneRequest() {
		super();
	}

	public UpdateAirplaneRequest(String id, String registration, int seatCapacity) {
		super();
		this.id = id;
		this.registration = registration;
		this.seatCapacity = seatCapacity;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the registration
	 */
	public String getRegistration() {
		return registration;
	}

	/**
	 * @param registration the registration to set
	 */
	public void setRegistration(String registration) {
		this.registration = registration;
	}

	/**
	 * @return the seatCapacity
	 */
	public int getSeatCapacity() {
		return seatCapacity;
	}

	/**
	 * @param seatCapacity the seatCapacity to set
	 */
	public void setSeatCapacity(int seatCapacity) {
		this.seatCapacity = seatCapacity;
	}
	
}
