import java.io.*;
import java.util.*;

public class GUI {

	public static void main (String[] args) {
		
		Restaurant r = new Restaurant();
		Table t = new Table();
		
		try {	
			
			FileInputStream tfIn = null;
			ObjectInputStream toIn = null;
			
			tfIn = new FileInputStream("table.dat");
			toIn = new ObjectInputStream(tfIn);
			
			FileInputStream mfIn = null;
			ObjectInputStream moIn = null;
			
			mfIn = new FileInputStream("menu.dat");
			moIn = new ObjectInputStream(mfIn);
			
			r.tableInput(toIn);
			r.menuInput(moIn);

			FileInputStream ofIn = null;
			ObjectInputStream ooIn = null;
			
			ofIn = new FileInputStream("order.dat");
			ooIn = new ObjectInputStream(ofIn);

			t.ordersInput(ooIn);
			
		} catch (ClassNotFoundException e) {
			//안내창 띄우기
			AlertDialog ad = new AlertDialog(2);
			ManagementMode mm = new ManagementMode();
			mm.setVisible(true);	
		} catch (FileNotFoundException e) {
			//안내창 띄우기
			AlertDialog ad = new AlertDialog(2);
			ManagementMode mm = new ManagementMode();
			mm.setVisible(true);	
		} catch (IOException e) {
			//안내창 띄우기
			AlertDialog ad = new AlertDialog(3);
		} 
		
		
		
	}
}
