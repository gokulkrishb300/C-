package accountdeclare;

public class Seat {
	private String seatNo;
	private String seatClass;
	private String seatPosition;
	private int price;
	private boolean meal;
	private boolean seatStatus;
	
	public String getSeatNo() {
		return seatNo;
	}
	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}
	public String getSeatPosition() {
		return seatPosition;
	}
	public void setSeatPosition(String seatPosition) {
		this.seatPosition = seatPosition;
	}
	public boolean getSeatStatus() {
		return seatStatus;
	}
	public void setSeatStatus(boolean seatStatus) {
		this.seatStatus = seatStatus;
	}
	public String getSeatClass() {
		return seatClass;
	}
	public void setSeatClass(String seatClass) {
		this.seatClass = seatClass;
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
	@Override
	public String toString() {
		return "Seat [seatNo=" + seatNo + ", seatClass=" + seatClass + ", seatPosition=" + seatPosition + ", price="
				+ price + ", meal=" + meal + ", seatStatus=" + seatStatus + "]";
	}
	
	
	
}
