import java.util.Vector;

public class ShippingTime implements Runnable{
	Thread t;
	int shipTime;
	Vector<Shipping>Ship  = new Vector<>();

	public ShippingTime(int x) {
		t = new Thread(this);
		t.run();
		this.shipTime = x;
	}
	
	
	public void run() {
		while(shipTime!=0) {
			shipTime--;
			
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
	}
}