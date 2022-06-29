package accountdeclare;

public class TravelHistory {
	private int bookingId;
	private int customerId;
	private Points startingPoint;
	private Points endPoint;
	private long startTime;
	private long endTime;
	private boolean bookingType;
	private short charges;
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
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
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
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
				+ ", BookingType=" + bookingType + ", Charges=" + charges;
	}
	
	
	
	
	
}
