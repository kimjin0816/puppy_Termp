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
		super("Pet Matket"); 														// Ÿ��Ʋ
		JPanel jPanel = new JPanel();   											// â �����
		ImageIcon img = new ImageIcon
		("C:\\Users\\user\\eclipse-workspace\\Trem_Project\\background\\������.jpg");	// ���ȭ�� �̹��� �߰�
		
		Label screen_click = new Label("ȭ���� Ŭ�� ���ּ���");							// ȭ�� Ŭ���� �� �ֵ���
		screen_click.setFont(new Font("���� ���", Font.BOLD, 20));					// ���� ����
		screen_click.setForeground(Color.DARK_GRAY);								// �۾��� ���� ��
		
		JButton btn_image = new JButton(img);										// ��ư �����
		btn_image.setPreferredSize(new Dimension(800,800)); 						// JFrame���� ��ư ũ������
		
		jPanel.add(screen_click);													
		jPanel.add(btn_image);														// JPanel�� ��ư �߰�	
		add(jPanel); 																// ȭ�鿡 ��ư ���
		
		setSize(800,800); 															// â ũ�� ����
		setResizable(false);														// ��üȭ�� ���� 
		setLocationRelativeTo(null);												// â ũ�� ���� ����
		setVisible(true);															// ȭ�� ���
		
		btn_image.addActionListener(new ActionListener() {							// ȭ�� Ŭ�� �� ��Ÿ���� �޼ҵ�
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new ManageChat();													// ��������(������ ä�ù�)	
				new Order();														// �ֹ� ȭ�� ���
				setVisible(false);													// Ŭ���� �Ⱥ��̰� �� ��. 	
			}
		});
	}
	
	public static void main(String[] args) {
		new FirstScreen();															// ���� �� ù ȭ��
		new JdbcConnect();															// mySQL �����ͺ��̽� ����
		new TableCreating();														// mySQL ���̺� ����
	}
}
