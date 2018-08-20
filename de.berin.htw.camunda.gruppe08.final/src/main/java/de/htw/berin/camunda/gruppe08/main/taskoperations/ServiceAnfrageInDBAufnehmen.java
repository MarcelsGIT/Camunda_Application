package de.htw.berin.camunda.gruppe08.main.taskoperations;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.htw.berin.camunda.gruppe08.dao.Connector;
import de.htw.berin.camunda.gruppe08.domain.Kunde;
/**
 * @project:de.htw.berin.camunda.gruppe08
 * @author: Marcel
 * @created: 17.06.2018
 * @changed: 17.06.2018
 * @changeBy:
 * @description: Anfrage wird in DB aufgenommen.
 * @comments:
 */
public class ServiceAnfrageInDBAufnehmen implements JavaDelegate {
	@Override
	public void execute(DelegateExecution de) throws Exception {
		// TODO Auto-generated method stub
		Connector.getInstance().insertRequestIntoDatabase(this.setRequestData((Kunde)de.getVariable("Anfragender")));
		Connector.getInstance().close();

		de.setVariable("Main_InstanceID", de.getProcessInstanceId());
	}
	
	private HashMap<String, Object> setRequestData(Kunde customer){
		HashMap<String, Object> requestDB = new HashMap<String, Object>();
		
		requestDB.put("AnfrageID", customer.getId());
		requestDB.put("Email", customer.getEmail());
		requestDB.put("Vorname", customer.getFirstName());
		requestDB.put("Nachname", customer.getLastName());
		requestDB.put("Strasse", customer.getStreet());
		requestDB.put("PLZ", customer.getPlz());
		requestDB.put("Ort", customer.getCity());
		requestDB.put("Tier", customer.getAnfrage().getPet());
		requestDB.put("Futter", customer.getAnfrage().getPetfood());
		requestDB.put("Haltungsart", customer.getAnfrage().getKeeping());
		requestDB.put("andereTiere", customer.getAnfrage().getMorePets());
		requestDB.put("startDatum", customer.getAnfrage().getStartDate());
		requestDB.put("endDatum", customer.getAnfrage().getEndDate());
		this.sysout(requestDB);
		return requestDB;
	}
	
	private void sysout(HashMap<String, Object> requestDB) {
		System.out.println("In Datenbank eingetragen:");
		for(Object o : requestDB.values()) {
			System.out.println(o.toString());
		}
	}

}
