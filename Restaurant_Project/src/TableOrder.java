import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TableOrder extends JFrame {
	
	Restaurant r = new Restaurant();
	Table t = new Table();
	Store s = new Store();
	
	private JPanel topPanel;
    private JLabel tableLabel;
    private JLabel payLabel;
    private JButton storeButton;
    
    private JPanel centerPanel;
    private DefaultTableModel orderTableModel;
    private JTable orderTable;
    private JScrollPane orderScrollPane;
    
    private JPanel menuPanel;
    private JButton[] mBtn = new JButton[100];
    private JScrollPane menuScrollPane;
    
    private JPanel bottomPanel;
    private JButton addButton;
    private JLabel countLabel;
    private JButton removeButton;
    //private JButton confirmButton;
    private JLabel totalLabel;
    private JButton payButton;
    
    // 주문된 메뉴의 합계(결제금액)
	int tableOrderPay = 0; 
	
	public int getTableOrderPay() {
		return tableOrderPay;
	}
	public void setTableOrderPay(int tableOrderPay) {
		this.tableOrderPay = tableOrderPay;
	}
    
    public TableOrder(int index) {
    	
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
    	
    	
    	// 메뉴 개수
    	int ms = r.menuList.size();
    	
    	
        // 프레임 설정
    	int x = 1000; // 가로 길이
    	int y = 600; // 세로 길이 
        setTitle("테이블 별 주문");
        setSize(x, y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 상단 패널
        topPanel = new JPanel(new BorderLayout());
        tableLabel = new JLabel(r.tableList.get(index).getTableId());
        topPanel.add(tableLabel, BorderLayout.WEST);

        storeButton = new JButton("매장으로");
        storeButton.addActionListener(new Listener());
        topPanel.add(storeButton, BorderLayout.EAST);

        // 가운데 패널
        centerPanel = new JPanel(new BorderLayout());

        // 주문 목록 JTable
        int mx = 600; // 메뉴 패널의 가로 길이
        int ox = x - mx - 10; // 주문 목록 패널의 가로 길이: 전체 패널의 가로 길이에서 메뉴 패널의 가로 길이 뺀 것 
        orderTableModel = new DefaultTableModel();
        orderTableModel.addColumn("메뉴 아이디");
        orderTableModel.addColumn("수량");
        orderTableModel.addColumn("가격");
        orderTable = new JTable(orderTableModel);
        orderScrollPane = new JScrollPane(orderTable);
        orderScrollPane.setPreferredSize(new Dimension(ox, getHeight())); // 너비 600 지정
        centerPanel.add(orderScrollPane, BorderLayout.WEST);

        // 메뉴 선택 패널
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0, 3, 20, 20)); // 가로로 세 개씩 배치하고 간격 추가
        menuScrollPane = new JScrollPane(menuPanel);
        menuScrollPane.setPreferredSize(new Dimension(mx, getHeight())); // 너비 600 지정
        centerPanel.add(menuScrollPane, BorderLayout.EAST);

        // 메뉴 항목 버튼 추가
        for (int m = 0; m < ms; m++) {
        	//mBtn[m] = new JButton ("<HTML><body style = 'text-align:center;'> r.menuList.get(m).getMenuId() <br> r.menuList.get(m).getPrice() + 원 <body></HTML> ");
        	mBtn[m] = new JButton(r.menuList.get(m).getMenuId() + "(" + r.menuList.get(m).getPrice() + "원)");
        	mBtn[m].setPreferredSize(new Dimension(60, 30));
        	menuPanel.add(mBtn[m]);
        	
        	mBtn[m].addActionListener(new Listener());
        }

        // 하단 패널
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // 수량 추가 버튼
        addButton = new JButton("+");
        addButton.addActionListener(new Listener());
        bottomPanel.add(addButton);
        
        //수량 라벨
        countLabel = new JLabel("수량");
        bottomPanel.add(countLabel);

        // 수량 삭제 버튼
        removeButton = new JButton("-");
        removeButton.addActionListener(new Listener());
        bottomPanel.add(removeButton);

        // 확인 버튼
        //confirmButton = new JButton("확인");
        //bottomPanel.add(confirmButton);
        

        // 결제 금액 표시
        //int payIndex = r.tableList.get(index).getIndex();
        //int pay = t.payTable();
        totalLabel = new JLabel("결제 금액: ");
        payLabel = new JLabel();
        bottomPanel.add(totalLabel);
        bottomPanel.add(payLabel);

        // 결제하기 버튼
        payButton = new JButton("결제하기");
        payButton.addActionListener(new Listener());
        bottomPanel.add(payButton);

        // 하단 패널을 프레임 하단에 추가
        centerPanel.add(bottomPanel, BorderLayout.SOUTH);
       
        // 프레임 설정
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			int tableIndex = s.getIndex(); // 테이블 인덱스 
			// 매장으로 버튼 눌렀을 경우 
			if (e.getSource() == storeButton) {

				setVisible(false);
			}
			
			// 메뉴 버튼 눌렀을 경우 
			int ml = mBtn.length;
			for (int i = 0; i <ml; i++) {
				if (mBtn[i] == null) {
					break;
				}
				if (e.getSource() == mBtn[i]) {
					String mId = r.menuList.get(i).getMenuId(); // 메뉴 아이디
					Menu m = new Menu(mId);
					int price = r.menuPrice(m); //메뉴 가격
					Order o = new Order(mId); // 메뉴 검색하기 용 
					Order order = new Order(mId, 1, price); // 추가할 주문 용
					
					// 주문할 메뉴의 인덱스 (몇 번 째로 주문된 메뉴인지)
					int orderIndex = r.tableList.get(tableIndex).searchOrder(o); 
					
					r.tableList.get(tableIndex).addOrder(order); // 주문 추가 
					int payIndex = r.tableList.get(tableIndex).getIndex();
					
					tableOrderPay += price; // 주문된 메뉴 추가된 금액 
					payLabel.setText(tableOrderPay + "원"); // 결제 금액 라벨 바꾸기 

					if (orderIndex == -1) { //처음 주문하는 경우 
						orderTableModel.addRow(new Object[] {mId, 1, price});
					} else {
						int count = r.tableList.get(tableIndex).orderList.get(orderIndex).getOrderCount();
						orderTableModel.setValueAt(count, orderIndex - payIndex , 1); // 수량 변경하기 
						orderTableModel.setValueAt(count * price, orderIndex - payIndex , 2); //가격 변경하기 
					}	
					
					break;

				}
			}
			
			// 결제하기 버튼 눌렀을 때 
			if (e.getSource() == payButton) {
				Pay p = new Pay();
				p.setVisible(true);
				//setVisible(false); // 테이블 별 주문 프레임은 안 보이게 
			}
			
	
		    // + 버튼 눌렀을 때
		    if (e.getSource() == addButton) {

				try {
					int i = orderTable.getSelectedRow(); // 클릭한 테이블 인덱스 
				    String mId = (String) orderTable.getValueAt(i, 0);
				    int orderCount = (int) orderTable.getValueAt(i, 1);
				    //int price = (int) orderTable.getValueAt(i, 2);
				    
				    Order o = new Order(mId);
				    Menu m = new Menu(mId);
				    int menuIndex = r.tableList.get(tableIndex).searchOrder(o); // 메뉴 인덱스 
				    int menuPrice = r.menuPrice(m); // 테이블에 있는 금액이 아닌 메뉴의 금액 
				    
				    r.tableList.get(tableIndex).orderList.get(menuIndex).setOrderCount(orderCount + 1); // 주문한 메뉴 수량 하나 더하기
				    int newCount = orderCount + 1; // 하나 더한 수량
				    tableOrderPay = newCount * menuPrice; // 수량에 대응하는 새 가격 
				    r.tableList.get(tableIndex).orderList.get(menuIndex).setOrderPrice(tableOrderPay); // 가격 수정하기 
				 
				    orderTable.setValueAt(newCount, i, 1); // 테이블 값 수량 변경 
				    orderTable.setValueAt(tableOrderPay, i, 2); // 테이블 값 금액 변경  
				    
				    
					payLabel.setText(tableOrderPay + "원"); // 결제 금액 라벨 바꾸기 
					
				} catch (Exception e4) {
					AlertDialog ad = new AlertDialog(15); // 테이블 선택하지 않고 + 버튼 눌렀을 경우 
				} 
			}
		    
		    // - 버튼 눌렀을 때
		    if (e.getSource() == removeButton) {
		    	try {
		    		int i = orderTable.getSelectedRow(); // 클릭한 테이블 인덱스 
				    String mId = (String) orderTable.getValueAt(i, 0);
				    int orderCount = (int) orderTable.getValueAt(i, 1);
				    //int price = (int) orderTable.getValueAt(i, 2);
				    
				    Order o = new Order(mId);
				    Menu m = new Menu(mId);
				    int menuIndex = r.tableList.get(tableIndex).searchOrder(o); // 메뉴 인덱스 
				    int menuPrice = r.menuPrice(m); // 테이블에 있는 금액이 아닌 메뉴의 금액 
				    
				    if (orderCount < 2) {
				    	// 최소 주문 수량을 1으로 
				    	AlertDialog ad = new AlertDialog(4);
				    } else { // 수량 빼기
				    	
				    	r.tableList.get(tableIndex).orderList.get(menuIndex).setOrderCount(orderCount - 1); // 주문한 메뉴 수량 하나 빼기 
					    int newCount = orderCount - 1; // 하나 뺀 수량 
					    tableOrderPay = newCount * menuPrice; // 수량에 대응하는 새 가격 
					    r.tableList.get(tableIndex).orderList.get(menuIndex).setOrderPrice(tableOrderPay); // 가격 수정하기 
					 
					    orderTable.setValueAt(newCount, i, 1); // 테이블 값 수량 변경 
					    orderTable.setValueAt(tableOrderPay, i, 2); // 테이블 값 금액 변경 
						
					    payLabel.setText(tableOrderPay + "원"); // 결제 금액 라벨 바꾸기 
				    }	
			    } catch (Exception e4) {
					AlertDialog ad = new AlertDialog(15); // 테이블을 선택하지 않고 -버튼을 눌렀을 경우 
				} 
			    
			}
			
		    try {
		    	// 테이블 파일처리
	    		FileOutputStream tfOut = null;
				ObjectOutputStream toOut = null;
				
				tfOut = new FileOutputStream("table.dat");
				toOut = new ObjectOutputStream(tfOut);
				r.tableOutput(toOut);
				
		    }  catch (FileNotFoundException e2) {
				AlertDialog ad = new AlertDialog(2);
			} catch (IOException e3) {
				AlertDialog ad = new AlertDialog(3);
			} 
		}
    }
    
    public static void main(String[] args) {
    	Store s = new Store();
        SwingUtilities.invokeLater(() -> {
        	
        	int i = s.getIndex();
            TableOrder gui = new TableOrder(i);
            gui.setVisible(true);
            
        });
    }
}
