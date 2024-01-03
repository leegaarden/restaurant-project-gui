import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MenuManagement extends JFrame {
	
	Restaurant r = new Restaurant();
	
	private JPanel topPanel;
    private JLabel titleLabel;
    private JButton backButton;
    
    private JPanel centerPanel;
    private DefaultTableModel menuTableModel;
    private JTable menuTable;
    private JScrollPane tableScrollPane;
    
    private JPanel bottomPanel;
    private JLabel idSLabel;
    private JLabel nameSLabel;
    private JLabel priceSLabel;
    private JTextField idSField;
    private JTextField nameSField;
    private JTextField priceSField;
    private JButton searchButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private Color originalSelectionColor;
    
    
    public MenuManagement() {
    	
    	try {
    		FileInputStream fin = null;
    		ObjectInputStream oin = null;
    		
    		fin = new FileInputStream("menu.dat");
    		oin = new ObjectInputStream(fin);
    		
    		r.menuInput(oin);
    	} catch (ClassNotFoundException e) {
    		AlertDialog ad = new AlertDialog(2);
		} catch (FileNotFoundException e) {
			AlertDialog ad = new AlertDialog(2);
		} catch (IOException e) {
			AlertDialog ad = new AlertDialog(3);
		} 
    	
        // 프레임 설정
        setTitle("메뉴 관리");
        setSize(1000, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 상단 패널
        topPanel = new JPanel(new BorderLayout());
        titleLabel = new JLabel("메뉴 관리");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        topPanel.add(titleLabel, BorderLayout.WEST);

        backButton = new JButton("관리자 모드");
        backButton.addActionListener(new Listener());
        topPanel.add(backButton, BorderLayout.EAST);

        // 가운데 패널
        centerPanel = new JPanel(new BorderLayout());

        // 메뉴 테이블 생성
        menuTableModel = new DefaultTableModel(new String[]{"메뉴 아이디", "메뉴 이름", "가격"}, 0);
        menuTable = new JTable(menuTableModel);
        tableScrollPane = new JScrollPane(menuTable);

        // 메뉴 데이터 추가
        int ms = r.menuList.size(); 
        for (int i = 0; i < ms; i++) {
            menuTableModel.addRow(new Object[]{r.menuList.get(i).getMenuId(), r.menuList.get(i).getName(), r.menuList.get(i).getPrice()});
        }
        
        // 스크롤 패널을 가운데 패널에 추가 
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);

        // 하단 패널
        bottomPanel = new JPanel();
        
        //메뉴 아이디 라벨
        idSLabel = new JLabel("메뉴 아이디:");
        bottomPanel.add(idSLabel);
        
        //아이디 검색 필드
        idSField = new JTextField(15);
        bottomPanel.add(idSField);
        
        //이름 라벨
        nameSLabel = new JLabel("이름:");
        bottomPanel.add(nameSLabel);
        
        //이름 검색 필드
        nameSField = new JTextField(15);
        bottomPanel.add(nameSField);
        
        //가격 라벨
        priceSLabel = new JLabel("가격:");
        bottomPanel.add(priceSLabel);
        
        //가격 검색 필드
        priceSField = new JTextField(15);
        bottomPanel.add(priceSField);

        //검색 버튼
        searchButton = new JButton("검색");
        searchButton.addActionListener(new Listener());
        bottomPanel.add(searchButton);

        //추가 버튼
        addButton = new JButton("추가");
        addButton.addActionListener(new Listener());
        bottomPanel.add(addButton);

        //삭제 버튼 
        deleteButton = new JButton("삭제");
        deleteButton.addActionListener(new Listener());
        bottomPanel.add(deleteButton);

        //수정 버튼 
        updateButton = new JButton("수정");
        updateButton.addActionListener(new Listener());
        bottomPanel.add(updateButton);

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
				String id = idSField.getText();
				
				Menu m = new Menu(id);
				
				try {
					int s = r.searchMenu(m);
					if (s == -1 ) {
						AlertDialog ad = new AlertDialog(10); //없을 경우
					} else {
						//있을 경우 해당 행을 노랑색으로 표시 
						 menuTable.setRowSelectionInterval(s, s);
	                     menuTable.setSelectionBackground(Color.YELLOW);
					}
				} catch (java.lang.NullPointerException NullException) {
					AlertDialog ad = new AlertDialog(10);
				} catch (java.util.InputMismatchException e1) {
					AlertDialog ad = new AlertDialog(11);
				} 
			}
			//추가 버튼 눌렀을 경우
			if (e.getSource() == addButton) {
				
				try {
					String id = idSField.getText();
					String name = nameSField.getText();
					String price = priceSField.getText();
					int p = Integer.parseInt(price);
					Menu m = new Menu(id, name, p);
					
					r.addMenu(m);
					menuTableModel.addRow(new Object[]{m.menuId, m.name, m.price});
					
					FileOutputStream fOut = null;
		    		ObjectOutputStream oOut = null;
		    		
		    		fOut = new FileOutputStream("menu.dat");
		    		oOut = new ObjectOutputStream(fOut);
		    		
		    		r.menuOutput(oOut);
				} catch (NumberFormatException e4) {
					AlertDialog ad = new AlertDialog(11);
				} catch (FileNotFoundException e1) {
					AlertDialog ad = new AlertDialog(2);
				} catch (IOException e2) {
					AlertDialog ad = new AlertDialog(3);
				} catch (Exception e3) { 
					AlertDialog ad = new AlertDialog(6); //이미 존재하여 추가 불가능
					String id = idSField.getText();
					String name = nameSField.getText();
					String price = priceSField.getText();
					int p = Integer.parseInt(price);
					Menu m = new Menu(id, name, p);
					int i = r.searchMenu(m);
					menuTable.setRowSelectionInterval(i, i);
                    originalSelectionColor = menuTable.getSelectionBackground();
                    menuTable.setSelectionBackground(Color.YELLOW);
				} 
			}
			//삭제 버튼을 눌렀을 경우 
			if (e.getSource() == deleteButton) {
				
				try {
					int i = menuTable.getSelectedRow();
					String dId = (String) menuTable.getValueAt(i, 0);
					String dName = (String) menuTable.getValueAt(i, 1);
					int dPrice = (int) menuTable.getValueAt(i, 2);
					
					Menu m = new Menu(dId, dName, dPrice);
					
					if(i != -1) {
						menuTableModel.removeRow(i);
					}
					r.removeMenu(m);
					
					FileOutputStream fOut = null;
		    		ObjectOutputStream oOut = null;
		    		
		    		fOut = new FileOutputStream("menu.dat");
		    		oOut = new ObjectOutputStream(fOut);
		    		
		    		r.menuOutput(oOut);
				} catch (FileNotFoundException e1) {
					AlertDialog ad = new AlertDialog(2);
				} catch (IOException e2) {
					AlertDialog ad = new AlertDialog(3);
				} catch (Exception e3) {
					AlertDialog ad = new AlertDialog(5); //존재하지 않아 삭제 불가능
				} 
			}
			//수정하기 버튼을 눌렀을 경우
			if (e.getSource() == updateButton) {
				
				try {
					int i = menuTable.getSelectedRow();
					String dId = (String) menuTable.getValueAt(i, 0);
					String dName = (String) menuTable.getValueAt(i, 1);
					int dPrice = (int) menuTable.getValueAt(i, 2);
					String price = priceSField.getText();
					int newPrice = Integer.parseInt(price);
					
					Menu m = new Menu(dId, dName, dPrice);
					Menu update = new Menu(dId, dName, newPrice);
					
					r.removeMenu(m);
					r.addMenu(update);
					
					menuTable.setValueAt(newPrice, i, 2); //테이블 데이터 수정
					
					FileOutputStream fOut = null;
		    		ObjectOutputStream oOut = null;
		    		
		    		fOut = new FileOutputStream("menu.dat");
		    		oOut = new ObjectOutputStream(fOut);
		    		
		    		r.menuOutput(oOut);
				} catch (NumberFormatException e4) {
					AlertDialog ad = new AlertDialog(11); //가격을 입력하지 않았을 경우
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
            MenuManagement frame = new MenuManagement();
            frame.setVisible(true);
        });
    }
}
