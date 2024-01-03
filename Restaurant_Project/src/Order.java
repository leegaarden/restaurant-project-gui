import java.io.*;

public class Order extends Menu implements Serializable {

	public int orderCount; //주문수량
	int orderPrice;

	//생성자
	public Order() {}
	public Order (String menuId) { //메뉴 검색하기 용 생성자
		this.menuId = menuId;
	}
	public Order(String menuId, int orderCount, int price) {
		this.menuId = menuId;
		this.orderCount = orderCount;
		this.price = price;
	}

	//getter & setter
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	public int getOrderCount() {
		return orderCount;
	}	
	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}
	public int getOrderPrice() {
		return this.price * orderCount;
	} 
	
	//equals 함수 재정의 
	public boolean equals(Order o) {
    	if (this.menuId.equals(o.getMenuId())) {
    		return true;
    	} else {
    		return false;
    	}
    }
   
    public void orderWrite(ObjectOutputStream od) throws IOException {
		
		od.writeObject(this);
		
	}
    public void orderOutput(DataOutputStream od) throws IOException {
		
		od.writeUTF(menuId);
		od.writeInt(orderCount);
		od.writeInt(price);
		
		
	}
}
