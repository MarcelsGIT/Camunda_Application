package de.htw.berin.camunda.gruppe08.main.taskoperations;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.htw.berin.camunda.gruppe08.dao.Connector;
import de.htw.berin.camunda.gruppe08.domain.Sitter;
import de.htw.berin.camunda.gruppe08.main.taskoperations.service.Bewertung;

/**
 * @project:de.htw.berin.camunda.gruppe08
 * @author: Marcel
 * @created: 17.06.2018
 * @changed: 17.06.2018
 * @changeBy:
 * @description: Bewertung findet hier statt.
 * @comments:
 */
public class ServiceBewerbungBewerten implements JavaDelegate {

	
	/**
	 * Bewerbung wird bewertet
	 */
	@Override
	public void execute(DelegateExecution de) throws Exception {
		// TODO Auto-generated method stub
		try {
			Sitter bewerber = (Sitter)de.getVariable("Bewerbung");
			String bewertung = Bewertung.getInstance().priceEvalutaion(bewerber);
			de.setVariable("Bewertung", bewertung);
			System.out.println("Die Bewerbung wurde bewertet");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
