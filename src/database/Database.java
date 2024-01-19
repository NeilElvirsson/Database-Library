package database;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {
   static String url = "localhost";
   static int port = 3306;
   public static String database = "";
   public static String username = "";
   public static String password = "";

   /*
   Private variables
    */
   private static Database db;

   private MysqlDataSource dataSource;
   private Database() { }

    private void initializeDataSource(){

           dataSource = new MysqlDataSource();
           dataSource.setUser(username);
           dataSource.setPassword(password);
           dataSource.setURL("jdbc:mysql://" + url + ":" + port + "/" + database + "?serverTimeZone=UTC");
           // jdbc:mysql://localhost:3306/<database>


    }
    private Connection createConnection(){
       try {
           return dataSource.getConnection();
       } catch(SQLException ex) {

           return null;
       }
    }
    public static Connection getConnection() {
       if (db == null) {
           db = new Database();
           db.initializeDataSource();
       }
       return db.createConnection();
    }
    public static void SQLExceptionPrint (SQLException sqle) {
       SQLExceptionPrint(sqle, false);
    }
    public static void SQLExceptionPrint(SQLException sqle, boolean printStackTrace) {
       while (sqle != null) {
           System.out.println("\n---SQLException Caught---\n");
           System.out.println("SQLState:" + sqle.getSQLState());
           System.out.println("Errorcode: " + sqle.getErrorCode());
           System.out.println("Message: " + sqle.getMessage());
           if(printStackTrace) sqle.printStackTrace();
           sqle = sqle.getNextException();
       }
    }

}
