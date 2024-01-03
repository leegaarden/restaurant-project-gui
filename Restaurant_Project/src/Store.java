import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Store extends JFrame {
	Restaurant r = new Restaurant();
	Table t = new Table();
	int index = 0; // tableList의 인덱스 
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	private JPanel topPanel;
	private JLabel hcLabel;
	private JButton[] hcBtn = new JButton[5]; // 1,2인 3,4인 5,6인 7,8인 9,10인 -> 총 5개 버튼 필요 
	private JButton backButton;
	
	private JPanel panel;
	private JButton[] tBtn = new JButton[100]; // 테이블 버튼의 배열
	//private JButton[] aBtn = new JButton[tBtn.length]; // 이용중 버튼의 배열 -> 이용중 버튼 삭제!
	private JPanel[] buttonPanels = new JPanel[tBtn.length]; // 테이블 버튼과 이용 중 버튼을 담을 배열
	//private JButton tableButton;
	
	public Store() {
		
		try {
    		// 테이블 파일처리
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
		
		// 테이블 개수
		int ts = r.tableList.size();
		
		// 프레임 설정
        setTitle("Store");
        setSize(750, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 상단 패널 생성
        topPanel = new JPanel(new FlowLayout());
        
        // 인원 라벨 생성
        hcLabel = new JLabel("인원: ");
        topPanel.add(hcLabel);
        
        
        int hl = hcBtn.length;
        for (int i = 0; i < hl; i++) {
        	hcBtn[i] = new JButton((i * 2 + 1) + "," + (i * 2 + 2) + "인");
        	hcBtn[i].addActionListener(new Listener());
        	topPanel.add(hcBtn[i]);
        }
        
        // "관리자 모드" 버튼 생성
        backButton = new JButton("관리자 모드");
        backButton.addActionListener(new Listener());
        backButton.setPreferredSize(new Dimension(100, 30)); // 작은 크기로 설정

        // 패널에 버튼 추가
        topPanel.add(backButton);
        

        // 패널을 프레임에 추가
        add(topPanel, BorderLayout.NORTH);

        // 대표 패널 생성
        panel = new JPanel();
        int row = ts / 3;
        panel.setLayout(new GridLayout(row, 3, 10, 10)); // 테이블 개수를 세 줄로 표현하기 
      

        // 버튼 생성 및 패널에 추가
        for (int i = 0; i < ts; i++) {
        	
        	int os = r.tableList.get(i).orderList.size();
        	int index = t.getIndex();
        	String tableId = r.tableList.get(i).getTableId(); 
        	String order = "";
        	
        	if (!t.getAvailable()) { //테이블에 주문 내역이 있을 경우 
        		for (int j = index; j < os; j++) {
            		String o = r.tableList.get(i).orderList.get(j).getMenuId() + "\n(" + r.tableList.get(i).orderList.get(j).getOrderCount() + ")" + 
            	               "," + r.tableList.get(i).orderList.get(j).getOrderPrice() + "\n";
            		order += o ;
            	}
        	}
        	
        	// 테이블 버튼 패널 생성
            buttonPanels[i] = new JPanel();
            buttonPanels[i].setLayout(new BorderLayout());
            
            // "이용중" 버튼 생성
            ///aBtn[i] = new JButton("이용중");
            // "이용중" 버튼의 투명도 조절
            //aBtn[i].setContentAreaFilled(false);
            //aBtn[i].addActionListener(new Listener());
            
            // 테이블 버튼 생성
            int hc = r.tableList.get(i).getHeadCount();
            tBtn[i] = new JButton(tableId+ "(" + hc + "인)" + "\n" +order);
            tBtn[i].setContentAreaFilled(false);
            tBtn[i].addActionListener(new Listener());
            
            // 패널에 "이용중" 버튼과 테이블 버튼 추가
            //buttonPanels[i].add(aBtn[i], BorderLayout.NORTH);
            buttonPanels[i].add(tBtn[i], BorderLayout.CENTER);

            // 패널을 대표 패널에 추가
            panel.add(buttonPanels[i]);
            
        }

        // 중앙 패널을 프레임에 추가
        add(panel);
	}
	
	class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//관리자 모드 버튼 눌렀을 경우 
			if (e.getSource() == backButton) {
				ManagementMode mm = new ManagementMode();
				mm.setVisible(true);
				
			}
			
			// 인원 선택 버튼 눌렀을 때 
			int hl = hcBtn.length;
			for (int i = 0; i < hl -1; i++) {
				if (e.getSource() == hcBtn[i]) {
					t.setHeadCount(i * 2 + 1);
					break;
				}
			}
			
			//테이블 버튼 눌렀을 때 
			int tl = tBtn.length;
			for (int i = 0; i < tl; i ++) {
				if (tBtn[i] == null) {
					break;
				}
				if (e.getSource() == tBtn[i]) {
					//int tHc = r.tableList.get(i).getHeadCount();
					int headCount = t.getHeadCount();
					
					if (headCount == 0) { //인원수를 클릭하지 않은 경우
						AlertDialog ad = new AlertDialog(14);
						break;
					}
					
					if (r.searchHc(headCount, i) == -1) { // 인원수를 초가했을 때
						AlertDialog ad = new AlertDialog(12);
						break;
					} else {
						if (r.tableList.get(i).getAvailable()) { // 테이블이 사용 가능 상태이면
							tBtn[i].setContentAreaFilled(true); // 테이블 색상 변경
							r.tableList.get(i).setAvailable(false); // 테이블 사용 불가 상태로 변경 
						} else {
							setIndex(i); // 테이블 인덱스 set 
		                    
							TableOrder to = new TableOrder(i);
							to.setVisible(true);
							break;
						}
					}
	
				}
			}

			/*
			//이용중 버튼 눌렀을 때
			int al = aBtn.length;
			for (int i = 0; i < al; i ++) {
				if (aBtn[i] == null) {
					break;
				}
				if (e.getSource() == aBtn[i]) { 
					if (aBtn[i].isContentAreaFilled()) {
                        aBtn[i].setContentAreaFilled(false); 
                        r.tableList.get(i).setAvailable(false); // 테이블 이용 가능 상태를 false로 바꾸기
                    } else {
                        aBtn[i].setContentAreaFilled(true);
                        r.tableList.get(i).setAvailable(true); // 테이블 이용가능 상태를 true로 바꾸기 
                    }
					break;
				}
			} */
				
		}
    }
	
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Store s = new Store();
            s.setVisible(true);
        });
    } 
}
