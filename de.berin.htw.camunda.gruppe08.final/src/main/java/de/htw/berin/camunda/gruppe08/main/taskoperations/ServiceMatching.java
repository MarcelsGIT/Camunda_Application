package de.htw.berin.camunda.gruppe08.main.taskoperations;

import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.htw.berin.camunda.gruppe08.dao.Connector;
import de.htw.berin.camunda.gruppe08.domain.Kunde;
import de.htw.berin.camunda.gruppe08.domain.Sitter;
import de.htw.berin.camunda.gruppe08.main.taskoperations.service.Matching;

/**
 * @project:de.htw.berin.camunda.gruppe08
 * @author: Marcel
 * @created: 17.06.2018
 * @changed: 17.06.2018
 * @changeBy:
 * @description: Matching findet hier statt.
 * @comments:
 */
public class ServiceMatching implements JavaDelegate {
	private static final Logger L = LoggerFactory.getLogger(ServiceMatching.class);
	@Override
	public void execute(DelegateExecution de) throws Exception {
		// TODO Auto-generated method stub
		try {
			HashMap<String, Sitter> matchedSitters = Matching.getInstance().match
														(
																	(HashMap<String, Sitter>)de.getVariable("SitterMap"),
																	(Kunde) de.getVariable("Anfragender")
														);
			de.setVariable(
					"MatchedSitters", matchedSitters 
					);
			System.out.println("Sitter wurden mit der Anfrage gematched");
		}catch(Exception e) {
			throw new BpmnError("NO_SITTERS_MATCHED");
		}
	}
	


}
