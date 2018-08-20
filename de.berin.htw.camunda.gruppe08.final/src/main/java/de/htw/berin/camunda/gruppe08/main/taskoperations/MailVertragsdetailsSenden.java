package de.htw.berin.camunda.gruppe08.main.taskoperations;

import java.util.HashMap;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.htw.berin.camunda.gruppe08.dao.Connector;
import de.htw.berin.camunda.gruppe08.domain.Kunde;
import de.htw.berin.camunda.gruppe08.mail.MailVermittelt;

public class MailVertragsdetailsSenden implements JavaDelegate {

	@Override
	public void execute(DelegateExecution de) throws Exception {
		// TODO Auto-generated method stub
		Kunde kunde = (Kunde) de.getVariable("Anfragender");
		MailVermittelt mv= new MailVermittelt();
		HashMap<String, Object> anfrage = Connector.getInstance().selectRequest(kunde.getId());
		mv.sendMail((String)anfrage.get("Email"),(String) anfrage.get("Vorname"));
		mv.sendMail((String)anfrage.get("SitterMail"), (String)anfrage.get("SitterVorname"));
	}

}
