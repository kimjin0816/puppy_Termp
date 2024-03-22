import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ManageTable extends JFrame {

	JPanel Panel, tablePanel, delPanel;
	JTable table;
	DefaultTableModel model;
	JScrollPane tablescroll;
	JTextField delField;
	JButton delBtn, delRegistBtn;
	int rowCount;
	String[] basket = new String[6];

	public ManageTable() {
		super("매출 및 주문내역");								// 관리자에게 보여지는 주문내역 및 매출 프레임
		setSize(500, 400);
		Panel = new JPanel();
		Panel.setLayout(new BorderLayout());
		
		tablePanel = new JPanel();
		String dataHead[] = {"순번","상품명","단가","수량","합계","총 금액"}; 	//
		String data[][] = new String[rowCount][6];						//
		model= new DefaultTableModel(data, dataHead);					//
		table = new JTable(model);										// 테이블 생성
		tablescroll = new JScrollPane(table);							//
		tablescroll.setPreferredSize(new Dimension(300,200));			//
		Panel.add(tablescroll, BorderLayout.CENTER);					//
		
		delPanel = new JPanel();										
		delPanel.setLayout(new GridLayout(1,3));						
		delField = new JTextField();									
																		
		delField.setEditable(true);											
		delBtn = new JButton("삭제");
		delPanel.add(delField);
		delPanel.add(delBtn);
		delRegistBtn = new JButton("새로고침");
	    delPanel.add(delRegistBtn);
		Panel.add(delPanel, BorderLayout.SOUTH);
		add(Panel);
		
		// 디비 연동된 테이블의 값을 삭제 할 수 있는 버튼 메소드
		delBtn.addActionListener(new ActionListener(){ 
			public void actionPerformed(ActionEvent e){
				int sno = Integer.parseInt(delField.getText());
				System.out.println(sno);
				JdbcDelete(sno);
			}
		});
		
		// 실시간을 삭제한 테이블의 값을 보여주는 새로고침 버튼 메소드
		delRegistBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new ManageTable();
			}
		});
		
		
		JdbcSelect();	
		setVisible(true);
	}
	
	// 현재까지 DB에 연동된 데이터값들을 조회 메소드
	public void JdbcSelect() {				
		final String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
		final String sql = "select * from product;";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, "root", "password");
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				basket[0] = rs.getString(1);		// no
				basket[1] = rs.getString(2);		// name
				basket[2] = rs.getString(3);		// price
				basket[3] = rs.getString(4);		// su
				basket[4] = rs.getString(5);		// sum
				basket[5] = rs.getString(6);		// sumtotal
				model.addRow(basket);
			}
			
		}catch(Exception e) {
			System.out.println(e);
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
				if(con != null) {
					con.close();
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	//  현재까지 DB에 연동된 데이터값들을 삭제하는 메소드
	public void JdbcDelete(int sno) {
		String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = " delete from product where no = ?" ;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, "root", "password");
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, sno);
			
			rowCount = pstmt.executeUpdate();
			if(rowCount == 1) {
				System.out.println("삭제 성공");
			}else {
				System.out.println("삭제 실패");
			}
			
		}catch(Exception e) {
			System.out.println(e);
		}finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(con != null) {
					con.close();
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}	
}

