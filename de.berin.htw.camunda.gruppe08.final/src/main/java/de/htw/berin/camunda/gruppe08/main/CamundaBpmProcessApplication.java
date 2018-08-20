	package de.htw.berin.camunda.gruppe08.main;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.identity.User;

import de.htw.berin.camunda.gruppe08.domain.Kunde;

/**
 * Process Application exposing this application's resources the process engine. 
 */
@ProcessApplication
public class CamundaBpmProcessApplication extends ServletProcessApplication {

  private static final String PROCESS_DEFINITION_KEY = "de.htw.berin.camunda.gruppe08";

  /**
   * In a @PostDeploy Hook you can interact with the process engine and access 
   * the processes the application has deployed. 
   */
  @PostDeploy
  public void onDeploymentFinished(ProcessEngine processEngine) {
	Cleaner.getInstance().clean(processEngine);
	DataGenerator2.getInstance().setup(processEngine);  
  }

}
