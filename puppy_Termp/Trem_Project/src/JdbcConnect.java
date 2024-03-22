import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConnect {		// �����ͺ��̽� ����
	public JdbcConnect() {
		try {
			// JDBC ����̹��� �̸� ����
			String driverName = "com.mysql.cj.jdbc.Driver";
			
			// JDBC ����̹� �ε�
			Class.forName(driverName);		
			String URL = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
			
			// �����ͺ��̽��� ����
			Connection con = DriverManager.getConnection(URL, "root", "password");
			System.out.println("MySQL �����ͺ��̽��� ���������� �����߽��ϴ�.");
			
			// �����ͺ��̽� ����
			con.close();
			
		}catch(Exception e) {
			System.out.println("MySQL ������ ���̽� ���ӿ� ������ �ֽ��ϴ�.");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
