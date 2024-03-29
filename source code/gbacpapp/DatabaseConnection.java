package fii.student.gbacpapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class DatabaseConnection {
 
   public static Connection getConnection()
           throws ClassNotFoundException, SQLException {
	   
        String hostName = "localhost";
        String sid = "XEPDB1";
        String userName = "student";
        String password = "student";
 
       return getConnection(hostName, sid, userName, password);
   }
 
   public static Connection getConnection(String hostName, String sid,
           String userName, String password) throws ClassNotFoundException,
           SQLException {
  
       Class.forName("oracle.jdbc.driver.OracleDriver");
 
       // URL Connection for Oracle
       String connectionURL = "jdbc:oracle:thin:@" + hostName + ":1521/" + sid;
 
       Connection conn = DriverManager.getConnection(connectionURL, userName,
               password);
       return conn;
   }
   
    public static void closeConnection(Connection conn) {
            try {
                    conn.close();
            } catch (Exception e) {
            }
    }

    public static void rollback(Connection conn) {
            try {
                    conn.rollback();
            } catch (Exception e) {
            }
    }
}

