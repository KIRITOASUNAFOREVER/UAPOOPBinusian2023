import java.util.UUID;

public class Menu extends Shipping{
	private int price;

	public Menu(String productName, int quantity, UUID uuid, String address, String expedition, int status,
			int totalprice, int price) {
		super(productName, quantity, uuid, address, expedition, status, totalprice);
		this.price = price;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
