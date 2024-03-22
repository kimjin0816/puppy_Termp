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
		super("���� �� �ֹ�����");								// �����ڿ��� �������� �ֹ����� �� ���� ������
		setSize(500, 400);
		Panel = new JPanel();
		Panel.setLayout(new BorderLayout());
		
		tablePanel = new JPanel();
		String dataHead[] = {"����","��ǰ��","�ܰ�","����","�հ�","�� �ݾ�"}; 	//
		String data[][] = new String[rowCount][6];						//
		model= new DefaultTableModel(data, dataHead);					//
		table = new JTable(model);										// ���̺� ����
		tablescroll = new JScrollPane(table);							//
		tablescroll.setPreferredSize(new Dimension(300,200));			//
		Panel.add(tablescroll, BorderLayout.CENTER);					//
		
		delPanel = new JPanel();										
		delPanel.setLayout(new GridLayout(1,3));						
		delField = new JTextField();									
																		
		delField.setEditable(true);											
		delBtn = new JButton("����");
		delPanel.add(delField);
		delPanel.add(delBtn);
		delRegistBtn = new JButton("���ΰ�ħ");
	    delPanel.add(delRegistBtn);
		Panel.add(delPanel, BorderLayout.SOUTH);
		add(Panel);
		
		// ��� ������ ���̺��� ���� ���� �� �� �ִ� ��ư �޼ҵ�
		delBtn.addActionListener(new ActionListener(){ 
			public void actionPerformed(ActionEvent e){
				int sno = Integer.parseInt(delField.getText());
				System.out.println(sno);
				JdbcDelete(sno);
			}
		});
		
		// �ǽð��� ������ ���̺��� ���� �����ִ� ���ΰ�ħ ��ư �޼ҵ�
		delRegistBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new ManageTable();
			}
		});
		
		
		JdbcSelect();	
		setVisible(true);
	}
	
	// ������� DB�� ������ �����Ͱ����� ��ȸ �޼ҵ�
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
	
	//  ������� DB�� ������ �����Ͱ����� �����ϴ� �޼ҵ�
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
				System.out.println("���� ����");
			}else {
				System.out.println("���� ����");
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

