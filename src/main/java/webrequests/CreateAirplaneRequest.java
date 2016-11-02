package webrequests;

public class CreateAirplaneRequest {
	
	private String manufacturer;
	
	private String type;
	
	private String registration;
	
	private int seatCapacity;
	
	private String deliveryDate;
	
	private String msnNumber;
	
	public CreateAirplaneRequest() {
		super();
	}

	public CreateAirplaneRequest(String manufacturer, String type, String registration, int seatCapacity,
			String deliveryDate, String msnNumber) {
		super();
		this.manufacturer = manufacturer;
		this.type = type;
		this.registration = registration;
		this.seatCapacity = seatCapacity;
		this.deliveryDate = deliveryDate;
		this.msnNumber = msnNumber;
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

	
}
