import java.io.*;
import java.util.ArrayList;

public class Table implements Serializable {
	
	//변수
	public String tableId; //테이블 아이디
	public int headCount;
	int index = 0;
	int pay;
	boolean available = true; // 사용 가능 상태 
	ArrayList<Order> orderList = new ArrayList<Order> ();
	
	Order order = new Order();
	Restaurant r = new Restaurant();
	
	//생성자
	public Table() {}; 	
	public Table (String tableId) {
		this.tableId = tableId;
	}
	public Table (String tableId, int headCount) {
		this.tableId = tableId;
		this.headCount = headCount;
	}
	
	//getter & setter
	
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId =  tableId;
	}
	
	public int getHeadCount() {
		return headCount;
	}
	public void setHeadCount(int headCount) {
		this.headCount = headCount;
	}
	
	// 테이블 이용 가능 여부 
	public boolean getAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getPay() {
		return pay;
	}
	public void setPay(int pay) {
		this.pay = pay;
	}
	
	//equals 함수 재정의 
	public boolean equals(Object o) {
		Table t = (Table) o;
		return (this.tableId.equals(t.tableId)); //같으면 true, 다르면 false
	}
    
	//기능  
	public String showOrder(int i) { //메뉴 아이디 배열 보여주는 함수 
		
		//return tableList.get(i).tableId + " " +  tableList.get(i).getHeadCount();
		return orderList.get(i).menuId + " " + orderList.get(i).orderCount + " " + orderList.get(i).getOrderPrice();
	}
	
	public int payTable() { //주문 결제, reset 하는 기능 
		
		pay = 0;
		int index = getIndex();
		int os = orderList.size();
		for (int i = index; i < os; i++) {
			
			int r = orderList.get(i).getOrderPrice(); //새로 추가한 것들만 계산할 수 있도록 뒤에서부터 새롭게 주문한 만큼만 금액 계산
			pay += r;
		}
		setIndex(os);
		setPay(pay);
		return pay;
	}
	
	public void resetTable() {
		orderList.clear();
	}
	
	public int searchOrder(Order o) { 
		
		int os = orderList.size();
		int index = getIndex();
		for (int i = index; i < os; i++) {
			if (orderList.get(i).equals(o)) {
				return i; //존재할 경우 인덱스 return 
			}
		} 
		return -1;//처음 시키는 메뉴인 경우 	
	}
	
	public void addOrder(Order o) {
		
		int j = searchOrder(o);
		
		if (j == -1) {//처음 주문하는 경우 
			orderList.add(o);
		} else {
			orderList.get(j).orderCount += o.getOrderCount(); //재주문일 경우 존재하는 배열에 횟수 추가해주기
			//count ++;
		}
		
	}
	
	//파일처리
	public void tableWrite(ObjectOutputStream o) throws IOException {
		
		o.writeObject(this);

	}
	
	public void ordersOutput(ObjectOutputStream out) throws IOException, FileNotFoundException {
		
		int os = orderList.size();
		out.writeInt(os);
		int index = getIndex();
		out.writeInt(index);
		for (int i = 0; i < os; i++) {
			orderList.get(i).orderWrite(out);
		}
		
	}
	
	public void ordersInput(ObjectInputStream in) throws IOException, ClassNotFoundException, FileNotFoundException {

		int os = in.readInt(); 
		index = in.readInt();
		for (int i = 0; i < os ; i++) {
			Order o =(Order) in.readObject();
			orderList.add(o);
			//orderList = (ArrayList<Order>) in.readObject();
		}					
	}
	
	
	//DataOutput & DataInput
	public void tableOutput(DataOutputStream td) throws IOException {
		
		td.writeUTF(tableId);

	}

	public void ordersOutput(DataOutputStream out) throws IOException, FileNotFoundException {
		
		out.writeInt(orderList.size());
		
		for (int i = 0; i < orderList.size(); i++) {
			orderList.get(i).orderOutput(out);
		}
	}
	
	public void ordersInput(DataInputStream in) throws IOException, FileNotFoundException {
		
		int ol = in.readInt();  
		
		for (int i = 0; i < ol ; i++) {
			String mid = in.readUTF();
			int c = in.readInt();
			int p = in.readInt();
			Order o = new Order(mid, c, p);
			orderList.add(o);
		}

	}	

}