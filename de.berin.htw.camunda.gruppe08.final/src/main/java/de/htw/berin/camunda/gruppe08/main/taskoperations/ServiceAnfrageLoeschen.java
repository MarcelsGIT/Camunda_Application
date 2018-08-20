package de.htw.berin.camunda.gruppe08.main.taskoperations;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.EventSubscriptionQuery;

import de.htw.berin.camunda.gruppe08.dao.Connector;
import de.htw.berin.camunda.gruppe08.domain.Kunde;

/**
 * @author Marcel
 * 
 */

public class ServiceAnfrageLoeschen implements JavaDelegate {


	/**
	 * Löscht die Anfrage aus der Datenbank und sendet ein Signal an die Sitter und Kundenprozesse falls noch aktive Instanzen existieren
	 */
	@Override
	public void execute(DelegateExecution de) throws Exception {
		// TODO Auto-generated method stub
		Kunde customer = (Kunde) de.getVariable("Anfragender");
		Connector.getInstance().deleteRequest(customer.getId());
		System.out.println("Anfrage ins archiv verschoben");
	}

}
