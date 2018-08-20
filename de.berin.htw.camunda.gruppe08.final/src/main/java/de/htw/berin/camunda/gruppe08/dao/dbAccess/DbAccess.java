package de.htw.berin.camunda.gruppe08.dao.dbAccess;

import java.sql.Connection;
import java.sql.DriverManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DbAccess {
	
	
	 public static void loadDriver(final String driver) {
		    try {
		      Class.forName(driver);
		      
		    } catch (Exception e) {

		      throw new RuntimeException(e);
		    }
		  }

		  public static Connection getConnectionViaDriverManager(final String url, final String user, final String passwd) {
		    try {
		      Connection connection = DriverManager.getConnection(url, user, passwd);

		      return connection;
		    } catch (Exception e) {
	
		      throw new RuntimeException(e);
		    }
		  }

}
