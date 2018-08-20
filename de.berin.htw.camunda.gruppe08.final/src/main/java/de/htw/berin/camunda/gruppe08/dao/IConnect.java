package de.htw.berin.camunda.gruppe08.dao;

import java.util.HashMap;
import java.util.LinkedList;

import de.htw.berin.camunda.gruppe08.domain.Anfrage;
import de.htw.berin.camunda.gruppe08.domain.Sitter;

public interface IConnect {
	
	/**
	 * Request wird in DB aufgenommen.
	 * @param values
	 */
	public void insertRequestIntoDatabase(HashMap<String, Object> values);
	/**
	 * Anfrage wird aus DB abgerufen
	 * @param whereClause
	 * @return
	 */
	public HashMap<String, Object> selectRequest(String whereClause);
	
	/**Überschreiben des Requests mit hinzugefügten Daten nachdem der Kunde angenommen hat. (Update)
	 * @param values
	 */
	public void overwriteRequest(HashMap<String, Object> values);
	/**
	 * Löschen des Requests. 
	 * @param RequestID
	 */
	public void deleteRequest(String RequestID);


}

