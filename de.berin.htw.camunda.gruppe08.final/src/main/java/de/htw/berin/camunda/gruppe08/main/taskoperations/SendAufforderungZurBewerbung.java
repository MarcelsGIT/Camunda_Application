package de.htw.berin.camunda.gruppe08.main.taskoperations;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.htw.berin.camunda.gruppe08.dao.Connector;
import de.htw.berin.camunda.gruppe08.domain.Sitter;
import de.htw.berin.camunda.gruppe08.main.taskoperations.service.Matching;
/**
 * @project:de.htw.berin.camunda.gruppe08
 * @author: Marcel
 * @created: 17.06.2018
 * @changed: 17.06.2018
 * @changeBy:
 * @description: Aufforderung zur Bewerbung wird an entsprechende Sitter gesendet. Sitterprozess "Bewerben" wird gestartet.
 * @comments:
 */
public class SendAufforderungZurBewerbung implements JavaDelegate {
	
	private static final Logger L = LoggerFactory.getLogger(SendAufforderungZurBewerbung.class);
	
	@Override
	public void execute(DelegateExecution de) throws Exception {
		// TODO Auto-generated method stub
		try {

			HashMap<String, Sitter> sitters = (HashMap<String, Sitter>) de.getVariable("MatchedSitters");
			if(sitters == null) {System.out.println("kommt nicht an");}
			this.messageMatchedSitters(de, sitters);
			System.out.println("Entsprechende Sitter zur Bewerbung aufgefordert");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void messageMatchedSitters(DelegateExecution de, HashMap<String, Sitter> sitters) {
		for(Sitter sitter : sitters.values()) {
			this.createProcessInstance(de, sitter.getId());
		}
	}
	
	private ProcessInstance createProcessInstance(DelegateExecution de, String userId) {
		de.setVariable("applyedSitterId", userId);
		ProcessInstance pi = de.getProcessEngineServices().getRuntimeService().startProcessInstanceByMessage("Start_Sitter", de.getVariables());
		de.setVariable("Sitter_InstanceID", pi.getProcessInstanceId());
		return pi;
	}

}

