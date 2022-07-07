package accountdeclare;

public class Inventory {
	private Category category;
	private String brand;
	private String model;
	private int price;
	private short stock;
	private int discount;
	
	public String getCategory() {
		return category+"";
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public short getStock() {
		return stock;
	}
	public void setStock(short stock) {
		this.stock = stock;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	@Override
	public String toString() {
		return "Inventory [category=" + category + ", brand=" + brand + ", model=" + model + ", price=" + price
				+ ", stock=" + stock + ", discount=" + discount + "]";
	}
	
	
}
