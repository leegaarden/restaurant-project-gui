import java.io.*;
import java.util.ArrayList;

public class Restaurant implements Serializable  {
	
	int total; 
	//클래스를 배열로 선언 
	ArrayList<Table> tableList = new ArrayList<Table> ();
	ArrayList<Menu> menuList = new ArrayList<Menu> ();
	int ts = tableList.size();
	
	//생성자
	public Restaurant() {};
	 
	//기능
	public String showTable(int i) { //테이블 아이디 배열 보여주는 함수
		
		return tableList.get(i).tableId + " " +  tableList.get(i).getHeadCount();
	} 
		
	public String showMenu(int i) { //메뉴 아이디 배열 보여주는 함수 
		
		return menuList.get(i).menuId + " " + menuList.get(i).name + " " + menuList.get(i).price;
	}
	
	public int searchTable(Table table)  { //테이블 검색하는 기능
		
		return tableList.indexOf(table);
	}
	
	public void addTable(Table table) throws Exception { //테이블 더하는 기능 
		
		int j = searchTable(table);
		
		if (j == -1) { //존재하지 않을 경우 
			tableList.add(table);
		} else {
			throw new Exception("중복 테이블 존재");
		}
		
	} 
	
	public void removeTable(Table table) throws Exception { //테이블 삭제하는 기능 
		
		int j = searchTable(table);
		
		if (j == -1) {
			throw new Exception("삭제할 테이블 없음");
		} else {
			tableList.remove(j);
		}
	}	
	
	public int searchMenu(Menu menu) { //메뉴 검색하는 기능

		return menuList.indexOf(menu);
	}
		
	public void addMenu(Menu menu) throws Exception{ //메뉴 추가하는 기능
		
		int j = searchMenu(menu);
		
		if (j == -1) { 
			menuList.add(menu);
		} else {
			throw new Exception("중복 메뉴 존재");
		}
		
	}	
	 
	public void removeMenu(Menu menu) throws Exception{ //메뉴 삭제하는 기능
		
		int j = searchMenu(menu);
		
		if (j == -1) {
			throw new Exception("삭제할 메뉴 없음");
		} else {
			menuList.remove(j);
		}
	}
	
	public int menuPrice(Menu menu)  { //가격 return 하는 기능 
		
		int price = 0;
		
		try {
			int j = searchMenu(menu);
			if (j == -1) {
				price = 0;
			} else {
				price = menuList.get(j).getPrice();
			}
		} catch (Exception e) {
			System.out.println("금액 찾을 수 없음");
		}
		return price;
	}
	
	
	public int payTotal() {
		
		int ts = tableList.size();
		for (int i = 0; i < ts; i++) {
			int s = tableList.get(i).orderList.size(); //total은 새롭게 구하기
			for(int j = 0; j < s; j++) {
				int t = tableList.get(i).orderList.get(j).getOrderPrice();
				total += t;
			}
		}
		return total;
	} 
	
	public int getTotal() {
	
		return total;
	} 
	
	
	public int searchHc(int hc, int s) throws IndexOutOfBoundsException {
		 
		if(hc > tableList.get(s).getHeadCount()) { //설정한 인원보다 많은 경우 
			return -1;
		} else {
			return hc;
		}
	}
	
	//파일처리
	public void tableOutput(ObjectOutputStream out) throws IOException, FileNotFoundException {
		
		int ts = tableList.size();
		out.writeInt(ts); //테이블 수 
		for (int i = 0; i < ts; i++) {
			tableList.get(i).tableWrite(out);
		}
		
	
	}
	
	public void tableInput(ObjectInputStream in) throws IOException, ClassNotFoundException, FileNotFoundException{
		
		ts = in.readInt(); //테이블 개수
		for (int i = 0; i < ts ; i++) {
			Table t = (Table) in.readObject();
			tableList.add(t);
		}
		
	}
	
	public void menuOutput(ObjectOutputStream out) throws IOException, FileNotFoundException {
		
		int ms = menuList.size();
		out.writeInt(ms); //메뉴 수 
		for (int i = 0; i < ms; i++) {
			menuList.get(i).menuWrite(out);
		}
		
	
	}
	
	public void menuInput(ObjectInputStream in) throws IOException, ClassNotFoundException, FileNotFoundException{
		
		int ms = in.readInt(); //메뉴 개수
		for (int i = 0; i < ms ; i++) {
			Menu m = (Menu) in.readObject();
			menuList.add(m);
		}
		
	}

	public void resOutput(ObjectOutputStream out) throws IOException, FileNotFoundException {
		
		int ts = tableList.size();
		out.writeInt(ts); //테이블 수 
		for (int i = 0; i < ts; i++) {
			tableList.get(i).tableWrite(out);
		}
		
		int ms = menuList.size();
		out.writeInt(ms); //메뉴 수 
		for (int i = 0; i < ms; i++) {
			menuList.get(i).menuWrite(out);
		}
	
	}
	
	public void resInput(ObjectInputStream in) throws IOException, ClassNotFoundException, FileNotFoundException{
		
		int ts = in.readInt(); //테이블 개수
		for (int i = 0; i < ts ; i++) {
			Table t = (Table) in.readObject();
			tableList.add(t);
		}
		
		int ms = in.readInt(); //메뉴 개수 		
		for (int i = 0; i < ms; i++) {
			Menu m = (Menu) in.readObject();
			menuList.add(m);
		}
		
	}
	
	public void  totalOutput(DataOutputStream out) throws FileNotFoundException, IOException {
		
		int t = payTotal();
		out.writeInt(t);
		
	}
	
	public void totalInput(DataInputStream in) throws FileNotFoundException, IOException {
		
		total = in.readInt(); //매출
	}
	
	//DataOutput & DataInput
	public void resDsataOutput(DataOutputStream out) throws IOException, FileNotFoundException {
		
		out.writeInt(tableList.size()); //테이블 수 
		
		for (int i = 0; i < tableList.size(); i++) {
			tableList.get(i).tableOutput(out);
		}
		
		out.writeInt(menuList.size()); //메뉴 수 
		
		for (int i = 0; i < menuList.size(); i++) {
			menuList.get(i).menuOutput(out);
		}
	}
	
	public void resDataInput(DataInputStream in) throws IOException, FileNotFoundException {
		
		int tl = in.readInt(); //테이블 개수
		
		for (int i = 0; i < tl ; i++) {
			String tid = in.readUTF();
			Table t = new Table(tid);
			tableList.add(t);
		}
		
		int ml = in.readInt(); //메뉴 개수 		
		
		for (int i = 0; i < ml; i++) {
			String mid = in.readUTF();
			String mn = in.readUTF();
			int p = in.readInt();
			Menu m = new Menu(mid, mn, p);
			menuList.add(m);
		}

	}
		
}
