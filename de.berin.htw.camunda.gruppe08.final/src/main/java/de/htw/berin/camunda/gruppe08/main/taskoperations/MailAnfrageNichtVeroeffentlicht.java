package de.htw.berin.camunda.gruppe08.main.taskoperations;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.htw.berin.camunda.gruppe08.domain.Kunde;
import de.htw.berin.camunda.gruppe08.mail.MailAnfrageAbgelaufen;

public class MailAnfrageNichtVeroeffentlicht implements JavaDelegate {

	@Override
	public void execute(DelegateExecution de) throws Exception {
		// TODO Auto-generated method stub
		Kunde customer = (Kunde) de.getVariable("Anfragender");
		MailAnfrageAbgelaufen nv = new MailAnfrageAbgelaufen();
		nv.sendMail(customer.getEmail(), customer.getFirstName());
	}

}