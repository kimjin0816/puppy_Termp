import java.awt.BorderLayout;					// (동,서,남,북,센터)위치를 지정할 수 있는 레이아웃
import java.awt.Button;							// 버튼
import java.awt.Color;							// 색깔
import java.awt.Dimension;						// 컴포넌트의 폭과 높이를 정수 정밀도로 캡슐화한다.
import java.awt.FlowLayout;						// 가운데를 중심으로 왼쪽으로 정렬하는 레이아웃 
import java.awt.Font;							// 글씨체
import java.awt.GridLayout;						// 행과 열을 선언하여 배치하는 레이아웃
import java.awt.Label;							// 읽기 전용 텍스트를 표시하는데 사용한다.
import java.awt.Panel;							// 응용 프로그램이 다른 구성요소를 연결할 수 있는 공간을 제공
import java.awt.TextField;						// 한 줄 텍스트를 입력하고 편집할 수 있도록 하는 텍스트 구성 요소
import java.awt.event.ActionEvent;				// 버튼이나 메뉴 항목을 클릭할 때마다 알림을 받는다.
import java.awt.event.ActionListener;			// actionPerformed()라는 하나의 메서드만 있다.
import java.io.DataInputStream;					// 입력 스트림에서 기본 데이터를 읽을 수 있습니다.
import java.io.DataOutputStream;				// 데이터 유형을 출력 스트림
import java.io.IOException;						// 이 스캐너의 읽기 가능 항목에서 마지막으로 발생한 IOException을 가져오는 데 사용되는 Java 스캐너 클래스 의 메서드
import java.io.InputStream;						// 바이트 단위로 데이터를 읽는다. 외부로부터 읽어 들이는 기능관련 클래스들
import java.io.OutputStream;					// 외부로 데이터를 전송한다. 외부로 데이터를 전송하는 기능 관련 클래스들
import java.net.Socket;							// 클라이언트 측의 TCP 기능을 수행하기 위한 자바의 기본 클래스
import java.net.UnknownHostException;			// DNS 확인 오류가 있음을 나타낸다.
import java.sql.Connection;						// 데이터베이스를 연결해주는 객체
import java.sql.DriverManager;					// 연결을 시도할 JDBC 드라이버를 지정
import java.sql.PreparedStatement;				// SQL구문을 실행시키는 기능을 갖는 객체
import java.sql.SQLException;					// 데이터베이스 액세스 오류 또는 기타 오류에 대한 정보를 제공하는 예외


import javax.swing.ImageIcon;					// 이미지 넣는 Icon
import javax.swing.JButton;						// 플랫폼 독립적 구현이 있는 레이블이 지정된 단추
import javax.swing.JFrame;						// 윈도우창을 띄어보는 것
import javax.swing.JLabel;						// 글자 삽입 라벨 
import javax.swing.JPanel;						// 경량 구성 요소를 위한 범용 컨테이너를 제공
import javax.swing.JScrollPane;					// table의 스크롤 제공
import javax.swing.JTable;						// 행과 열로 이루어진 테이블 제공
import javax.swing.JTextArea;					// 한 줄 입력이 가능한 객체
import javax.swing.JTextField;					// 여러 줄 입력이 가능한 객체
import javax.swing.table.DefaultTableModel;		// 테이블에 값을 대신넣어줄 객체

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
	
	// 강아지 용품 프레임 생성
	public Order() {										
		Or_frame = new JFrame("강아지 용품");								// 전체적인 창										
										
        NorthPanel = new Panel();										// BorderLayout의 북쪽에 위치
        NorthPanel.setBackground(Color.orange);							// 
        NorthPanel.setLayout(new FlowLayout(FlowLayout.LEFT));			//
        Or_frame.add(NorthPanel, BorderLayout.NORTH);					//
        																//
        title = new JLabel("보리사랑");									//
        title.setFont(new Font("Serif", Font.BOLD, 11));				//
        title.setForeground(Color.BLUE);								//
        title.setFont(title.getFont().deriveFont(15.0f));				//
        NorthPanel.add(title);											//
        																//
        btn_feed = new JButton("사료");									//
        btn_feed.setPreferredSize(new Dimension(100,70));				//
        btn_feed.setFont(btn_feed.getFont().deriveFont(15.0f));			//
        btn_feed.setBackground(Color.pink);								//
        NorthPanel.add(btn_feed);										//
        																//
        btn_snack = new JButton("간식");									//
        btn_snack.setPreferredSize(new Dimension(100,70));				//
        btn_snack.setBackground(Color.pink);							//
        btn_snack.setFont(btn_snack.getFont().deriveFont(15.0f));		//
        NorthPanel.add(btn_snack);										//
        																//
        btn_supplie = new JButton("용품");								//
        btn_supplie.setPreferredSize(new Dimension(100,70));			//
        btn_supplie.setBackground(Color.pink);							//
        btn_supplie.setFont(btn_supplie.getFont().deriveFont(15.0f));	//
        NorthPanel.add(btn_supplie);									//
        					
        
		SouthPanel1 = new Panel();										// BorderLayout의 남쪽에 위치
		SouthPanel1.setLayout(new GridLayout(1,2));						//
																		//
		SouthPanel2 = new Panel();										//
		String dataHead[] = {"상품명","단가","수량","합계","총 금액"};			//
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
		btn_send = new JButton("보내기");									//
																		//
		textArea.setEditable(false);									//
		SouthPanel3.add(scrollPane1, BorderLayout.CENTER);				//
		sp3_South.add(tfMsg, BorderLayout.CENTER);						//
		sp3_South.add(btn_send, BorderLayout.EAST);						//
		SouthPanel3.add(sp3_South, BorderLayout.SOUTH);					//
		SouthPanel1.add(SouthPanel3);									//
		Or_frame.add(SouthPanel1, BorderLayout.SOUTH);					//
		
		
		EastPanel = new Panel();										//	BorderLayout의 동쪽에 위치
		EastPanel.setLayout(new GridLayout(3,1));						//
		btn_reset = new JButton("초기화");								//
		btn_next = new JButton("주문");									//											
		btn_exit = new JButton("종료");									//
																		//
		EastPanel.add(btn_next);										//
		EastPanel.add(btn_reset);										//
		EastPanel.add(btn_exit);										//
		Or_frame.add(EastPanel, BorderLayout.EAST);						//
		
		clientThread = new ClientThread();								// ClientThread 실행하기 위한 객체
		clientThread.setDaemon(true);									//
		clientThread.start();											//
		
		btn_feed.addActionListener(this);							//
        btn_snack.addActionListener(this);							//
		btn_supplie.addActionListener(this);						//
		btn_send.addActionListener(this);							// 버튼을 눌렀을 시 동작	
		btn_reset.addActionListener(this);							//
		btn_next.addActionListener(this);
		btn_exit.addActionListener(this);							//
		
        Or_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// 닫기를 눌렀을 시 종료			
    	Or_frame.setResizable(false);								// 창 크지 조절 여부
    	Or_frame.setSize(900, 800);									// 창 크기 설정
    	Or_frame.setVisible(true);									// 창 보임 유무
	}

	// 관리자와의 채팅접속을 위한 클라이언트 클래스
	class ClientThread extends Thread{										
		@Override
		public void run() {
			try {
				socket = new Socket("127.0.0.1", 8098);
				textArea.append("대단히 반갑습니다.\n상당히 고맙습니다.\n무엇을 도와드릴까요?\n");
				textArea.append("주문 전 성함과 전화번호를 적어주세요.\n");
				//데이터 전송을 위한스트림 생성(입출력 모두)
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				
				//보조스트림으로 만들어서 데이터전송 작업을 편하게 * 다른 보조스트림 사용
				dis = new DataInputStream(is);
				dos = new DataOutputStream(os);
				
				// 상대방 메세지 받기
				while(true) {  
					String msg = dis.readUTF();
					textArea.append("[관리자] : " + msg + "\n");
					textArea.setCaretPosition(textArea.getText().length());
				}
				//예외 처리
			}catch(UnknownHostException e) {
				textArea.append("서버 주소가 이상합니다.\n");
			}catch(IOException e) {
				textArea.append("고객님 정말 죄송합니다. 다시 한번 주문해주세요.");
			}
		}
	}
	
	void sendMessage() {			// 문자를 입력시 보내는 메소드
		String msg = tfMsg.getText(); 		// TextField에 써있는 글씨를 얻어오기
		  tfMsg.setText(""); 				// 입력후 빈칸으로
		  // 1. TextArea(채팅창)에 표시
		  textArea.append("[고객] : " + msg + "\n");
		  textArea.setCaretPosition(textArea.getText().length());
		  // 2. 상대방(Server)에게 메세지 전송하기
		  // 아웃풋 스트림을 통해 상대방에게 데이터 전송
		  // 네트워크 작업은 별도의 Thread가 하는 것이 좋음
		  t = new Thread() {
			  @Override
			  public void run() { 
				  try { 
					  dos.writeUTF(msg);	//UTF = 유니코드의 규약(포맷), 한글 깨지지 않게 해줌
					  dos.flush(); 			// 계속 채팅 위해 close()하면 안됨
				  }catch(IOException e) {
					  e.printStackTrace();
				  }
			  }
		  };
		  t.start();
		}
	
	// 데이터 베이스의 insert(삽입) 메소드
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
			// JDBC 드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 데이터 베이스 연결
			con = DriverManager.getConnection(URL, "root", "password");

			// sql구문 mySQL에서 실행
			ptmt = con.prepareStatement(sql);

			// 첫번째, 두번째, 세번째... 데이터를 넣기
			ptmt.setString(1, name); 
			ptmt.setString(2, price);
			ptmt.setString(3, su);
			ptmt.setString(4, sum);
			ptmt.setString(5, sumtotal);
			result = ptmt.executeUpdate();

			// 저장 확인
			if(result == 1) {
				System.out.println("데이터 저장 성공");
			}else {
				System.out.println("데이터 저장 실패");
			}
			// 예외 처리
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e){
			 e.printStackTrace();
		}finally {
			// DB close 필수!
			// 접속이 된 것
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

	public void actionPerformed(ActionEvent e) {	// e.getSource()가 클릭한 버튼과 같다면 그 버튼 실행
		if(e.getSource() == btn_feed) {
			CenterPanel1 = new Panel();
	        CenterPanel1.setBackground(Color.LIGHT_GRAY);
	        CenterPanel1.setLayout(null);
	        Or_frame.add(CenterPanel1, BorderLayout.CENTER);
	        
			String feed[] = {"LID","그레인프린 소고기","덴탈 뉴트리냠냠","어덜트 미니언도어","본아페티","어덜트 쇠고기","피부,피모 뉴트리냠냠","그레인프리"};
			
			String image_feed[] = {
					"C://Users//user//eclipse-workspace//Trem_Project//feed_image//LID 사료.png",
					"C://Users//user//eclipse-workspace//Trem_Project//feed_image//더리얼 그레인프리 크런치 소고기.jpg",
					"C://Users//user//eclipse-workspace//Trem_Project//feed_image//덴탈 뉴트리냠냠.jpg",
					"C://Users//user//eclipse-workspace//Trem_Project//feed_image//로얄캐닌 어덜트 미니언도어.png",
					"C://Users//user//eclipse-workspace//Trem_Project//feed_image//본아페티 관절 다이어트 1kg.jpg",
					"C://Users//user//eclipse-workspace//Trem_Project//feed_image//컴페니언펫츠클래식 어덜트 쇠고기.png",
					"C://Users//user//eclipse-workspace//Trem_Project//feed_image//피부,피모 뉴트리냠냠.jpg",
					"C://Users//user//eclipse-workspace//Trem_Project//feed_image//하이퍼알러지 그레인프리.png"};
			
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
		    	
		    	la_price_feed[i] = new Label(feed_price[i] + "원");
		    	la_price_feed[i].setBounds(btn_image_feed[i].getX() + 30, num_feed[i].getY()- 25, 100, 20);
		    	
		    	btn_ok[i] = new JButton("장바구니");													
                btn_ok[i].setBounds(btn_image_feed[i].getX(), num_feed[i].getY() + 30, 100, 20);			
                btn_ok[i].setEnabled(false);
                
                int j = i;
         
                btn_mius[i].addActionListener(new ActionListener() {			// 수량 감소 메소드
         
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
                    
                btn_plus[i].addActionListener(new ActionListener() {			// 수량 증가 메소드		
         
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
                    
                btn_ok[i].addActionListener(new ActionListener() {				// 테이블에 저장하게 해주는 메소드
         
                	@Override
                    	public void actionPerformed(ActionEvent e) {
                   		   num_feed[j].setText("0");									
                           total += feed_price[j] * count;							
                           
                           basket[0] = feed[j];							
                           basket[1] = feed_price[j]+"원";
                           basket[2] = "" + count;
                           basket[3] = feed_price[j] * count+"원";
                           basket[4] = total + "원";
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
		  
	        String snack[] = {"치석제거껌","건강간식","닭가슴살","시저","연어간식","영양간식","육포","패키지간식"};
			
			String image_snack[] = {
					"C:\\Users\\user\\eclipse-workspace\\Trem_Project\\snack_image\\강아지 치석제거 간식껌.png",
					"C:\\Users\\user\\eclipse-workspace\\Trem_Project\\snack_image\\건강간식.jpg",
					"C:\\Users\\user\\eclipse-workspace\\Trem_Project\\snack_image\\닭가슴살간식.jpg",
					"C:\\Users\\user\\eclipse-workspace\\Trem_Project\\snack_image\\시저.png",
					"C:\\Users\\user\\eclipse-workspace\\Trem_Project\\snack_image\\연어간식.png",
					"C:\\Users\\user\\eclipse-workspace\\Trem_Project\\snack_image\\영양간식.jpg",
					"C:\\Users\\user\\eclipse-workspace\\Trem_Project\\snack_image\\육포.jpg",
					"C:\\Users\\user\\eclipse-workspace\\Trem_Project\\snack_image\\패키지간식.png"};
			
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
		    	
		    	la_price_snack[i] = new Label(snack_price[i] + "원");
		    	la_price_snack[i].setBounds(btn_image_snack[i].getX() + 30,  num_snack[i].getY()- 25, 100, 20);
		    	
		    	btn_ok[i] = new JButton("장바구니");													
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
                        basket[1] = snack_price[j]+"원";
                        basket[2] = "" + count;
                        basket[3] = snack_price[j] * count+"원";
                        basket[4] = total + "원";
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
		  
		  String supplies[] = {"가방","넥카라","목줄","배변봉투","배변패드","수건","실타래공","집"};
		  	
		  String image_supplies[] = {
				  "C:\\Users\\user\\eclipse-workspace\\Trem_Project\\supplie_image\\가방.png",
				  "C:\\Users\\user\\eclipse-workspace\\Trem_Project\\supplie_image\\넥카라.jpg",
				  "C:\\Users\\user\\eclipse-workspace\\Trem_Project\\supplie_image\\목줄.jpg",
				  "C:\\Users\\user\\eclipse-workspace\\Trem_Project\\supplie_image\\배변봉투.png",
				  "C:\\Users\\user\\eclipse-workspace\\Trem_Project\\supplie_image\\배변패드.png",
				  "C:\\Users\\user\\eclipse-workspace\\Trem_Project\\supplie_image\\수건.png",
				  "C:\\Users\\user\\eclipse-workspace\\Trem_Project\\supplie_image\\실타래공.png",
				  "C:\\Users\\user\\eclipse-workspace\\Trem_Project\\supplie_image\\집.png"};
				
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
		    	
			  la_price_supplies[i] = new Label(price_supplies[i] + "원");
			  la_price_supplies[i].setBounds(btn_image_supplies[i].getX() + 30,  num_supplies[i].getY()- 25, 100, 20);
		    	
			  btn_ok[i] = new JButton("장바구니");														
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
					  basket[1] = price_supplies[j]+"원";
					  basket[2] = "" + count;
					  basket[3] = price_supplies[j] * count+"원";
					  basket[4] = total + "원";
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
		  sendMessage();						// 메세지 보내기
	  }else if (e.getSource() == btn_next) {
		  new ManageTable();					// 관리자에게 주문 한 내역 넘어감.
	  }else if (e.getSource() == btn_reset) {
		  model.setNumRows(0);
	  }else if (e.getSource() == btn_exit) {
		  System.exit(0);						// 종료 
	  }
	}
}		