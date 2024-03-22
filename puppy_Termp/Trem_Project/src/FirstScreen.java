import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;	
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FirstScreen extends JFrame {
	public FirstScreen() {
		super("Pet Matket"); 														// 타이틀
		JPanel jPanel = new JPanel();   											// 창 만들기
		ImageIcon img = new ImageIcon
		("C:\\Users\\user\\eclipse-workspace\\Trem_Project\\background\\강아지.jpg");	// 배경화면 이미지 추가
		
		Label screen_click = new Label("화면을 클릭 해주세요");							// 화면 클릭할 수 있도록
		screen_click.setFont(new Font("맑은 고딕", Font.BOLD, 20));					// 라벨을 만듬
		screen_click.setForeground(Color.DARK_GRAY);								// 글씨에 색을 줌
		
		JButton btn_image = new JButton(img);										// 버튼 만들기
		btn_image.setPreferredSize(new Dimension(800,800)); 						// JFrame에서 버튼 크기조정
		
		jPanel.add(screen_click);													
		jPanel.add(btn_image);														// JPanel에 버튼 추가	
		add(jPanel); 																// 화면에 버튼 출력
		
		setSize(800,800); 															// 창 크기 조절
		setResizable(false);														// 전체화면 금지 
		setLocationRelativeTo(null);												// 창 크기 조절 여부
		setVisible(true);															// 화면 출력
		
		btn_image.addActionListener(new ActionListener() {							// 화면 클릭 시 나타나는 메소드
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new ManageChat();													// 서버실행(관리자 채팅방)	
				new Order();														// 주문 화면 출력
				setVisible(false);													// 클릭시 안보이게 할 것. 	
			}
		});
	}
	
	public static void main(String[] args) {
		new FirstScreen();															// 실행 시 첫 화면
		new JdbcConnect();															// mySQL 데이터베이스 연동
		new TableCreating();														// mySQL 테이블 생성
	}
}
