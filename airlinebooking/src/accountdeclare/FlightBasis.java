package accountdeclare;

public class FlightBasis {
	private int businessPrice;
	private int economyPrice;
	private boolean businessInit;
	private boolean economyInit;
	
	public int getBusinessPrice() {
		return businessPrice;
	}
	public void setBusinessPrice(int businessPrice) {
		this.businessPrice = businessPrice;
	}
	public int getEconomyPrice() {
		return economyPrice;
	}
	public void setEconomyPrice(int economyPrice) {
		this.economyPrice = economyPrice;
	}
	
	public boolean isBusinessInit() {
		return businessInit;
	}
	public void setBusinessInit(boolean businessInit) {
		this.businessInit = businessInit;
	}
	public boolean isEconomyInit() {
		return economyInit;
	}
	public void setEconomyInit(boolean economyInit) {
		this.economyInit = economyInit;
	}
	@Override
	public String toString() {
		return "FlightBasis [businessPrice=" + businessPrice + ", economyPrice=" + economyPrice + ", businessInit="
				+ businessInit + ", economyInit=" + economyInit + "]";
	}

	
	
	
}
