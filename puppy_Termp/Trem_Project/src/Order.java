import java.awt.BorderLayout;					// (��,��,��,��,����)��ġ�� ������ �� �ִ� ���̾ƿ�
import java.awt.Button;							// ��ư
import java.awt.Color;							// ����
import java.awt.Dimension;						// ������Ʈ�� ���� ���̸� ���� ���е��� ĸ��ȭ�Ѵ�.
import java.awt.FlowLayout;						// ����� �߽����� �������� �����ϴ� ���̾ƿ� 
import java.awt.Font;							// �۾�ü
import java.awt.GridLayout;						// ��� ���� �����Ͽ� ��ġ�ϴ� ���̾ƿ�
import java.awt.Label;							// �б� ���� �ؽ�Ʈ�� ǥ���ϴµ� ����Ѵ�.
import java.awt.Panel;							// ���� ���α׷��� �ٸ� ������Ҹ� ������ �� �ִ� ������ ����
import java.awt.TextField;						// �� �� �ؽ�Ʈ�� �Է��ϰ� ������ �� �ֵ��� �ϴ� �ؽ�Ʈ ���� ���
import java.awt.event.ActionEvent;				// ��ư�̳� �޴� �׸��� Ŭ���� ������ �˸��� �޴´�.
import java.awt.event.ActionListener;			// actionPerformed()��� �ϳ��� �޼��常 �ִ�.
import java.io.DataInputStream;					// �Է� ��Ʈ������ �⺻ �����͸� ���� �� �ֽ��ϴ�.
import java.io.DataOutputStream;				// ������ ������ ��� ��Ʈ��
import java.io.IOException;						// �� ��ĳ���� �б� ���� �׸񿡼� ���������� �߻��� IOException�� �������� �� ���Ǵ� Java ��ĳ�� Ŭ���� �� �޼���
import java.io.InputStream;						// ����Ʈ ������ �����͸� �д´�. �ܺηκ��� �о� ���̴� ��ɰ��� Ŭ������
import java.io.OutputStream;					// �ܺη� �����͸� �����Ѵ�. �ܺη� �����͸� �����ϴ� ��� ���� Ŭ������
import java.net.Socket;							// Ŭ���̾�Ʈ ���� TCP ����� �����ϱ� ���� �ڹ��� �⺻ Ŭ����
import java.net.UnknownHostException;			// DNS Ȯ�� ������ ������ ��Ÿ����.
import java.sql.Connection;						// �����ͺ��̽��� �������ִ� ��ü
import java.sql.DriverManager;					// ������ �õ��� JDBC ����̹��� ����
import java.sql.PreparedStatement;				// SQL������ �����Ű�� ����� ���� ��ü
import java.sql.SQLException;					// �����ͺ��̽� �׼��� ���� �Ǵ� ��Ÿ ������ ���� ������ �����ϴ� ����


import javax.swing.ImageIcon;					// �̹��� �ִ� Icon
import javax.swing.JButton;						// �÷��� ������ ������ �ִ� ���̺��� ������ ����
import javax.swing.JFrame;						// ������â�� ���� ��
import javax.swing.JLabel;						// ���� ���� �� 
import javax.swing.JPanel;						// �淮 ���� ��Ҹ� ���� ���� �����̳ʸ� ����
import javax.swing.JScrollPane;					// table�� ��ũ�� ����
import javax.swing.JTable;						// ��� ���� �̷���� ���̺� ����
import javax.swing.JTextArea;					// �� �� �Է��� ������ ��ü
import javax.swing.JTextField;					// ���� �� �Է��� ������ ��ü
import javax.swing.table.DefaultTableModel;		// ���̺� ���� ��ų־��� ��ü

public class Order extends Thread implements ActionListener{	
	JFrame Or_frame;
	
	Panel NorthPanel
		, CenterPanel1, CenterPanel2, CenterPanel3
		, SouthPanel1, SouthPanel2, SouthPanel3
		, EastPanel;
	
	JLabel title ;
	JButton btn_feed, btn_snack, btn_supplie;
	
	JButton btn_next, btn_reset, btn_exit;
	
	JTable table;
	DefaultTableModel model;
	JScrollPane tablescroll, scrollPane1;
	JTextArea textArea;
	JTextField tfMsg;
	JPanel sp3_South;
	JButton btn_send;	
	
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	ClientThread clientThread;
	
	String basket[] = new String[5];
	Thread t;

	int count, total, result;
	
	// ������ ��ǰ ������ ����
	public Order() {										
		Or_frame = new JFrame("������ ��ǰ");								// ��ü���� â										
										
        NorthPanel = new Panel();										// BorderLayout�� ���ʿ� ��ġ
        NorthPanel.setBackground(Color.orange);							// 
        NorthPanel.setLayout(new FlowLayout(FlowLayout.LEFT));			//
        Or_frame.add(NorthPanel, BorderLayout.NORTH);					//
        																//
        title = new JLabel("�������");									//
        title.setFont(new Font("Serif", Font.BOLD, 11));				//
        title.setForeground(Color.BLUE);								//
        title.setFont(title.getFont().deriveFont(15.0f));				//
        NorthPanel.add(title);											//
        																//
        btn_feed = new JButton("���");									//
        btn_feed.setPreferredSize(new Dimension(100,70));				//
        btn_feed.setFont(btn_feed.getFont().deriveFont(15.0f));			//
        btn_feed.setBackground(Color.pink);								//
        NorthPanel.add(btn_feed);										//
        																//
        btn_snack = new JButton("����");									//
        btn_snack.setPreferredSize(new Dimension(100,70));				//
        btn_snack.setBackground(Color.pink);							//
        btn_snack.setFont(btn_snack.getFont().deriveFont(15.0f));		//
        NorthPanel.add(btn_snack);										//
        																//
        btn_supplie = new JButton("��ǰ");								//
        btn_supplie.setPreferredSize(new Dimension(100,70));			//
        btn_supplie.setBackground(Color.pink);							//
        btn_supplie.setFont(btn_supplie.getFont().deriveFont(15.0f));	//
        NorthPanel.add(btn_supplie);									//
        					
        
		SouthPanel1 = new Panel();										// BorderLayout�� ���ʿ� ��ġ
		SouthPanel1.setLayout(new GridLayout(1,2));						//
																		//
		SouthPanel2 = new Panel();										//
		String dataHead[] = {"��ǰ��","�ܰ�","����","�հ�","�� �ݾ�"};			//
		String data[][] = {};											//
		model = new DefaultTableModel(data, dataHead);					//
		table = new JTable(model);										//
		tablescroll = new JScrollPane(table);							//
		tablescroll.setPreferredSize(new Dimension(445,200));			//
		SouthPanel2.add(tablescroll);									//
		SouthPanel1.add(SouthPanel2);									//
																		//
		SouthPanel3 = new Panel();										//
		SouthPanel3.setLayout(new BorderLayout());						//
																		//
		sp3_South = new JPanel();										//
		sp3_South.setLayout(new BorderLayout());						//
																		//
		textArea = new JTextArea();										//
		scrollPane1 = new JScrollPane(textArea);						//
																		//
		tfMsg = new JTextField();										//
		btn_send = new JButton("������");									//
																		//
		textArea.setEditable(false);									//
		SouthPanel3.add(scrollPane1, BorderLayout.CENTER);				//
		sp3_South.add(tfMsg, BorderLayout.CENTER);						//
		sp3_South.add(btn_send, BorderLayout.EAST);						//
		SouthPanel3.add(sp3_South, BorderLayout.SOUTH);					//
		SouthPanel1.add(SouthPanel3);									//
		Or_frame.add(SouthPanel1, BorderLayout.SOUTH);					//
		
		
		EastPanel = new Panel();										//	BorderLayout�� ���ʿ� ��ġ
		EastPanel.setLayout(new GridLayout(3,1));						//
		btn_reset = new JButton("�ʱ�ȭ");								//
		btn_next = new JButton("�ֹ�");									//											
		btn_exit = new JButton("����");									//
																		//
		EastPanel.add(btn_next);										//
		EastPanel.add(btn_reset);										//
		EastPanel.add(btn_exit);										//
		Or_frame.add(EastPanel, BorderLayout.EAST);						//
		
		clientThread = new ClientThread();								// ClientThread �����ϱ� ���� ��ü
		clientThread.setDaemon(true);									//
		clientThread.start();											//
		
		btn_feed.addActionListener(this);							//
        btn_snack.addActionListener(this);							//
		btn_supplie.addActionListener(this);						//
		btn_send.addActionListener(this);							// ��ư�� ������ �� ����	
		btn_reset.addActionListener(this);							//
		btn_next.addActionListener(this);
		btn_exit.addActionListener(this);							//
		
        Or_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// �ݱ⸦ ������ �� ����			
    	Or_frame.setResizable(false);								// â ũ�� ���� ����
    	Or_frame.setSize(900, 800);									// â ũ�� ����
    	Or_frame.setVisible(true);									// â ���� ����
	}

	// �����ڿ��� ä�������� ���� Ŭ���̾�Ʈ Ŭ����
	class ClientThread extends Thread{										
		@Override
		public void run() {
			try {
				socket = new Socket("127.0.0.1", 8098);
				textArea.append("����� �ݰ����ϴ�.\n����� �����ϴ�.\n������ ���͵帱���?\n");
				textArea.append("�ֹ� �� ���԰� ��ȭ��ȣ�� �����ּ���.\n");
				//������ ������ ���ѽ�Ʈ�� ����(����� ���)
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				
				//������Ʈ������ ���� ���������� �۾��� ���ϰ� * �ٸ� ������Ʈ�� ���
				dis = new DataInputStream(is);
				dos = new DataOutputStream(os);
				
				// ���� �޼��� �ޱ�
				while(true) {  
					String msg = dis.readUTF();
					textArea.append("[������] : " + msg + "\n");
					textArea.setCaretPosition(textArea.getText().length());
				}
				//���� ó��
			}catch(UnknownHostException e) {
				textArea.append("���� �ּҰ� �̻��մϴ�.\n");
			}catch(IOException e) {
				textArea.append("���� ���� �˼��մϴ�. �ٽ� �ѹ� �ֹ����ּ���.");
			}
		}
	}
	
	void sendMessage() {			// ���ڸ� �Է½� ������ �޼ҵ�
		String msg = tfMsg.getText(); 		// TextField�� ���ִ� �۾��� ������
		  tfMsg.setText(""); 				// �Է��� ��ĭ����
		  // 1. TextArea(ä��â)�� ǥ��
		  textArea.append("[��] : " + msg + "\n");
		  textArea.setCaretPosition(textArea.getText().length());
		  // 2. ����(Server)���� �޼��� �����ϱ�
		  // �ƿ�ǲ ��Ʈ���� ���� ���濡�� ������ ����
		  // ��Ʈ��ũ �۾��� ������ Thread�� �ϴ� ���� ����
		  t = new Thread() {
			  @Override
			  public void run() { 
				  try { 
					  dos.writeUTF(msg);	//UTF = �����ڵ��� �Ծ�(����), �ѱ� ������ �ʰ� ����
					  dos.flush(); 			// ��� ä�� ���� close()�ϸ� �ȵ�
				  }catch(IOException e) {
					  e.printStackTrace();
				  }
			  }
		  };
		  t.start();
		}
	
	// ������ ���̽��� insert(����) �޼ҵ�
	@SuppressWarnings("finally")
	public boolean JdbcInsert(String name, String price, String su, String sum, String sumtotal) {
		// 
		final String URL = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
		final String sql = " INSERT INTO product(name, price, su, sum, sumtotal) " 
				+ " VALUES (?,?,?,?,?); ";
			
		Connection con = null;
		PreparedStatement ptmt = null;
			
		result = 0;
			
		try {
			// JDBC ����̹� �ε�
			Class.forName("com.mysql.cj.jdbc.Driver");
			// ������ ���̽� ����
			con = DriverManager.getConnection(URL, "root", "password");

			// sql���� mySQL���� ����
			ptmt = con.prepareStatement(sql);

			// ù��°, �ι�°, ����°... �����͸� �ֱ�
			ptmt.setString(1, name); 
			ptmt.setString(2, price);
			ptmt.setString(3, su);
			ptmt.setString(4, sum);
			ptmt.setString(5, sumtotal);
			result = ptmt.executeUpdate();

			// ���� Ȯ��
			if(result == 1) {
				System.out.println("������ ���� ����");
			}else {
				System.out.println("������ ���� ����");
			}
			// ���� ó��
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e){
			 e.printStackTrace();
		}finally {
			// DB close �ʼ�!
			// ������ �� ��
			try {
				if(con != null) {
					con.close();
				}
				if(ptmt != null) {
					ptmt.close();
				}
			} catch (final SQLException e) {
				e.printStackTrace();
				
			}
			return result > 0 ? true:false;
		}
	}

	public void actionPerformed(ActionEvent e) {	// e.getSource()�� Ŭ���� ��ư�� ���ٸ� �� ��ư ����
		if(e.getSource() == btn_feed) {
			CenterPanel1 = new Panel();
	        CenterPanel1.setBackground(Color.LIGHT_GRAY);
	        CenterPanel1.setLayout(null);
	        Or_frame.add(CenterPanel1, BorderLayout.CENTER);
	        
			String feed[] = {"LID","�׷������� �Ұ��","��Ż ��Ʈ���ȳ�","���Ʈ �̴Ͼ𵵾�","������Ƽ","���Ʈ ����","�Ǻ�,�Ǹ� ��Ʈ���ȳ�","�׷�������"};
			
			String image_feed[] = {
					"C://Users//user//eclipse-workspace//Trem_Project//feed_image//LID ���.png",
					"C://Users//user//eclipse-workspace//Trem_Project//feed_image//������ �׷������� ũ��ġ �Ұ��.jpg",
					"C://Users//user//eclipse-workspace//Trem_Project//feed_image//��Ż ��Ʈ���ȳ�.jpg",
					"C://Users//user//eclipse-workspace//Trem_Project//feed_image//�ξ�ĳ�� ���Ʈ �̴Ͼ𵵾�.png",
					"C://Users//user//eclipse-workspace//Trem_Project//feed_image//������Ƽ ���� ���̾�Ʈ 1kg.jpg",
					"C://Users//user//eclipse-workspace//Trem_Project//feed_image//����Ͼ�����Ŭ���� ���Ʈ ����.png",
					"C://Users//user//eclipse-workspace//Trem_Project//feed_image//�Ǻ�,�Ǹ� ��Ʈ���ȳ�.jpg",
					"C://Users//user//eclipse-workspace//Trem_Project//feed_image//�����۾˷��� �׷�������.png"};
			
			int feed_price[] = {4000,5000,4500,3500,2200,5000,3000,6800};
			
			Label txt_feed[] = new Label[feed.length];
			JButton btn_image_feed[] = new JButton[feed.length];
		    Label la_price_feed[] = new Label[feed.length];
		    Button btn_mius[] = new Button[feed.length];
		    Button btn_plus[] = new Button[feed.length];
		    TextField num_feed[] = new TextField[feed.length];
		    JButton btn_ok[] = new JButton[feed.length];
		    
		    for(int i=0; i< feed.length; i++) {
		    	CenterPanel1.add(btn_image_feed[i] = new JButton(new ImageIcon(image_feed[i])));
		    	
		    	if(i<4) {
		    		btn_image_feed[i].setBounds(25 + i * 150, 50, 100, 100);
		    	}else if(i<8) {	
		    		btn_image_feed[i].setBounds(25 + (i-4) * 150, 260, 100, 100);
		    	}else {
		    		btn_image_feed[i].setBounds(25 + (i-8) * 150, 470, 100, 100);
		    	}
		    	
		    	txt_feed[i] = new Label(feed[i]);
		    	txt_feed[i].setBounds(btn_image_feed[i].getX()-3, btn_image_feed[i].getY() - 20, 115, 20);
		    	
		    	num_feed[i] = new TextField("0");
		    	num_feed[i].setBackground(Color.white);
		    	num_feed[i].setEditable(false);
		    	num_feed[i].setBounds(btn_image_feed[i].getX() + 30, btn_image_feed[i].getY() + 130, 40, 20);
		    	
		    	btn_mius[i] = new Button("-");
		    	btn_mius[i].setBackground(Color.LIGHT_GRAY);
		    	btn_mius[i].setBounds(btn_image_feed[i].getX(), num_feed[i].getY(), 20, 20);
		    	
		    	btn_plus[i] = new Button("+");
		    	btn_plus[i].setBackground(Color.LIGHT_GRAY);
		    	btn_plus[i].setBounds(btn_image_feed[i].getX() + 80, num_feed[i].getY(), 20, 20);
		    	
		    	la_price_feed[i] = new Label(feed_price[i] + "��");
		    	la_price_feed[i].setBounds(btn_image_feed[i].getX() + 30, num_feed[i].getY()- 25, 100, 20);
		    	
		    	btn_ok[i] = new JButton("��ٱ���");													
                btn_ok[i].setBounds(btn_image_feed[i].getX(), num_feed[i].getY() + 30, 100, 20);			
                btn_ok[i].setEnabled(false);
                
                int j = i;
         
                btn_mius[i].addActionListener(new ActionListener() {			// ���� ���� �޼ҵ�
         
                	@Override
                	public void actionPerformed(ActionEvent e) {
                		if (count > 0) {								
                			count -= 1;										
                            	num_feed[j].setText(count + "");						
                                btn_ok[j].setEnabled(true);							
                		} else {											
                			btn_mius[j].setEnabled(false);						
                        }
                	}
                });
                    
                btn_plus[i].addActionListener(new ActionListener() {			// ���� ���� �޼ҵ�		
         
                	@Override
                    	public void actionPerformed(ActionEvent e) {
                			count += 1;											
                            num_feed[j].setText(count + "");							
                            btn_ok[j].setEnabled(true);							
                            if (count > 0) {									
                            	btn_mius[j].setEnabled(true);					
                            }
                        }
                });
                    
                btn_ok[i].addActionListener(new ActionListener() {				// ���̺� �����ϰ� ���ִ� �޼ҵ�
         
                	@Override
                    	public void actionPerformed(ActionEvent e) {
                   		   num_feed[j].setText("0");									
                           total += feed_price[j] * count;							
                           
                           basket[0] = feed[j];							
                           basket[1] = feed_price[j]+"��";
                           basket[2] = "" + count;
                           basket[3] = feed_price[j] * count+"��";
                           basket[4] = total + "��";
                           model.addRow(basket);
                           
                           String name = basket[0];
                           String price = basket[1];
                           String su = basket[2];
                           String sum = basket[3];
                           String sumtotal = basket[4];
                           JdbcInsert(name, price, su, sum, sumtotal);
                           
                           count=0;
                           btn_ok[j].setEnabled(false);
                	}
                });
                
                CenterPanel1.add(txt_feed[i]);
                CenterPanel1.add(num_feed[i]);	
                CenterPanel1.add(btn_mius[i]);
                CenterPanel1.add(btn_plus[i]);
                CenterPanel1.add(la_price_feed[i]);
                CenterPanel1.add(btn_ok[i]);	
                
                CenterPanel2.setVisible(false);
                CenterPanel3.setVisible(false);

                Or_frame.setVisible(true);
		    }
	  }else if(e.getSource() == btn_snack) {
		  	CenterPanel2 = new Panel();
	        CenterPanel2.setBackground(Color.LIGHT_GRAY);
	        CenterPanel2.setLayout(null);
	        Or_frame.add(CenterPanel2, BorderLayout.CENTER);	
		  
	        String snack[] = {"ġ�����Ų�","�ǰ�����","�߰�����","����","�����","���簣��","����","��Ű������"};
			
			String image_snack[] = {
					"C:\\Users\\user\\eclipse-workspace\\Trem_Project\\snack_image\\������ ġ������ ���Ĳ�.png",
					"C:\\Users\\user\\eclipse-workspace\\Trem_Project\\snack_image\\�ǰ�����.jpg",
					"C:\\Users\\user\\eclipse-workspace\\Trem_Project\\snack_image\\�߰����찣��.jpg",
					"C:\\Users\\user\\eclipse-workspace\\Trem_Project\\snack_image\\����.png",
					"C:\\Users\\user\\eclipse-workspace\\Trem_Project\\snack_image\\�����.png",
					"C:\\Users\\user\\eclipse-workspace\\Trem_Project\\snack_image\\���簣��.jpg",
					"C:\\Users\\user\\eclipse-workspace\\Trem_Project\\snack_image\\����.jpg",
					"C:\\Users\\user\\eclipse-workspace\\Trem_Project\\snack_image\\��Ű������.png"};
			
			int snack_price[] = {6500,6000,7500,6500,10000,6500,8000,13000};
			
			Label txt_snack[] = new Label[snack.length];
			JButton btn_image_snack[] = new JButton[snack.length];
		    Label la_price_snack[] = new Label[snack.length];
		    Button btn_mius[] = new Button[snack.length];
		    Button btn_plus[] = new Button[snack.length];
		    TextField num_snack[] = new TextField[snack.length];
		    JButton btn_ok[] = new JButton[snack.length];
		    
		    for(int i=0; i< snack.length; i++) {
		    	CenterPanel2.add(btn_image_snack[i] = new JButton(new ImageIcon(image_snack[i])));
		    	
		    	if(i<4) {
		    		btn_image_snack[i].setBounds(25 + i * 150, 50, 100, 100);
		    	}else if(i<8) {
		    		btn_image_snack[i].setBounds(25 + (i-4) * 150, 260, 100, 100);
		    	}else {
		    		btn_image_snack[i].setBounds(25 + (i-8) * 150, 470, 100, 100);
		    	}
		    	
		    	txt_snack[i] = new Label(snack[i]);
		    	txt_snack[i].setBounds(btn_image_snack[i].getX()-3, btn_image_snack[i].getY() - 20, 115, 20);
		    	
		    	num_snack[i] = new TextField("0");
		    	num_snack[i].setBackground(Color.white);
		    	num_snack[i].setEditable(false);
		    	num_snack[i].setBounds(btn_image_snack[i].getX() + 30, btn_image_snack[i].getY() + 130, 40, 20);
		    	
		    	btn_mius[i] = new Button("-");
		    	btn_mius[i].setBackground(Color.LIGHT_GRAY);
		    	btn_mius[i].setBounds(btn_image_snack[i].getX(), num_snack[i].getY(), 20, 20);
		    	
		    	btn_plus[i] = new Button("+");
		    	btn_plus[i].setBackground(Color.LIGHT_GRAY);
		    	btn_plus[i].setBounds(btn_image_snack[i].getX() + 80, num_snack[i].getY(), 20, 20);
		    	
		    	la_price_snack[i] = new Label(snack_price[i] + "��");
		    	la_price_snack[i].setBounds(btn_image_snack[i].getX() + 30,  num_snack[i].getY()- 25, 100, 20);
		    	
		    	btn_ok[i] = new JButton("��ٱ���");													
		    	btn_ok[i].setBounds(btn_image_snack[i].getX(),  num_snack[i].getY() + 30, 100, 20);			
		    	btn_ok[i].setEnabled(false);
		    	
		    	int j = i;
		    	btn_mius[i].addActionListener(new ActionListener() {
		            
                	@Override
                	public void actionPerformed(ActionEvent e) {
                		if (count > 0) {								
                			count -= 1;										
                			num_snack[j].setText(count + "");						
                			btn_ok[j].setEnabled(true);							
                		} else {											
                			btn_mius[j].setEnabled(false);						
                		}
                	}
                });
                    
		    	btn_plus[i].addActionListener(new ActionListener() {			
         
                	@Override
                	public void actionPerformed(ActionEvent e) {
                	count += 1;											
                	num_snack[j].setText(count + "");							
                	btn_ok[j].setEnabled(true);							
                	if (count > 0) {									
                		btn_mius[j].setEnabled(true);					
                		}
                	}
                });
                    
		    	btn_ok[i].addActionListener(new ActionListener() {				
         
		    		@Override
		    		public void actionPerformed(ActionEvent e) {
		    			num_snack[j].setText("0");									
                        total += snack_price[j] * count;							
                           
                        basket[0] = snack[j];							
                        basket[1] = snack_price[j]+"��";
                        basket[2] = "" + count;
                        basket[3] = snack_price[j] * count+"��";
                        basket[4] = total + "��";
                        model.addRow(basket);
                        
                        String name = basket[0];
                        String price = basket[1];
                        String su = basket[2];
                        String sum = basket[3];
                        String sumtotal = basket[4];
                        JdbcInsert(name, price, su, sum, sumtotal);   
                        
                        count=0;
                        btn_ok[j].setEnabled(false);
		    		}
		    	});
              
		    	CenterPanel2.add(btn_image_snack[i]);
		    	CenterPanel2.add(txt_snack[i]);
		    	CenterPanel2.add(num_snack[i]);	
		    	CenterPanel2.add(btn_mius[i]);
		    	CenterPanel2.add(btn_plus[i]);
		    	CenterPanel2.add(la_price_snack[i]);
		    	CenterPanel2.add(btn_ok[i]);
		    	
		    	CenterPanel1.setVisible(false);
		    	CenterPanel3.setVisible(false);
		    	
		    	Or_frame.setVisible(true);
		    }
	  }else if(e.getSource() == btn_supplie){
		  CenterPanel3 = new Panel();
		  CenterPanel3.setBackground(Color.LIGHT_GRAY);
		  CenterPanel3.setLayout(null);
		  Or_frame.add(CenterPanel3, BorderLayout.CENTER);	
		  
		  String supplies[] = {"����","��ī��","����","�躯����","�躯�е�","����","��Ÿ����","��"};
		  	
		  String image_supplies[] = {
				  "C:\\Users\\user\\eclipse-workspace\\Trem_Project\\supplie_image\\����.png",
				  "C:\\Users\\user\\eclipse-workspace\\Trem_Project\\supplie_image\\��ī��.jpg",
				  "C:\\Users\\user\\eclipse-workspace\\Trem_Project\\supplie_image\\����.jpg",
				  "C:\\Users\\user\\eclipse-workspace\\Trem_Project\\supplie_image\\�躯����.png",
				  "C:\\Users\\user\\eclipse-workspace\\Trem_Project\\supplie_image\\�躯�е�.png",
				  "C:\\Users\\user\\eclipse-workspace\\Trem_Project\\supplie_image\\����.png",
				  "C:\\Users\\user\\eclipse-workspace\\Trem_Project\\supplie_image\\��Ÿ����.png",
				  "C:\\Users\\user\\eclipse-workspace\\Trem_Project\\supplie_image\\��.png"};
				
		  int price_supplies[] = {18000,7500,8000,9000,10500,12000,6000,45000};
			
		  Label txt_supplies[] = new Label[supplies.length];
		  JButton btn_image_supplies[] = new JButton[supplies.length];
		  Label la_price_supplies[] = new Label[supplies.length];
		  Button btn_mius[] = new Button[supplies.length];
		  Button btn_plus[] = new Button[supplies.length];
		  TextField num_supplies[] = new TextField[supplies.length];
		  JButton btn_ok[] = new JButton[supplies.length];
		    
		  for(int i=0; i< supplies.length; i++) {
			  CenterPanel2.add(btn_image_supplies[i] = new JButton(new ImageIcon(image_supplies[i])));
		    	
			  if(i<4) {
				  btn_image_supplies[i].setBounds(25 + i * 150, 50, 100, 100);
			  }else if(i<8) {
				  btn_image_supplies[i].setBounds(25 + (i-4) * 150, 260, 100, 100);
			  }else {
				  btn_image_supplies[i].setBounds(25 + (i-8) * 150, 470, 100, 100);
			  }
		    	
			  txt_supplies[i] = new Label(supplies[i]);
			  txt_supplies[i].setBounds(btn_image_supplies[i].getX()-3, btn_image_supplies[i].getY() - 20, 115, 20);
		    	
			  num_supplies[i] = new TextField("0");
			  num_supplies[i].setBackground(Color.white);
			  num_supplies[i].setEditable(false);
			  num_supplies[i].setBounds(btn_image_supplies[i].getX() + 30, btn_image_supplies[i].getY() + 130, 40, 20);
		    	
			  btn_mius[i] = new Button("-");
			  btn_mius[i].setBackground(Color.LIGHT_GRAY);
			  btn_mius[i].setBounds(btn_image_supplies[i].getX(), num_supplies[i].getY(), 20, 20);
		    	
			  btn_plus[i] = new Button("+");
			  btn_plus[i].setBackground(Color.LIGHT_GRAY);
			  btn_plus[i].setBounds(btn_image_supplies[i].getX() + 80, num_supplies[i].getY(), 20, 20);
		    	
			  la_price_supplies[i] = new Label(price_supplies[i] + "��");
			  la_price_supplies[i].setBounds(btn_image_supplies[i].getX() + 30,  num_supplies[i].getY()- 25, 100, 20);
		    	
			  btn_ok[i] = new JButton("��ٱ���");														
			  btn_ok[i].setBounds(btn_image_supplies[i].getX(),  num_supplies[i].getY() + 30, 100, 20);			
			  btn_ok[i].setEnabled(false);
		    	
			  int j = i;
			  btn_mius[i].addActionListener(new ActionListener() {			
		            
				  @Override
				  public void actionPerformed(ActionEvent e) {
					  if (count > 0) {								
						  count -= 1;										
						  	num_supplies[j].setText(count + "");						
						  	btn_ok[j].setEnabled(true);							
					  } else {											
						  btn_mius[j].setEnabled(false);						
					  }
				  }
			  });
                    
			  btn_plus[i].addActionListener(new ActionListener() {			
         
				  @Override
				  public void actionPerformed(ActionEvent e) {
					  count += 1;											
					  num_supplies[j].setText(count + "");							
					  btn_ok[j].setEnabled(true);							
					  if (count > 0) {									
						  btn_mius[j].setEnabled(true);					
					  }
				  }
			  });
                    
			  btn_ok[i].addActionListener(new ActionListener() {				
         
				  @Override
				  public void actionPerformed(ActionEvent e) {
					  num_supplies[j].setText("0");									
					  total += price_supplies[j] * count;							
                        
					  basket[0] = supplies[j];							
					  basket[1] = price_supplies[j]+"��";
					  basket[2] = "" + count;
					  basket[3] = price_supplies[j] * count+"��";
					  basket[4] = total + "��";
					  model.addRow(basket);
                      
					  String name = basket[0];
                      String price = basket[1];
                      String su = basket[2];
                      String sum = basket[3];
                      String sumtotal = basket[4];
                      JdbcInsert(name, price, su, sum, sumtotal);   
                      
					  count = 0;
					  btn_ok[j].setEnabled(false);
				  }
			  });
              
			  CenterPanel3.add(btn_image_supplies[i]);
			  CenterPanel3.add(txt_supplies[i]);
			  CenterPanel3.add(num_supplies[i]);	
			  CenterPanel3.add(btn_mius[i]);
			  CenterPanel3.add(btn_plus[i]);
			  CenterPanel3.add(la_price_supplies[i]);
			  CenterPanel3.add(btn_ok[i]);
		    	
			  CenterPanel1.setVisible(false);
			  CenterPanel2.setVisible(false);
			  		    	
			  Or_frame.setVisible(true);
		  }   		
	  }else if (e.getSource() == btn_send) {
		  sendMessage();						// �޼��� ������
	  }else if (e.getSource() == btn_next) {
		  new ManageTable();					// �����ڿ��� �ֹ� �� ���� �Ѿ.
	  }else if (e.getSource() == btn_reset) {
		  model.setNumRows(0);
	  }else if (e.getSource() == btn_exit) {
		  System.exit(0);						// ���� 
	  }
	}
}		