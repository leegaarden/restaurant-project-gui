import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Pay extends JFrame {
	Restaurant r = new Restaurant();
	Table t = new Table();
	Store s = new Store();
	int i = s.getIndex(); // tableList의 인덱스 
	TableOrder to = new TableOrder(i);
	
    private JPanel centerPanel;
    private JTable paymentTable;
    private DefaultTableModel paymentTableModel;
    private JScrollPane scrollPane;
    
    private JPanel bottomPanel;
    private JLabel payLabel;
    private JButton payButton;
    private JButton backButton;

    public Pay() {
    	try {
    		//메뉴 파일처리
    		FileInputStream mfIn = null;
			ObjectInputStream moIn = null;
			
			mfIn = new FileInputStream("menu.dat");
			moIn = new ObjectInputStream(mfIn);
			r.menuInput(moIn);
			
			//테이블 파일처리
			FileInputStream fin = null;
    		ObjectInputStream oin = null;
    		
    		fin = new FileInputStream("table.dat");
    		oin = new ObjectInputStream(fin);
    		
    		r.tableInput(oin);
    		
    	} catch (ClassNotFoundException e) {
    		AlertDialog ad = new AlertDialog(2);
		} catch (FileNotFoundException e) {
			AlertDialog ad = new AlertDialog(2);
		} catch (IOException e) {
			AlertDialog ad = new AlertDialog(3);
		} 
    	
    	// 결제할 금액
    	int pay = 0;
        // 프레임 설정
        setTitle("결제");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 가운데 패널
        centerPanel = new JPanel(new BorderLayout());

        // 주문 내역 테이블 생성 
        paymentTableModel = new DefaultTableModel(new String[]{"메뉴 아이디", "수량", "가격"}, 0);
        paymentTable = new JTable(paymentTableModel);
        scrollPane = new JScrollPane(paymentTable);
        
        // 주문 내역 추가 
        int tableIndex = s.getIndex(); // 테이블 인덱스 
        int os = r.tableList.get(tableIndex).orderList.size();
        int payIndex = r.tableList.get(tableIndex).getIndex();
  
        for (int j = payIndex; j < os; j++) {
        	paymentTableModel.addRow(new Object[] {r.tableList.get(tableIndex).orderList.get(j).getMenuId(), r.tableList.get(tableIndex).orderList.get(j).getOrderCount(), r.tableList.get(tableIndex).orderList.get(j).getOrderPrice()});
        	pay += r.tableList.get(tableIndex).orderList.get(j).getOrderPrice();
    	}
      
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        // 금액 라벨
        
        payLabel = new JLabel("금액: " + pay + "원");
        
        // 결제하기 버튼
        payButton = new JButton("결제하기");
        payButton.addActionListener(new Listener());
        
        // 돌아가기 버튼 
        backButton = new JButton("돌아가기");
        backButton.addActionListener(new Listener());

        // 하단 패널
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(payLabel);
        bottomPanel.add(payButton);
        bottomPanel.add(backButton);

        // 가운데 패널과 하단 패널을 프레임에 추가
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int tableIndex = s.getIndex(); //테이블 인덱스 
			// 결제하기 버튼 눌렀을 때 
			if (e.getSource() == payButton) {
				
				r.tableList.get(tableIndex).payTable(); // 결제하기 
				r.tableList.get(tableIndex).setAvailable(true); //결제 후 이용 가능한 상태로 바꾸기 
				
				Store s = new Store();
				s.setVisible(true);
				to.setVisible(false);
				//setVisible(false);
				dispose(); // 현재 프레임을 닫음
				
				try {
					//매출 파일처리 
					FileOutputStream ttfOut = null;
					DataOutputStream ttdOut = null;
						
					ttfOut = new FileOutputStream("total.dat");
					ttdOut = new DataOutputStream(ttfOut); 
						
					r.totalOutput(ttdOut);
					ttfOut.close(); ttdOut.close();
					
					//테이블 파일 처리
					FileOutputStream tfOut = null;
					ObjectOutputStream toOut = null;
					   
					tfOut = new FileOutputStream("table.dat");
					toOut = new ObjectOutputStream(tfOut);
						
					r.tableOutput(toOut);
					   
		    	} catch (IOException e1) {
		    		AlertDialog ad = new AlertDialog(3);
	            } catch (Exception fne) {
	            	AlertDialog ad = new AlertDialog(2);
	            } 
				
			}
			
			// 돌아가기 버튼 눌렀을 때
			if (e.getSource() == backButton) {
				dispose(); // 현재 프레임을 닫음
			}
			
		}
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Pay p = new Pay();
            p.setVisible(true);
        });
    }
}
