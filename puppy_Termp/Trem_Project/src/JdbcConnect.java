import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConnect {		// 데이터베이스 연동
	public JdbcConnect() {
		try {
			// JDBC 드라이버의 이름 지정
			String driverName = "com.mysql.cj.jdbc.Driver";
			
			// JDBC 드라이버 로드
			Class.forName(driverName);		
			String URL = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
			
			// 데이터베이스에 연결
			Connection con = DriverManager.getConnection(URL, "root", "password");
			System.out.println("MySQL 데이터베이스에 성공적으로 접속했습니다.");
			
			// 데이터베이스 종료
			con.close();
			
		}catch(Exception e) {
			System.out.println("MySQL 데이터 베이스 접속에 문제가 있습니다.");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
