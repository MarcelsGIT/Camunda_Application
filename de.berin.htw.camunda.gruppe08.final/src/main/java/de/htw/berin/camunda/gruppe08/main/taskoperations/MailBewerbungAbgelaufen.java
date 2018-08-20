package de.htw.berin.camunda.gruppe08.main.taskoperations;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.htw.berin.camunda.gruppe08.domain.Kunde;
import de.htw.berin.camunda.gruppe08.mail.MailAnfrageAbgelaufen;

public class MailBewerbungAbgelaufen implements JavaDelegate {

	@Override
	public void execute(DelegateExecution de) throws Exception {
		// TODO Auto-generated method stub
		Kunde customer = (Kunde) de.getVariable("Anfragender");
		MailAnfrageAbgelaufen abgelaufen = new MailAnfrageAbgelaufen();
		abgelaufen.sendMail(customer.getEmail(), customer.getFirstName());
	}

}
