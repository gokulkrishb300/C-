package accountdeclare;

public class TravelHistory {
	private String bookingId;
	private int customerId;
	private Points startingPoint;
	private Points endPoint;
	private String startTime;
	private String endTime;
	private boolean bookingType;
	private short charges;
	public String getBookingId() {
		return bookingId;
	}
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Points getStartingPoint() {
		return startingPoint;
	}
	public void setStartingPoint(Points startingPoint) {
		this.startingPoint = startingPoint;
	}
	public Points getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(Points endPoint) {
		this.endPoint = endPoint;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public byte getBookingType() {
		if(bookingType == true)
		{
			return 1;
		}
		return 2;
	}
	public void setBookingType(boolean bookingType) {
		this.bookingType = bookingType;
	}
	public short getCharges() {
		return charges;
	}
	public void setCharges(short charges) {
		this.charges = charges;
	}
	@Override
	public String toString() {
		return "BookingId=" + bookingId + ", CustomerId=" + customerId + ", StartingPoint="
				+ startingPoint + ", EndPoint=" + endPoint + ", StartTime=" + startTime + ", EndTime=" + endTime
				+ ", BookingType=" + getBookingType() + ", Charges=" + charges;
	}
	
	
	
	
	
}
