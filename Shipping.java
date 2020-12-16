import java.util.UUID;

public class Shipping {
	private String productName;
	private int Quantity;
	private UUID uuid;
	private String Address;
	private String Expedition;
	private int status;
	private int totalPrice;
	
	public Shipping(String productName, int quantity, UUID uuid, String address, String expedition, int status,int totalprice) {
		super();
		this.productName = productName;
		Quantity = quantity;
		this.uuid = uuid;
		Address = address;
		Expedition = expedition;
		this.status = status;
		totalPrice = totalprice;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getExpedition() {
		return Expedition;
	}
	public void setExpedition(String expedition) {
		Expedition = expedition;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int gettotalPrice() {
		return totalPrice;
	}
	public void settotalPrice(int totalprice) {
		totalPrice = totalprice;
	}
}
