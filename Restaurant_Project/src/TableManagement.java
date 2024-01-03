import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.*;

public class TableManagement extends JFrame {
	
	Restaurant r = new Restaurant();
	
	private JPanel topPanel;
    private JLabel titleLabel;
    private JButton backButton;
    
    private JPanel centerPanel;
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane tableScrollPane;
    
    private JPanel bottomPanel;
    private JLabel searchLabel;
    private JTextField searchField;
    private JLabel HeadCountLabel;
    private JTextField HeadCountField;
    private JButton searchButton;
    private JButton addButton;
    private JButton deleteButton;
    private Color originalSelectionColor;

    public TableManagement() {
    	
    	try {
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
			//안내창 띄우기
			AlertDialog ad = new AlertDialog(3);
		} 
        // 프레임 설정
        setTitle("테이블 관리");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        
        // 상단 패널
        topPanel = new JPanel(new BorderLayout());
        titleLabel = new JLabel("<html><body> [테이블 관리] <br> 테이블 목록 </body><html>");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        topPanel.add(titleLabel, BorderLayout.WEST);

        // 관리자 모드 버튼
        backButton = new JButton("관리자모드");
        backButton.addActionListener(new Listener());
        topPanel.add(backButton, BorderLayout.EAST);

        // 가운데 패널
        centerPanel = new JPanel(new BorderLayout());

        // 테이블 모델 생성
        tableModel = new DefaultTableModel();
        tableModel.addColumn("테이블 아이디");
        tableModel.addColumn("인원");

        // JTable 생성 및 모델 설정
        table = new JTable(tableModel);
        tableScrollPane = new JScrollPane(table);

        // 테이블 데이터 추가
        int ts = r.tableList.size(); 
        for (int i = 0; i < ts; i++) {
            tableModel.addRow(new Object[]{r.tableList.get(i).getTableId(), r.tableList.get(i).getHeadCount()});
        }

        //스크롤 패널을 가운데 패널에 추가 
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);

        // 하단 패널
        bottomPanel = new JPanel();
        
        //테이블 아이디 라벨
        searchLabel = new JLabel("테이블 아이디: ");
        bottomPanel.add(searchLabel);

        // 테이블 검색 필드
        searchField = new JTextField(15);
        bottomPanel.add(searchField);
        
        //인원 라벨
        HeadCountLabel = new JLabel("인원: ");
        bottomPanel.add(HeadCountLabel);
        
        // 테이블 검색 필드
        HeadCountField = new JTextField(15);
        bottomPanel.add(HeadCountField);
        
        // 검색 버튼
        searchButton = new JButton("검색");
        searchButton.addActionListener(new Listener());
        bottomPanel.add(searchButton);

        // 추가 버튼
        addButton = new JButton("추가");
        addButton.addActionListener(new Listener());
        bottomPanel.add(addButton);

        // 삭제 버튼
        deleteButton = new JButton("삭제");
        deleteButton.addActionListener(new Listener());
        bottomPanel.add(deleteButton);

        // 상단 패널을 프레임 상단에 추가
        add(topPanel, BorderLayout.NORTH);

        // 가운데 패널을 프레임 중앙에 추가
        add(centerPanel, BorderLayout.CENTER);

        // 하단 패널을 프레임 하단에 추가
        add(bottomPanel, BorderLayout.SOUTH);
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
			//검색 버튼 눌렀을 경우
			if (e.getSource() == searchButton) {
				String tId = searchField.getText();
				Table t = new Table(tId);
				
				try {
					int s = r.searchTable(t);
					if (s == -1 ) {
						AlertDialog ad = new AlertDialog(8); //없을 경우
					} else {
						//있을 경우 해당 행을 노랑색으로 표시 
						 table.setRowSelectionInterval(s, s);
	                     table.setSelectionBackground(Color.YELLOW);
					}
				} catch (java.lang.NullPointerException NullException) {
					AlertDialog ad = new AlertDialog(8);
				} catch (java.util.InputMismatchException e1) {
					AlertDialog ad = new AlertDialog(11);
				}
			}
			//추가 버튼 눌렀을 경우
			if (e.getSource() == addButton) {
				
				try {
					String tId = searchField.getText();
					String hc = HeadCountField.getText();
					int thc = Integer.parseInt(hc);
					Table t = new Table(tId, thc);
					int i = r.searchTable(t);
					
					r.addTable(t);
					tableModel.addRow(new Object[]{t.tableId, t.headCount});
					
					FileOutputStream fOut = null;
		    		ObjectOutputStream oOut = null;
		    		
		    		fOut = new FileOutputStream("table.dat");
		    		oOut = new ObjectOutputStream(fOut);
		    		
		    		r.tableOutput(oOut);
				} catch (NumberFormatException e4) {
					AlertDialog ad = new AlertDialog(11);
				} catch (FileNotFoundException e1) {
					AlertDialog ad = new AlertDialog(2);
				} catch (IOException e2) {
					AlertDialog ad = new AlertDialog(3);
				} catch (Exception e3) {
					AlertDialog ad = new AlertDialog(6); //이미 존재하여 추가 불가능
					String tId = searchField.getText();
					String hc = HeadCountField.getText();
					int thc = Integer.parseInt(hc);
					Table t = new Table(tId, thc);
					int i = r.searchTable(t);
					table.setRowSelectionInterval(i, i);
                    originalSelectionColor = table.getSelectionBackground();
                    table.setSelectionBackground(Color.YELLOW);
				} 
			}
			//삭제 버튼을 눌렀을 경우 
			if (e.getSource() == deleteButton) {
				
				try {
					int i = table.getSelectedRow();
					String dId = (String) table.getValueAt(i, 0);
					int dHc = (int) table.getValueAt(i, 1);
					
					Table t = new Table(dId, dHc);
					
					if(i != -1) {
						tableModel.removeRow(i);
					}
					r.removeTable(t);
					
					FileOutputStream fOut = null;
		    		ObjectOutputStream oOut = null;
		    		
		    		fOut = new FileOutputStream("table.dat");
		    		oOut = new ObjectOutputStream(fOut);
		    		
		    		r.tableOutput(oOut);
				} catch (FileNotFoundException e1) {
					AlertDialog ad = new AlertDialog(2);
				} catch (IOException e2) {
					AlertDialog ad = new AlertDialog(3);
				} catch (Exception e3) {
					AlertDialog ad = new AlertDialog(5); //존재하지 않아 삭제 불가능
				} 
			}
		}
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
       
            TableManagement tm = new TableManagement();
            tm.setVisible(true);
        });
    }
}
