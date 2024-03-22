import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ManageChat extends JFrame {			// 관리자 채팅방
	JTextArea textArea; 	//멤버 참조변수
	JTextField tfMsg;
	JButton btnSend;
	
	ServerSocket serverSocket;
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	
	public ManageChat() {		
		setTitle("관리자채팅방");								// 주제
		setBounds(450,50,500,350);
		
		textArea = new JTextArea();
		textArea.setEditable(false);						//쓰기 금지
		JScrollPane scrollPane = new JScrollPane(textArea);	// 테이블 만들기
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel msgPanel = new JPanel();						// 채팅장 부분 판넬
		msgPanel.setLayout(new BorderLayout());
		tfMsg = new JTextField();
		btnSend = new JButton("보내기");
		msgPanel.add(tfMsg, BorderLayout.CENTER);
		msgPanel.add(btnSend, BorderLayout.EAST);
		
		add(msgPanel, BorderLayout.SOUTH);
		
		// 보내기 버튼 클릭에 반응하는 리스너 추가
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});
		
		setVisible(true);
		
		ServerThread serverThread = new ServerThread(); 	// ServerThread의 객체를 실행
		serverThread.setDaemon(true);						//
		serverThread.start();								//
		
		// 창을 닫으면 한번에 닫히지 않고 하나씩 닫힘
		addWindowListener(new WindowAdapter(){
			@Override // 클라이언트 프레임에 window(창) 관련 리스너 추가
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				try {
					if(dos != null) {
						dos.close();
					}else if(dis!=null) {
						dis.close();
					}else if (socket!=null) {
						socket.close();
					}else if (serverSocket != null) {
						serverSocket.close();
					}
				}catch(IOException e1) {
					e1.printStackTrace();
				}
			}
		});	
		
		
	}// 생성자 메소드

	//이너클래싀 서버소켓을 생성하고 클라이언트의 연결을 대기하고,
	//연결되면 메세지를 지속적으로 받는 역할 수행
	class ServerThread extends Thread{
		@Override
		public void run() {
			try {
				serverSocket = new ServerSocket(8098);
				textArea.append("손님이 왔습니다!!\n");
				textArea.append("친절하게 안내해 주십시오.\n");
				socket = serverSocket.accept();// 클라이언트가 접속할 때까지 커서(스레드)가 대기
				
				//통신을 위한 스트림 생성
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				
				// 상대방이 보내온 데이터를 읽기
				while(true) {
					String msg = dis.readUTF(); // 상대방이 보낼때까지 대기
					textArea.append("[고객] : " + msg + "\n");
					textArea.setCaretPosition(textArea.getText().length());
				}
				
			}catch(IOException e) {
				textArea.append("클라이언트가 나갔습니다.\n");
			}
		}	
	}
	
	//메시지 전송하는 기능 메소드
	void sendMessage() {
		String msg = tfMsg.getText();  //TextField에 써있는 글씨를 얻어오기
		tfMsg.setText(""); 			//입력 후 빈칸으로
		textArea.append("[관리자] : " + msg + "\n"); // 1.TextArea(채팅창)에 표시
		textArea.setCaretPosition(textArea.getText().length());// 스크롤 따라가게
		//2. 상대방(Client)에게 메세지 전송하기
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					dos.writeUTF(msg);
					dos.flush();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}
}
