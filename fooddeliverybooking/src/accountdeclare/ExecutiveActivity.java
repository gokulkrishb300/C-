package accountdeclare;

public class ExecutiveActivity {
	private int trip;
	private String exeNo;
	private Points restaurant;
	private Points destinationPoint;
	private byte orders;
	private String pickupTime;
	private String deliveryTime;
	private byte deliveryCharge;
	
	public int getTrip() {
		return trip;
	}
	public void setTrip(int trip) {
		this.trip = trip;
	}
	public String getExeNo() {
		return exeNo;
	}
	public void setExeNo(String exeNo) {
		this.exeNo = exeNo;
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
	public byte getOrders() {
		return orders;
	}
	public void setOrders(byte orders) {
		this.orders = orders;
	}
	public String getPickupTime() {
		return pickupTime;
	}
	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public byte getDeliveryCharge() {
		return deliveryCharge;
	}
	public void setDeliveryCharge(byte deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}
	@Override
	public String toString() {
		return "Booking [trip=" + trip + ", exeNo=" + exeNo + ", restaurant=" + restaurant + ", destinationPoint="
				+ destinationPoint + ", orders=" + orders + ", pickupTime=" + pickupTime + ", deliveryTime="
				+ deliveryTime + ", deliveryCharge=" + deliveryCharge + "]";
	}
	
		
}
