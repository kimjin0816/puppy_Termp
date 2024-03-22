import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// ������ mySQL�ȿ� ���̺��� �����ϴ� SQL����
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
 
            //DB�� ����� con ��ü�κ��� Statement ��ü ȹ��.
            stmt = con.createStatement();
            
            //query �����
            StringBuilder sb = new StringBuilder();					// StringBuilder�� �乮�ڿ��� ����Ҷ� �����ϴ�.
            String sql = sb.append("create table if not exists product(")
            		.append("no int not null auto_increment primary key,")
                    .append("name varchar(20),")
                    .append("price varchar(20),")
                    .append("su varchar(20),")
                    .append("sum varchar(20),")
                    .append("sumtotal varchar(20)")
                    .append(");").toString();
            
            System.out.println("DB���� ���̺� ����!!");
 
            //query�� ������
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
                //�ڿ� ����
                if(con != null && !con.isClosed())
                    con.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}