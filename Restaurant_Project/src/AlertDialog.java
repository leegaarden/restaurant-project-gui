import javax.swing.JOptionPane;

public class AlertDialog {
	
	public AlertDialog(int i) {
		
		if (i == 1) {
			//설정을 완료하세요 안내창
			JOptionPane.showMessageDialog(null, "설정을 완료하세요", "정보", JOptionPane.INFORMATION_MESSAGE);
		} else if (i == 2) {
			//파일을 찾을 수 없습니다.
			JOptionPane.showMessageDialog(null, "파일을 찾을 수 없습니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
		} else if (i == 3) {
			//파일은 존재하나 에러가 발생했습니다. 
			JOptionPane.showMessageDialog(null, "파일은 존재하나 에러가 발생했습니다. ", "오류", JOptionPane.ERROR_MESSAGE);
		} else if (i == 4) {
			//최소 주문 수량은 1입니다.
			JOptionPane.showMessageDialog(null, "최소 주문 수량은 1입니다.", "오류", JOptionPane.ERROR_MESSAGE);
		} else if (i == 5) {
			//존재하지 않아 삭제할 수 없습니다. 
			JOptionPane.showMessageDialog(null, "존재하지 않아 삭제할 수 없습니다. ", "오류", JOptionPane.ERROR_MESSAGE);
		} else if (i == 6) {
			//이미 존재하여 추가할 수 없습니다. 
			JOptionPane.showMessageDialog(null, "이미 존재하여 추가할 수 없습니다. ", "오류", JOptionPane.ERROR_MESSAGE);
		} else if (i == 7) {
			//존재하는 테이블입니다.
			JOptionPane.showMessageDialog(null, "존재하는 테이블입니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
		} else if (i == 8) {
			//존재하지 않는 테이블입니다.
			JOptionPane.showMessageDialog(null, "존재하지 않는 테이블입니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
		} else if (i == 9) {
			//존재하는 메뉴입니다. 
			JOptionPane.showMessageDialog(null, "존재하는 메뉴입니다. ", "정보", JOptionPane.INFORMATION_MESSAGE);
		} else if (i == 10) {
			//존재하지 않는 메뉴입니다. 
			JOptionPane.showMessageDialog(null, "존재하지 않는 메뉴입니다. ", "정보", JOptionPane.INFORMATION_MESSAGE);
		} else if (i == 11) {
			//올바르게 입력하세요.
			JOptionPane.showMessageDialog(null, "올바르게 입력하세요. ", "오류", JOptionPane.ERROR_MESSAGE);
		} else if (i == 12) {
			//파일을 찾을 수 없습니다.
			JOptionPane.showMessageDialog(null, "인원을 초과했습니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
		} else if (i == 13) {
			//파일을 찾을 수 없습니다.
			JOptionPane.showMessageDialog(null, "지원하지 않는 인원 수 입니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
		} else if (i == 14) {
			//파일을 찾을 수 없습니다.
			JOptionPane.showMessageDialog(null, "인원수를 클릭하세요.", "정보", JOptionPane.INFORMATION_MESSAGE);
		} else if (i == 15) {
			//파일을 찾을 수 없습니다.
			JOptionPane.showMessageDialog(null, "메뉴를 선택해주세요.", "정보", JOptionPane.INFORMATION_MESSAGE);
		} 
	}
}
