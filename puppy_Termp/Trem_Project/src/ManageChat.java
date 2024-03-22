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

public class ManageChat extends JFrame {			// ������ ä�ù�
	JTextArea textArea; 	//��� ��������
	JTextField tfMsg;
	JButton btnSend;
	
	ServerSocket serverSocket;
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	
	public ManageChat() {		
		setTitle("������ä�ù�");								// ����
		setBounds(450,50,500,350);
		
		textArea = new JTextArea();
		textArea.setEditable(false);						//���� ����
		JScrollPane scrollPane = new JScrollPane(textArea);	// ���̺� �����
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel msgPanel = new JPanel();						// ä���� �κ� �ǳ�
		msgPanel.setLayout(new BorderLayout());
		tfMsg = new JTextField();
		btnSend = new JButton("������");
		msgPanel.add(tfMsg, BorderLayout.CENTER);
		msgPanel.add(btnSend, BorderLayout.EAST);
		
		add(msgPanel, BorderLayout.SOUTH);
		
		// ������ ��ư Ŭ���� �����ϴ� ������ �߰�
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});
		
		setVisible(true);
		
		ServerThread serverThread = new ServerThread(); 	// ServerThread�� ��ü�� ����
		serverThread.setDaemon(true);						//
		serverThread.start();								//
		
		// â�� ������ �ѹ��� ������ �ʰ� �ϳ��� ����
		addWindowListener(new WindowAdapter(){
			@Override // Ŭ���̾�Ʈ �����ӿ� window(â) ���� ������ �߰�
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
		
		
	}// ������ �޼ҵ�

	//�̳�Ŭ���� ���������� �����ϰ� Ŭ���̾�Ʈ�� ������ ����ϰ�,
	//����Ǹ� �޼����� ���������� �޴� ���� ����
	class ServerThread extends Thread{
		@Override
		public void run() {
			try {
				serverSocket = new ServerSocket(8098);
				textArea.append("�մ��� �Խ��ϴ�!!\n");
				textArea.append("ģ���ϰ� �ȳ��� �ֽʽÿ�.\n");
				socket = serverSocket.accept();// Ŭ���̾�Ʈ�� ������ ������ Ŀ��(������)�� ���
				
				//����� ���� ��Ʈ�� ����
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				
				// ������ ������ �����͸� �б�
				while(true) {
					String msg = dis.readUTF(); // ������ ���������� ���
					textArea.append("[��] : " + msg + "\n");
					textArea.setCaretPosition(textArea.getText().length());
				}
				
			}catch(IOException e) {
				textArea.append("Ŭ���̾�Ʈ�� �������ϴ�.\n");
			}
		}	
	}
	
	//�޽��� �����ϴ� ��� �޼ҵ�
	void sendMessage() {
		String msg = tfMsg.getText();  //TextField�� ���ִ� �۾��� ������
		tfMsg.setText(""); 			//�Է� �� ��ĭ����
		textArea.append("[������] : " + msg + "\n"); // 1.TextArea(ä��â)�� ǥ��
		textArea.setCaretPosition(textArea.getText().length());// ��ũ�� ���󰡰�
		//2. ����(Client)���� �޼��� �����ϱ�
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
