package accountdeclare;

public class Seating {
	private String seatNo;
	private int passengerId;
	private int bookingId;
	private String flightNumber;
	private String seatClass;
	private String seatPreference;
	private String source;
	private String destination;
	private boolean meal;
	private int price;
	private boolean cancellationStatus;
	
	public String getSeatNo() 
	{
		return seatNo;
	}

	public int getPassengerId() {
		return passengerId;
	}
	public void setPassengerId(int passengerId) {
		this.passengerId = passengerId;
	}
	
	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public String getSeatPreference() {
		return seatPreference;
	}
	public void setSeatPreference(String seatPreference) {
		this.seatPreference = seatPreference;
	}
	public boolean isMeal() {
		return meal;
	}
	public void setMeal(boolean meal) {
		this.meal = meal;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	public boolean getCancellationStatus() {
		return cancellationStatus;
	}
	public void setCancellationStatus(boolean cancellationStatus) {
		this.cancellationStatus = cancellationStatus;
	}
	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	
	public String getSeatClass() {
		return seatClass;
	}

	public void setSeatClass(String seatClass) {
		this.seatClass = seatClass;
	}

	@Override
	public String toString() {
		return "Seating [seatNo=" + seatNo + ", passengerId=" + passengerId + ", bookingId=" + bookingId
				+ ", flightNumber=" + flightNumber + ", seatClass=" + seatClass + ", seatPreference=" + seatPreference
				+ ", source=" + source + ", destination=" + destination + ", meal=" + meal + ", price=" + price
				+ ", cancellationStatus=" + cancellationStatus + "]";
	}
	
	
	
	
	

	
	
	
	

	
}
