package de.htw.berin.camunda.gruppe08.dao.operations;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.htw.berin.camunda.gruppe08.dao.dbAccess.DbAccess;

/**
 * @project:de.htw.berin.camunda.gruppe08
 * @author: Marcel
 * @created: 17.06.2018
 * @changed: 17.06.2018
 * @changeBy: 
 * @description: Alle Datenbankzugriffe für die Anfragetabelle werden hier ausgeführt.
 * @comments:
 */
public class AnfrageDao implements IDao {
	
	private static AnfrageDao ad;
	private String[] columnnames = {"AnfrageID", "Email", "Vorname" ,"Nachname", "Strasse", "PLZ", "Ort", "Tier", "Futter", 
									"Haltungsart", "andereTiere", "startDatum", "endDatum", "SitterMail", "SitterVorname",
									"SitterNachname", "SitterStrasse", "SitterPLZ", "SitterOrt", "SitterPreis"};
	
	private AnfrageDao() {
	}
	
	public static AnfrageDao getInstance() {
		if(ad == null) {
			ad = new AnfrageDao();
		}
		return ad;
	}
	/* (non-Javadoc)
	 * 		requestDB.put("AnfrageID", customer.getId());
		requestDB.put("Email", customer.getEmail());
		requestDB.put("Vorname", customer.getFirstName());
		requestDB.put("Nachname", customer.getLastName());
		requestDB.put("", customer.getStreet());
		requestDB.put("PLZ", customer.getPlz());
		requestDB.put("Ort", customer.getCity());
		requestDB.put("Tier", customer.getAnfrage().getPet());
		requestDB.put("Futter", customer.getAnfrage().getPetfood());
		requestDB.put("Haltungsart", customer.getAnfrage().getKeeping());
		requestDB.put("andereTiere", customer.getAnfrage().getMorePets());
		requestDB.put("startDatum", customer.getAnfrage().getStartDate());
		requestDB.put("endDatum", customer.getAnfrage().getEndDate());
	 * @see de.htw.berin.camunda.gruppe08.dao.operations.IDao#insertIntoDatabase(java.sql.Connection, java.util.LinkedList)
	 */
	@Override
	public void insertIntoDatabase(Connection con, HashMap<String, Object> values) throws SQLException{
		String query = "INSERT INTO Anfrage (" + this.columnNamesAsString(0, 12) + ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		System.out.println(query);
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, (String)values.get(this.columnnames[0]));
		pstmt.setString(2, (String)values.get(this.columnnames[1]));
		pstmt.setString(3, (String)values.get(this.columnnames[2]));
		pstmt.setString(4, (String)values.get(this.columnnames[3]));
		pstmt.setString(5, (String)values.get(this.columnnames[4]));
		pstmt.setString(6, (String)values.get(this.columnnames[5]));
		pstmt.setString(7, (String)values.get(this.columnnames[6]));
		pstmt.setString(8, (String)values.get(this.columnnames[7]));
		pstmt.setString(9, (String)values.get(this.columnnames[8]));
		pstmt.setString(10, (String)values.get(this.columnnames[9]));
		pstmt.setBoolean(11, (boolean)values.get(this.columnnames[10]));
		pstmt.setDate(12, Date.valueOf((LocalDate) values.get(this.columnnames[11])));
		pstmt.setDate(13, Date.valueOf((LocalDate) values.get(this.columnnames[12])));
		
		pstmt.execute();
		pstmt.close();
	}

	/* (non-Javadoc)
	 * @see de.htw.berin.camunda.gruppe08.dao.operations.IDao#selectSingeObject(java.sql.Connection, java.lang.String[])
	 */
	@Override
	public HashMap<String, Object> selectRequest(Connection con, String whereClause) throws SQLException{
		HashMap<String, Object> data = new HashMap<String, Object>();
		String query = "SELECT * FROM Anfrage WHERE AnfrageID = ?";
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, whereClause);
		
		ResultSet rs = pstmt.executeQuery();
		data = this.resultSetToMap(rs);
		
		rs.close();
		pstmt.close();

		return data;
	}

	/* (non-Javadoc)
	 * @see de.htw.berin.camunda.gruppe08.dao.operations.IDao#overwriteRequest(java.sql.Connection, java.util.HashMap)
	 */
	@Override
	public void overwriteRequest(Connection con, HashMap<String, Object> values)throws SQLException {
		// TODO Auto-generated method stub
		String query = "UPDATE Anfrage SET SitterMail = ?, SitterVorname = ?, SitterNachname = ?, SitterStrasse = ?, SitterPLZ = ?, SitterOrt = ?, SitterPreis = ? WHERE AnfrageID =?";
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, (String)values.get(this.columnnames[13]));
		pstmt.setString(2, (String)values.get(this.columnnames[14]));
		pstmt.setString(3, (String)values.get(this.columnnames[15]));
		pstmt.setString(4, (String)values.get(this.columnnames[16]));
		pstmt.setString(5, (String)values.get(this.columnnames[17]));
		pstmt.setString(6, (String)values.get(this.columnnames[18]));
		pstmt.setLong(7, (Long)values.get(this.columnnames[19]));
		pstmt.setString(8, (String)values.get(this.columnnames[0]));
		
		pstmt.execute();
		pstmt.close();
	}

	/* (non-Javadoc)
	 * @see de.htw.berin.camunda.gruppe08.dao.operations.IDao#deleteRequest(java.lang.String)
	 */
	@Override
	public void deleteRequest(Connection con, String AnfrageID)throws SQLException {
		// TODO Auto-generated method stub
		String query = "DELETE FROM Anfrage WHERE AnfrageID = ?";
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, AnfrageID);
		
		pstmt.execute();
		pstmt.close();
		System.out.println("Gelöscht");
		
	}
	
	private HashMap<String, Object> resultSetToMap(ResultSet rs)throws SQLException{
		HashMap<String, Object> results = new HashMap<String, Object>();
		if(rs.next()) {
			for(int i = 0 ; i < this.columnnames.length ; i++) {
			results.put(this.columnnames[i], rs.getObject(this.columnnames[i]));
			}
		}
		return results;
	}
	
	private String columnNamesAsString(int startNumber, int endNumber) {
		String chained = "";
		for(int i = startNumber ; i <= endNumber ; i++ ) {
			chained += this.columnnames[i];
			if(i != endNumber) {
				chained += ", ";
			}
		}
		return chained;
	}


}
