package de.htw.berin.camunda.gruppe08.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.htw.berin.camunda.gruppe08.dao.dbAccess.DbAccess;
import de.htw.berin.camunda.gruppe08.dao.dbAccess.DbCred;
import de.htw.berin.camunda.gruppe08.dao.operations.AnfrageDao;
import de.htw.berin.camunda.gruppe08.domain.Anfrage;
import de.htw.berin.camunda.gruppe08.domain.Sitter;
/**
 * 
 * @project:de.htw.berin.camunda.gruppe08
 * @author: Marcel
 * @created: 17.06.2018
 * @changed: 17.06.2018
 * @changeBy:
 * @description: Stellt die Datenbankverbindung her. SINGLETON PATTERN mit dem erzeugten Objekt wird die Datenbankverbindung aufgebaut. 
 * 				 Daher muss die Verbindung nach jedem Objektaufruf geschlossen werden. @see close Methode am Ende der Klasse
 * @comments:
 */
public class Connector implements IConnect{
	
	private static Connector connect;
	private Connection con;
	
	/**
	 * 
	 * @return connect
	 */
	public static Connector getInstance() {
		if(connect == null) {
			connect = new Connector();
			connect.establishConnection();
		}
		return connect;
	}
	
	/* (non-Javadoc)
	 * @see de.htw.berin.camunda.gruppe08.dao.IConnect#insertRequestIntoDatabase(java.util.HashMap)
	 */
	@Override
	public void insertRequestIntoDatabase(HashMap<String, Object> values) {
		// TODO Auto-generated method stub
		try {
			AnfrageDao.getInstance().insertIntoDatabase(this.con, values);

		}catch(SQLException e) {
			
			e.printStackTrace();
			this.close();
		}
	}
	/* (non-Javadoc)
	 * @see de.htw.berin.camunda.gruppe08.dao.IConnect#selectRequest(java.lang.String[])
	 */
	@Override
	public HashMap<String, Object> selectRequest(String whereClause) {
		// TODO Auto-generated method stub
		HashMap<String, Object> data = null;
		try {
			data = AnfrageDao.getInstance().selectRequest(this.con, whereClause);

		}catch(SQLException e) {
			e.printStackTrace();
			this.close();
		}
		return data;
	}
	/* (non-Javadoc)
	 * @see de.htw.berin.camunda.gruppe08.dao.IConnect#overwriteRequest(java.util.HashMap)
	 */
	@Override
	public void overwriteRequest(HashMap<String, Object> values) {
		// TODO Auto-generated method stub
		try {
			AnfrageDao.getInstance().overwriteRequest(this.con, values);
		}catch(SQLException e) {
			
			e.printStackTrace();
			this.close();
		}
	}
	/* (non-Javadoc)
	 * @see de.htw.berin.camunda.gruppe08.dao.IConnect#deleteRequest(java.lang.String)
	 */
	@Override
	public void deleteRequest(String RequestID) {
		// TODO Auto-generated method stub
		try {
			AnfrageDao.getInstance().deleteRequest(this.con, RequestID);
		}catch(SQLException e) {
			
			e.printStackTrace();
			this.close();
		}
	}

	
	/**
	 * Darf nicht aufgerufen werden. 
	 */
	private void establishConnection() {
		DbAccess.loadDriver(DbCred.driverClass);
		this.con = DbAccess.getConnectionViaDriverManager(DbCred.url, DbCred.user, DbCred.password); // returns Connection
	}
	/**
	 * Wird am Ende jeder execute verwendet @see alle Klassen, die später an Tasks angebunden werden 
	 * @param con
	 */
	public void close() {
		try{con.close();}catch(SQLException e){e.printStackTrace();}
		connect = null;
	}






}
