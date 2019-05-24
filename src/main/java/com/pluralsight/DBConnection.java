package com.pluralsight;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
  
  private Connection jdbcConnection;
  private String jdbcURL = "jdbc:mysql://localhost:3306/book_store";
  private String jdbcUsername = "root";
  private String jdbcPassword = "root";
  
  public DBConnection() {
    connect();
  }

  public Connection getConnection() {
    return jdbcConnection;
  }

//  public void connect()  {
//    try {
//      Class.forName("org.sqlite.JDBC");
//      jdbcConnection = DriverManager.getConnection("jdbc:sqlite:book_store.db");
//      System.out.println("Opened database successfully");
//
//      createTableIfNotExists();
//    } catch ( Exception e ) {
//     System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//     System.exit(0);
//   }
// }
//  
  
 
  public void connect() {
		try {
			if (jdbcConnection == null || jdbcConnection.isClosed()) {
				
				Class.forName("com.mysql.jdbc.Driver");
				
				jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
				
				System.out.println("MySQL Connection Established");		
			}		
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
  
 

 private void createTableIfNotExists() {
   try {
       DatabaseMetaData meta = jdbcConnection.getMetaData();
       ResultSet res = meta.getTables(null, null, null, new String[] {"TABLE"});
       Statement stmt = jdbcConnection.createStatement();
       if (!res.next()) {
       	// Create table

           String sql = "CREATE TABLE book " +
                          "(id INTEGER PRIMARY KEY NOT NULL," +
                          " title TEXT NOT NULL, " +
                          " author TEXT NOT NULL, " +
                          " price REAL)";
           stmt.executeUpdate(sql);

           sql = "INSERT INTO book (title, author, price) VALUES (\"1984\", \"George Orwell\", 1.00)";
           stmt.executeUpdate(sql);

           stmt.close();
       }
    } catch ( Exception e ) {
       System.err.println( e.getClass().getName() + ": " + e.getMessage() );
       System.exit(0);
    }
 }


  public void disconnect() {
    try {
    	if (jdbcConnection != null && !jdbcConnection.isClosed()) {
    	    jdbcConnection.close();
    	}
    } catch (SQLException e) {
    	e.printStackTrace();
    }
  }
}
