import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;

public class SalesManagement extends JFrame {
	
	Table t = new Table();
	Restaurant r = new Restaurant();
	
	private JPanel topPanel;
    private JLabel titleLabel;
    private JButton backButton;
    
    private JPanel centerPanel;
    private JTable paymentTable;
    private DefaultTableModel tableModel;
    private JLabel totalSalesLabel;

    public SalesManagement() {
    	
    	try {
    		//총액 파일처리
    		FileInputStream ttfIn = null;
			DataInputStream ttdIn = null;
			
			ttfIn = new FileInputStream("total.dat");
			ttdIn = new DataInputStream(ttfIn); 
			
			r.totalInput(ttdIn);
			ttfIn.close(); ttdIn.close();
			
    		//테이블 파일처리
    		FileInputStream tfIn = null;
			ObjectInputStream toIn = null;
			
			tfIn = new FileInputStream("table.dat");
			toIn = new ObjectInputStream(tfIn);
			r.tableInput(toIn);
    	} catch (ClassNotFoundException e) {
    		AlertDialog ad = new AlertDialog(2);
		} catch (FileNotFoundException e) {
			AlertDialog ad = new AlertDialog(2);
		} catch (IOException e) {
			AlertDialog ad = new AlertDialog(3);
		} 
    	
        // 프레임 설정
        setTitle("매출 관리");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 상단 패널
        topPanel = new JPanel(new BorderLayout());
        titleLabel = new JLabel("[매출 관리]");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        topPanel.add(titleLabel, BorderLayout.WEST);

        // 관리자 모드 버튼
        backButton = new JButton("관리자 모드");
        backButton.addActionListener(new Listener());
        topPanel.add(backButton, BorderLayout.EAST);

        // 가운데 패널
        centerPanel = new JPanel(new BorderLayout());

        //테이블 모델 생성
        tableModel = new DefaultTableModel();
        tableModel.addColumn("메뉴 아이디");
        tableModel.addColumn("수량");
        tableModel.addColumn("가격");

        // JTable 생성 및 모델 설정
        paymentTable = new JTable(tableModel);
        paymentTable.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        JScrollPane paymentScrollPane = new JScrollPane(paymentTable);
        centerPanel.add(paymentScrollPane, BorderLayout.CENTER);
        
        // 테이블 데이터 추가
        int ts = r.tableList.size();
        for (int i = 0; i < ts; i++) {
        	int os = r.tableList.get(i).orderList.size();
        	for (int j = 0; j < os; j++) {
        		tableModel.addRow(new Object[] {r.tableList.get(i).orderList.get(j).getMenuId(), r.tableList.get(i).orderList.get(j).getOrderCount(), r.tableList.get(i).orderList.get(j).getOrderPrice()});
        	}
        }
        /*
        for (int i = 0; i < os; i++) {
        	tableModel.addRow(new Object[] {t.orderList.get(i).getMenuId(), t.orderList.get(i).getOrderCount(), t.orderList.get(i).getOrderPrice()});
        }*/

        // 오른쪽 패널
        JPanel rightPanel = new JPanel(new BorderLayout());
        totalSalesLabel = new JLabel("  총 매출: " + r.getTotal() + "원  ");
        rightPanel.add(totalSalesLabel, BorderLayout.EAST);

        // 상단 패널을 프레임 상단에 추가
        add(topPanel, BorderLayout.NORTH);

        // 가운데 패널을 프레임 중앙에 추가
        add(centerPanel, BorderLayout.CENTER);

        // 오른쪽 패널을 프레임 오른쪽에 추가
        add(rightPanel, BorderLayout.EAST);
    }
    
    class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			//관리자 모드 버튼 눌렀을 경우 
			if (e.getSource() == backButton) {
				ManagementMode mm = new ManagementMode();
				mm.setVisible(true);
				setVisible(false);
			}
		}
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SalesManagement frame = new SalesManagement();
            frame.setVisible(true);
        });
    }
}
