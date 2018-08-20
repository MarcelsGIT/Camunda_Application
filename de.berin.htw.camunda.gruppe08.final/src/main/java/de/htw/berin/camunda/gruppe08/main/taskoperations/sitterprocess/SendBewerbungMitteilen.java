package de.htw.berin.camunda.gruppe08.main.taskoperations.sitterprocess;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;

import de.htw.berin.camunda.gruppe08.domain.Sitter;
import java.util.HashMap;


public class SendBewerbungMitteilen implements JavaDelegate {

	@Override
	public void execute(DelegateExecution de) throws Exception {
		// TODO Auto-generated method stub
		
		User user = de.getProcessEngineServices().getIdentityService()
				.createUserQuery().userId
				(
						de.getProcessEngineServices().getIdentityService().getCurrentAuthentication().getUserId()
				)
				.singleResult();
		
		HashMap<String, Sitter> sitters = (HashMap<String, Sitter>)de.getVariable("MatchedSitters");
		Sitter sitter = sitters.get(user.getId());
		
		sitter.setPricePerDay((Long)de.getVariable("preisProTag"));
		
		de.getProcessEngineServices().getRuntimeService().createMessageCorrelation
			(
				"Bewerbung_Entgegennehmen2"
			).setVariable
			(
					"Bewerbung", sitter
			).setVariable
			(
				"preisProTag", de.getVariable("preisProTag")
			).setVariable
			(
					"BewerberVorname", sitter.getFirstName()
			).setVariable
			(
					"BewerberNachname", sitter.getLastName()
			).processInstanceId
			(
					(String)de.getVariable("Main_InstanceID")
			).correlate();
			System.out.println("Sitter hat sich beworben");	
	}

}
