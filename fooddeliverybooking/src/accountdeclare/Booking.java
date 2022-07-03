package accountdeclare;

public class Booking {
	private int customerId;
	private Points restaurant;
	private Points destinationPoint;
	private String time;
	private int bookingId;
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Points getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Points restaurant) {
		this.restaurant = restaurant;
	}
	public Points getDestinationPoint() {
		return destinationPoint;
	}
	public void setDestinationPoint(Points destinationPoint) {
		this.destinationPoint = destinationPoint;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	@Override
	public String toString() {
		return "Booking [customerId=" + customerId + ", restaurant=" + restaurant + ", destinationPoint="
				+ destinationPoint + ", time=" + time + ", bookingId=" + bookingId + "]";
	}
	
	
}
