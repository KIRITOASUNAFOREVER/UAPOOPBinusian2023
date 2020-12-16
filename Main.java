import java.util.Scanner;
import java.util.Vector;
import java.util.UUID;
import java.util.Random;

public class Main {
	
	Scanner scan = new Scanner(System.in);
	UUID uuid = UUID.randomUUID();
	Vector<Shipping> ShippingList = new Vector<>();
	Vector<Menu> MenuList = new Vector<>();
	Random rand = new Random();
	int indeks  = 0;
	
	
	public void addShippingData(UUID shipping){
		int index;
		int jumlahBeli;
		int jumlahBarang = MenuList.size();
		String Address;
		String pilihan;
		String Expedition;
		int totalPrice = 0;
		FinalHarga viCepat = new FinalHarga();
		int hargaViCepat = viCepat.getHarga(); 
		int peluangGagal = 0;
		int hargaVeDex;
		
		if(jumlahBarang != 0){
			for(int i=0;i<MenuList.size();i++){
				if(MenuList.get(i).getStatus()==1){
					System.out.println("No      : "+(i+1));
					System.out.println("Name    : "+MenuList.get(i).getProductName());
					System.out.println("Price   : "+"Rp. "+MenuList.get(i).getPrice()+".0");
					System.out.println("Stocks  : "+MenuList.get(i).getQuantity());
					System.out.println();
				}
			}
			System.out.println("Pick product to buy [1.."+MenuList.size()+"]: ");
			do{
				index = scan.nextInt();scan.nextLine();
			}while(index < 1 || index > 9);
			
			do{
				System.out.println("Input quantity [1.."+MenuList.get(index-1).getQuantity()+"]: ");
				jumlahBeli = scan.nextInt();scan.nextLine();
				if(jumlahBeli > MenuList.get(index-1).getQuantity()){
					System.out.println("Insufficient product stock, Maximum purchase of this product is only "+MenuList.get(index-1).getQuantity()+".");
				}
			}while(jumlahBeli < 1 || jumlahBeli > MenuList.get(index-1).getQuantity());
			
			MenuList.get(index-1).setQuantity(MenuList.get(index-1).getQuantity()-jumlahBeli);
			totalPrice = jumlahBeli * MenuList.get(index-1).getPrice();
			
			ShippingList.add(new Shipping(MenuList.get(index-1).getProductName(), jumlahBeli, shipping,"Jl. Kucing", "Unknown", 2,totalPrice));
			
			
			if(MenuList.get(index-1).getQuantity() == 0){
				MenuList.get(index-1).setStatus(0);
				MenuList.remove(index-1);
			}
			do{
				System.out.println("Would you like to add more product into your cart? [Y/N]: ");
				pilihan = scan.nextLine();
			}while(!pilihan.equals("Y") && !pilihan.equals("N"));
			
			if(pilihan.equals("Y")){
				addShippingData(shipping);
			}else if(pilihan.equals("N")){
				indeks++;
				
				do{
					System.out.println("Input shipping address [must begin with 'Jl. ' (case-sensitive)]: ");
					Address = scan.nextLine();
				}while(!Address.equals("Jl. ") && Address.length() < 10);
				for(int j=0;j<ShippingList.size();j++){
					if(ShippingList.get(j).getUuid()==shipping){
						ShippingList.get(j).setAddress(Address);
					}
				}
				String temp1 = "VeDex";
				String temp2 = "ViCepat";
				do{
					System.out.println("Input Shipping Service [VeDex | ViCepat (case-insensitive)]: ");
					Expedition = scan.nextLine();
				}while(!Expedition.equalsIgnoreCase(temp1) && !Expedition.equalsIgnoreCase(temp2));
				
				if(Expedition.equalsIgnoreCase(temp1)){
					for(int j=0;j<ShippingList.size();j++){
						if(ShippingList.get(j).getUuid()==shipping){
							ShippingList.get(j).setExpedition("VeDex");
						}
					}
					if(totalPrice < 150000)
					{
						hargaVeDex = 10000;
						System.out.println();
						System.out.println("Product's Prices       : "+"Rp. "+totalPrice +".0");
						System.out.println("Shipping Fee           : "+"Rp. "+hargaVeDex +".0");
						System.out.println("Grand Total            : "+"Rp. "+(totalPrice+hargaVeDex)+".0");
						for(int j=0;j<ShippingList.size();j++){
							if(ShippingList.get(j).getUuid()==shipping){
								ShippingList.get(j).settotalPrice(totalPrice + hargaVeDex);
							}
						}
					}
					else{
						hargaVeDex = 0;
						System.out.println();
						System.out.println("Product's Prices       : "+"Rp. "+totalPrice +".0");
						System.out.println("Shipping Fee           : "+"Rp. "+hargaVeDex +".0");
						System.out.println("Grand Total            : "+"Rp. "+(totalPrice+hargaVeDex)+".0");
						for(int j=0;j<ShippingList.size();j++){
							if(ShippingList.get(j).getUuid()==shipping){
								ShippingList.get(j).settotalPrice(totalPrice + hargaVeDex);
							}
						}
					}
					
					peluangGagal = rand.nextInt(100)+1;
					Thread veDexThread = new Thread(new ShippingTime(2));
					veDexThread.run();
					
					if(peluangGagal <=100){
						for(int j=0;j<ShippingList.size();j++){
							if(ShippingList.get(j).getUuid()==shipping){
								ShippingList.get(j).setStatus(0);
							}
						}
					}
					else{
						for(int j=0;j<ShippingList.size();j++){
							if(ShippingList.get(j).getUuid()==shipping){
								ShippingList.get(j).setStatus(1);
							}
						}
					}
				}else if(Expedition.equalsIgnoreCase(temp2)){
					for(int j=0;j<ShippingList.size();j++){
						if(ShippingList.get(j).getUuid()==shipping){
							ShippingList.get(j).setExpedition("ViCepat");
						}
					}
					System.out.println();
					System.out.println("Product's Prices       : "+"Rp. "+totalPrice +".0");
					System.out.println("Shipping Fee           : "+"Rp. "+hargaViCepat +".0");
					System.out.println("Grand Total            : "+"Rp. "+(totalPrice+hargaViCepat)+".0");
					for(int j=0;j<ShippingList.size();j++){
						if(ShippingList.get(j).getUuid()==shipping){
							ShippingList.get(j).settotalPrice(totalPrice + hargaViCepat);
						}
					}
					
					Thread viCepatThread = new Thread(new ShippingTime(1));
					viCepatThread.run();
					
					for(int j=0;j<ShippingList.size();j++){
						if(ShippingList.get(j).getUuid()==shipping){
							ShippingList.get(j).setStatus(1);
						}
					}
				}
				System.out.println("Press enter to continue..");
				scan.nextLine();
			}
			
			}else{
			System.out.println("Sorry, all of our products are currently out of stock!");
			System.out.println("Press enter to continue..");
			scan.nextLine();
			}
		}
	
	public void viewCart(){
		UUID penanda = null;
		String temp1 = "VeDex";
		int jumlah = ShippingList.size();
		final int count = 1;
		if(jumlah==0){
			System.out.println("No purchase history");
			System.out.println();
		}
		else{
			
			if(count==1){
				penanda = ShippingList.get(0).getUuid();
				for(int j=0;j<ShippingList.size();j++){
					if(ShippingList.get(j).getExpedition().equalsIgnoreCase(temp1)){
						if(ShippingList.get(j).getStatus() == 2){
							System.out.println("VeDex");
							System.out.println("--------");
							System.out.println("Shipping ID     : "+penanda);
							System.out.println("Shipping Status : "+"On Going");
							System.out.println();
						}
						else if(ShippingList.get(j).getStatus()==0){
							System.out.println("VeDex");
							System.out.println("--------");
							System.out.println("Shipping ID     : "+penanda);
							System.out.println("Shipping Status : "+"Failed");
							System.out.println("Sorry for the inconvenience, your packet can't be traced.");
							System.out.println("Insurance fee will be sent directly to the according address.");
							System.out.println();
						}
					}else{
						if(ShippingList.get(j).getStatus() == 2){
							System.out.println("ViCepat");
							System.out.println("--------");
							System.out.println("Shipping ID     : "+penanda);
							System.out.println("Shipping Status : "+"On Going");
							System.out.println();System.out.println();
						}
						else if(ShippingList.get(j).getStatus()==0){
							System.out.println("ViCepat");
							System.out.println("--------");
							System.out.println("Shipping ID     : "+penanda);
							System.out.println("Shipping Status : "+"Failed");
							System.out.println("Sorry for the inconvenience, your packet can't be traced.");
							System.out.println("Insurance fee will be sent directly to the according address.");
							System.out.println();System.out.println();
						}
					}
					break;
				}
			}
				for(int k=1;k<ShippingList.size();k++){
					if(ShippingList.get(k).getUuid() == penanda){
						continue;
					}
					else{
						if(ShippingList.get(k).getExpedition().equalsIgnoreCase(temp1)){
							if(ShippingList.get(k).getStatus() == 2){
								System.out.println("VeDex");
								System.out.println("--------");
								System.out.println("Shipping ID     : "+ShippingList.get(k).getUuid());
								System.out.println("Shipping Status : "+"On Going");
								System.out.println();System.out.println();
							}
							else if(ShippingList.get(k).getStatus()==0){
								System.out.println("VeDex");
								System.out.println("--------");
								System.out.println("Shipping ID     : "+ShippingList.get(k).getUuid());
								System.out.println("Shipping Status : "+"Failed");
								System.out.println("Sorry for the inconvenience, your packet can't be traced.");
								System.out.println("Insurance fee will be sent directly to the according address.");
								System.out.println();System.out.println();
							}
					}
				}
		}
				}
	}
	
	public void viewFinishedOrder(){
		UUID temp = null;
		if(ShippingList.size()==0){
			System.out.println("No purchase history");
			System.out.println();
		}else{
			System.out.println("Finished Orders");
			System.out.println("===============");
			System.out.println();
			
			for(int m=0 ; m<ShippingList.size();m++){
				temp = ShippingList.get(m).getUuid();
				if(ShippingList.get(m).getStatus()==1){
					System.out.println("Shipping ID    : "+temp);
					System.out.println("Address        : "+ShippingList.get(m).getAddress());
					System.out.println("Total Price    : "+"Rp. "+ShippingList.get(m).gettotalPrice()+".0");
					System.out.println("Item(s) :");
					System.out.println("- "+ShippingList.get(m).getQuantity()+"pcs "+ShippingList.get(m).getProductName());
					for(int n=1 ; n < ShippingList.size(); n++){
						if(ShippingList.get(n).getUuid() == temp){
							System.out.println("- "+ShippingList.get(n).getQuantity()+"pcs "+ShippingList.get(n).getProductName());
						}
					}
				}
				break;
			}
			System.out.println();System.out.println();
			for(int p = 1 ; p < ShippingList.size(); p++){
				if(ShippingList.get(p).getUuid() == temp){
					continue;
				}
				else{
					if(ShippingList.get(p).getStatus()==1){
						temp = ShippingList.get(p).getUuid();
						p = p;
						System.out.println("Shipping ID    : "+temp);
						System.out.println("Address        : "+ShippingList.get(p).getAddress());
						System.out.println("Total Price    : "+"Rp. "+ShippingList.get(p).gettotalPrice()+".0");
						System.out.println("Item(s) :");
						System.out.println("- "+ShippingList.get(p).getQuantity()+"pcs "+ShippingList.get(p).getProductName());
						for(int v=(p+1) ; v < ShippingList.size(); v++){
							if(ShippingList.get(v).getUuid() == temp){
								System.out.println("- "+ShippingList.get(v).getQuantity()+"pcs "+ShippingList.get(v).getProductName());
							}
						}
					}
				}
				System.out.println();System.out.println();
			}
		}
		System.out.println("Press enter to continue..");
		scan.nextLine();
}
	
	public void mainMenu() {
		int choose;
		String name;
		
		System.out.println("Welcome to Vi Shop! ^_^");
		System.out.println();System.out.println();
		System.out.println("To create an order, please input your name.");
		do {
			System.out.print("Name [Min. 3 characters | Must contain two words]: ");
			name = scan.nextLine();
			if(name.length() < 3 || !name.contains(" ")){
				System.out.println("Name must be two words!");
			}
		}while(name.length() < 3 || !name.contains(" "));
		
		MenuList.add(new Menu("The Aubree Niacinamide Serum", 20 + rand.nextInt(21), uuid, "Jl. Kenari", "Unknown", 1,0, 99000));
		MenuList.add(new Menu("Tiff Body Cacao Coffee Scrub", 20 + rand.nextInt(21), uuid, "Jl. Gajah", "Unknown", 1, 0,150000));
		MenuList.add(new Menu("Kleveru Glass Skin Serum", 20 + rand.nextInt(21), uuid, "Jl. Kelinci", "Unknown", 1, 0,143000));
		MenuList.add(new Menu("Sensatia Botanicals Unscented Facial-C Serum", 20 + rand.nextInt(21), uuid, "Jl. Tikus", "Unknown", 1, 0,180000));
		MenuList.add(new Menu("Mineral Botanicals Vanila Lip Scrub", 20 + rand.nextInt(21), uuid, "Jl. Jerapah", "Unknown", 1, 0,55000));
		MenuList.add(new Menu("Think Hale Let's Mask", 20 + rand.nextInt(21), uuid, "Jl. Macan", "Unknown", 1, 0,129000));
		MenuList.add(new Menu("Faith In Face Cica Jelly Mask", 20 + rand.nextInt(21), uuid, "Jl. Singa", "Unknown", 1, 0,29000));
		MenuList.add(new Menu("Lacoco Swallow Facial Foam", 20 + rand.nextInt(21), uuid, "Jl. Monyet", "Unknown", 1, 0,165000));
		MenuList.add(new Menu("Everwhite Brightening Essence Serum", 20 + rand.nextInt(21), uuid, "Jl. Simpanse", "Unknown", 1, 0,125000));
		
		do {
			viewCart();
			System.out.println("Hello, "+name);
			System.out.println("1. Shop at Vi");
			System.out.println("2. Refresh My Shipping Status");
			System.out.println("3. View Purchase History");	
			System.out.println("4. Exit");
			System.out.print(">> ");
			choose = scan.nextInt(); scan.nextLine();
			switch(choose) {
			case 1:
				UUID uuidShipping = UUID.randomUUID();
				addShippingData(uuidShipping);
				break;
			case 2:
				
				break;
			case 3:
				viewFinishedOrder();
				break;
			}
		}while(choose!=4 || choose < 1 || choose > 4);
	}
	
	public Main() {
		mainMenu();
	}

	public static void main(String[] args) {
		new Main();
	}
}