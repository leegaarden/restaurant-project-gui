import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagementMode extends JFrame {
	private JPanel topPanel;
    private JLabel tableLabel;
    private JButton storeButton;
    
    private JPanel bottomPanel;
    private JButton tableButton;
    private JButton menuButton;
    private JButton salesButton;

    public ManagementMode() {
        // 프레임 설정
        setTitle("관리자 모드");
        setSize(600, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 현재 프레임만 종료

        // 상단 패널
        topPanel = new JPanel(new BorderLayout());
        tableLabel = new JLabel("관리자 상태");
        topPanel.add(tableLabel, BorderLayout.WEST);

        storeButton = new JButton("매장으로");
        storeButton.addActionListener(new Listener());
        topPanel.add(storeButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // 하단 패널
        bottomPanel = new JPanel(new GridLayout(1, 3));
        tableButton = new JButton("테이블 관리");
        tableButton.addActionListener(new Listener());
        menuButton = new JButton("메뉴 관리");
        menuButton.addActionListener(new Listener());
        salesButton = new JButton("매출 관리");
        salesButton.addActionListener(new Listener());

        // 버튼 크기를 고정
        Dimension buttonSize = new Dimension(150, 50);
        tableButton.setPreferredSize(buttonSize);
        menuButton.setPreferredSize(buttonSize);
        salesButton.setPreferredSize(buttonSize);

        bottomPanel.add(tableButton);
        bottomPanel.add(menuButton);
        bottomPanel.add(salesButton);
        add(bottomPanel, BorderLayout.CENTER);
    }
    
    class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == tableButton) {
				TableManagement tm = new TableManagement();
				tm.setVisible(true);
				setVisible(false);
			}
			if (e.getSource() == menuButton) {
				MenuManagement mm = new MenuManagement();
				mm.setVisible(true);
				setVisible(false);
			}
			if (e.getSource() == salesButton) {
				SalesManagement sm = new SalesManagement();
				sm.setVisible(true);
				setVisible(false);
			}
			if (e.getSource() == storeButton) {
				Store s = new Store();
				s.setVisible(true);
				setVisible(false);
			}
			
		}
    }
    
}
