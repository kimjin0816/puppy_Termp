import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// 연동된 mySQL안에 테이블을 생성하는 SQL구문
public class TableCreating {
    public TableCreating(){
        Connection con = null;
        Statement stmt = null;
 
        final String URL = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
        final String ID = "root";
        final String PW = "password";
 
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
 
            con = DriverManager.getConnection(URL, ID, PW);
 
            //DB와 연결된 con 객체로부터 Statement 객체 획득.
            stmt = con.createStatement();
            
            //query 만들기
            StringBuilder sb = new StringBuilder();					// StringBuilder는 긴문자열을 사용할때 유용하다.
            String sql = sb.append("create table if not exists product(")
            		.append("no int not null auto_increment primary key,")
                    .append("name varchar(20),")
                    .append("price varchar(20),")
                    .append("su varchar(20),")
                    .append("sum varchar(20),")
                    .append("sumtotal varchar(20)")
                    .append(");").toString();
            
            System.out.println("DB연동 테이블 생성!!");
 
            //query문 날리기
            stmt.execute(sql);
        }
 
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            try{
                //자원 해제
                if(con != null && !con.isClosed())
                    con.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}