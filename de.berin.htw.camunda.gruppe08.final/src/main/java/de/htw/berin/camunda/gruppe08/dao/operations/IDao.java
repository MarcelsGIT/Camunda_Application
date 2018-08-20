package de.htw.berin.camunda.gruppe08.dao.operations;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

public interface IDao {
	/**
	 * LinkedList<HashMap<Integer, Object>> values ist eine Liste, die Hashmaps enthält die 
	 * die Values eines zu Schreibenden Datensatzes enthält. Für jede Hashmap wird ein InsertBefehl ausgeführt
	 * @param values
	 */
	public void insertIntoDatabase(Connection con, HashMap<String, Object> values) throws SQLException;
	/**
	 * String ... definiert eine where Klausel oder ein Set von where Klauseln. Hier wird ein einzelnes 
	 * Objekt erzeugt
	 * @param whereClause
	 * @return
	 */
	public HashMap<String, Object> selectRequest(Connection con, String whereClause) throws SQLException;
	
	/**
	 * erneutes Schreiben der selben Anfrage, ergänzt um die Werte des Sitters. (SQL Update)
	 * @param con
	 * @param values
	 */
	public void overwriteRequest(Connection con, HashMap<String, Object> values)throws SQLException; 
	
	/**
	 * Löschen der Anfrage wegen Ablaufs
	 * @param AnfrageID
	 */
	public void deleteRequest(Connection con, String AnfrageID)throws SQLException;
	

}
