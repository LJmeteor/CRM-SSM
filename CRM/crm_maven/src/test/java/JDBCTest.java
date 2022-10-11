
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JDBCTest {


    @Test
    public  void testJDBC( ){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Suceessful Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("failuer Driver");
            throw new RuntimeException(e);
        }
        Connection connection=null;
        String url="jdbc:mysql://127.0.0.1:3306/crm2022";
        String username="root";
        String password="123456";
        try {
            connection=  DriverManager.getConnection(url,username,password);
            System.out.println("Suceessful connection");
        } catch (SQLException e) {
            System.out.println("failuer connection");
            throw new RuntimeException(e);
        }
        if (connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
    @Test public void c(){
        int a= 4/2 - 2/3 -1/3 +1/3;
        System.out.println(4/2 - 2/3 -1/3 +1/3);
    }


}
