package com.iti.project.TwilioSMSClient.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DatabaseUtil {
//omar
//      private static final String DB_URL = "jdbc:mysql://localhost:3306/twiliosms_db";
//   private static final String USER = "root";
//   private static final String PASS = "password";
//mahmoud
     private static final String DB_URL = "jdbc:mysql://localhost:3306/twilloProject";
     private static final String USER = "root";
     private static final String PASS = "Mahmoud1901**";


   static {
       try {
           Class.forName("com.mysql.cj.jdbc.Driver");
       }catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
   }

   public static Connection getConnection() throws SQLException {
       return DriverManager.getConnection(DB_URL, USER, PASS);
   }
   public static void closeConnection(Connection conn) throws SQLException {
       if(conn != null) {
           conn.close();
       }
   }
}
