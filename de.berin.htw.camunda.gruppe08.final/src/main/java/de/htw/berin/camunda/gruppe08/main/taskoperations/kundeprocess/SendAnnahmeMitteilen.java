package de.htw.berin.camunda.gruppe08.main.taskoperations.kundeprocess;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;

import de.htw.berin.camunda.gruppe08.dao.Connector;
import de.htw.berin.camunda.gruppe08.domain.Kunde;
import de.htw.berin.camunda.gruppe08.domain.Sitter;

public class SendAnnahmeMitteilen implements JavaDelegate {

	@Override
	public void execute(DelegateExecution de) throws Exception {
		// TODO Auto-generated method stub
		Kunde customer = (Kunde) de.getVariable("Anfragender");
		Sitter sitter = (Sitter) de.getVariable("Bewerbung");
		HashMap<String, Object> data = this.getSitterData(sitter);
		data.put("AnfrageID", customer.getId());
		Connector.getInstance().overwriteRequest(data);
		Connector.getInstance().close();
		de.getProcessEngineServices().getRuntimeService().createMessageCorrelation
		(
				"Bewerbung_Angenommen3"
		).setVariable
		(
				"Angenommen", de.getVariable("Bewerbung")
		).processInstanceId
		(
				(String)de.getVariable("Main_InstanceID")
		).correlate();
		System.out.println("Kunde hat die Bewerbung angenommen");
	}
	
	private HashMap<String, Object> getSitterData(Sitter s) {

		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("SitterMail", "camundagruppe08@gmail.com");
		data.put("SitterVorname", s.getFirstName());
		data.put("SitterNachname", s.getLastName());
		data.put("SitterStrasse", s.getStreet());
		data.put("SitterPLZ", s.getPlz());
		data.put("SitterOrt", s.getCity());
		data.put("SitterPreis", s.getPricePerDay());
		return data;
	}

}
