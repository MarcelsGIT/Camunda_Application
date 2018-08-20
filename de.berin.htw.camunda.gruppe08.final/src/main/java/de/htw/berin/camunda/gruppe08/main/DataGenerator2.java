package de.htw.berin.camunda.gruppe08.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.FilterService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Groups;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resource;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.filter.Filter;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.persistence.entity.AuthorizationEntity;
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;
import org.camunda.bpm.engine.task.TaskQuery;

import de.htw.berin.camunda.gruppe08.dao.data.CsvReader;
import de.htw.berin.camunda.gruppe08.domain.Kunde;
import de.htw.berin.camunda.gruppe08.domain.Sitter;

public class DataGenerator2 {

	
	private static DataGenerator2 dg;
	private final String email = "camundaGruppe08@gmail.com";
	private DataGenerator2() {
		
	}
	
	public static DataGenerator2 getInstance() {
		if(dg == null) {
			dg = new DataGenerator2();
		}
		return dg;
	}
	
	public void setup(ProcessEngine engine) {
		//Services
		IdentityService is = engine.getIdentityService();
		AuthorizationService as = engine.getAuthorizationService();
		FilterService fs = engine.getFilterService();
		TaskService ts = engine.getTaskService();

//		is.deleteGroup("sitters");
//		is.deleteGroup("employees");
//		is.deleteGroup("customers");
//		is.deleteUser("employee");
//		is.deleteUser("chunsa");
//		is.deleteUser("sitter0SitterID");
//		is.deleteUser("sitter01");
//		is.deleteUser("sitter02");
//		is.deleteUser("sitter03");
//		is.deleteUser("sitter04");
//		is.deleteUser("sitter05");
		//this.authGlobalGroupFilter(fs, as, ts);
//		this.authEmployeesForTask(as);
//		this.authSittersForTask(as);
//		this.authCustomersForTask(as);
		
		//Überprüfe ob die User bereits existieren anhand von employee
		if(is.createUserQuery().userId("demo").singleResult() != null) {
			return;
		}
		System.out.println("wirdausgeführt");
		//User erstellen
		this.createDemo(is);
		this.createEmployee(is);
		HashMap<String, Sitter> sitters = null;
		try{sitters = CsvReader.getInstance().readSitterFile();}catch(Exception e) {e.printStackTrace();}
		this.createSitters(is, sitters);
		this.createKunde(is);
		
		//Gruppen erstellen
		this.createAdminGroup(is);
		 this.createEmployeeGroup(is);
		 this.createSitterGroup(is);
		this.createCustomerGroup(is);
		
		//Hinzufuegen von Usern zu Gruppen
		is.createMembership("demo", "employees");
		is.createMembership("demo", "sitters");
		is.createMembership("demo", "customers");
		is.createMembership("demo", Groups.CAMUNDA_ADMIN);
		
		is.createMembership("employee", "employees");
		this.createMembershipForSitters(is);
		is.createMembership("chunsa", "customers");
		
		//Zugang zur Tasklist
		this.authAdminGroup(as);
		this.authAccessTastlistForEmployees(as);
		this.authAccessTastlistForSitters(as);
		this.authAccessTastlistForCustomers(as);
		
		//Ermächtigungen je Prozess und UserGruppe
		this.authStartProcess(as);
		this.authMainProcess(as);
		this.authSitterProcess(as);
		this.authCustomerProcess(as);
		this.authEmployeesForTask(as);
		this.authSittersForTask(as);
		this.authCustomersForTask(as);
		
		//Sichtbarkeit von Tasklisten !!nur für eigene Tasks!!
		this.authGlobalFilter(fs, as, ts);
		this.authGlobalGroupFilter(fs, as, ts);
	}
	
	private void createKunde(IdentityService is) {
		User rqu = new UserEntity();
		rqu.setId("chunsa");
		  //Kunde rqu = (Kunde)is.newUser("chunsa");
		  rqu.setFirstName("Huyen");
		  rqu.setLastName("Dao");
		  rqu.setPassword("customerpw");
		  rqu.setEmail(this.email);
		  is.saveUser(rqu);

	}
	
	private void createDemo(IdentityService is) {
		User mpl = is.newUser("demo");
		mpl.setFirstName("Marcel");
		mpl.setLastName("Decker");
		mpl.setPassword("demo");
		mpl.setEmail(this.email);
		is.saveUser(mpl);
	}
	
	private void createSitters(IdentityService is, HashMap<String, Sitter> sitters) {
		HashMap<String, User> users = new HashMap<String, User>();
		int i = 0;
		for(Sitter sitter : sitters.values()) {
			User s = is.newUser(sitter.getId());
			s.setFirstName(sitter.getFirstName());
			s.setLastName(sitter.getLastName());
			s.setPassword("sitterpw");
			s.setEmail(this.email);
			is.saveUser(s);
		}

	}
	
	private void createEmployee(IdentityService is) {
		User mpl = is.newUser("employee");
		mpl.setFirstName("Kaan");
		mpl.setLastName("Oezgiray");
		mpl.setPassword("employeepw");
		mpl.setEmail(this.email);
		is.saveUser(mpl);

	}
	
	private void createAdminGroup(IdentityService is) {
		if(is.createGroupQuery().groupId(Groups.CAMUNDA_ADMIN).count() == 0) {
	        Group camundaAdminGroup = is.newGroup(Groups.CAMUNDA_ADMIN);
	        camundaAdminGroup.setName("camunda BPM Administrators");
	        camundaAdminGroup.setType(Groups.GROUP_TYPE_SYSTEM);
	        is.saveGroup(camundaAdminGroup);
	        System.out.println("wirdausgeführt");
		}
	}
	
	private void createEmployeeGroup(IdentityService is) {
		Group ag = is.newGroup("employees");
		ag.setName("Mitarbeiter");
		ag.setType("WORKFLOW");
		is.saveGroup(ag);

	}
	
	private void createSitterGroup(IdentityService is) {
		Group ag = is.newGroup("sitters");
		ag.setName("Sitter");
		ag.setType("WORKFLOW");
		is.saveGroup(ag);

	}
	
	private void createCustomerGroup(IdentityService is) {
		Group ag = is.newGroup("customers");
		ag.setName("Kunden");
		ag.setType("WORKFLOW");
		is.saveGroup(ag);

	}
	
	private void createMembershipForSitters(IdentityService is) {
		for(Integer i = 1 ; i < 6 ; i++){
			is.createMembership("sitter0" + i.toString(), "sitters");
		}
	}
	
	private void authAdminGroup(AuthorizationService as) {
		for (Resource resource : Resources.values()) {
	        if(as.createAuthorizationQuery().groupIdIn(Groups.CAMUNDA_ADMIN).resourceType(resource).resourceId(Authorization.ANY).count() == 0) {
	          AuthorizationEntity userAdminAuth = new AuthorizationEntity(Authorization.AUTH_TYPE_GRANT);
	          userAdminAuth.setGroupId(Groups.CAMUNDA_ADMIN);
	          userAdminAuth.setResource(resource);
	          userAdminAuth.setResourceId(Authorization.ANY);
	          userAdminAuth.addPermission(Permissions.ALL);
	          as.saveAuthorization(userAdminAuth);
	          System.out.println("wirdausgeführt");
	        }
		}
	}
	
	private void authAccessTastlistForCustomers(AuthorizationService as) {
		Authorization aTasklist = as.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
		aTasklist.setGroupId("customers");
		aTasklist.setResource(Resources.APPLICATION);
		aTasklist.setResourceId("tasklist");
		aTasklist.addPermission(Permissions.ACCESS);
		
		as.saveAuthorization(aTasklist);
	}
	
	private void authAccessTastlistForEmployees(AuthorizationService as) {
		Authorization aTasklist = as.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
		aTasklist.setGroupId("employees");
		aTasklist.setResource(Resources.APPLICATION);
		aTasklist.setResourceId("tasklist");
		aTasklist.addPermission(Permissions.ACCESS);
		
		as.saveAuthorization(aTasklist);
	}
	
	private void authAccessTastlistForSitters(AuthorizationService as) {
		Authorization aTasklist = as.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
		aTasklist.setGroupId("sitters");
		aTasklist.setResource(Resources.APPLICATION);
		aTasklist.setResourceId("tasklist");
		aTasklist.addPermission(Permissions.ACCESS);
		
		as.saveAuthorization(aTasklist);
	}
	
	private void authStartProcess(AuthorizationService as) {
		Authorization aProcessDefinition = as.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
		Authorization aProcessInstance = as.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
		
		aProcessDefinition.setGroupId("customers");
		aProcessInstance.setGroupId("customers");
		aProcessDefinition.setResource(Resources.PROCESS_DEFINITION);
		aProcessInstance.setResource(Resources.PROCESS_INSTANCE);
		aProcessDefinition.setResourceId("start_finding_sitter");
		aProcessInstance.setResourceId("*");
		aProcessDefinition.addPermission(Permissions.CREATE_INSTANCE);
		aProcessDefinition.addPermission(Permissions.READ);
		aProcessInstance.addPermission(Permissions.CREATE);
		aProcessDefinition.addPermission(Permissions.TASK_WORK);
		aProcessInstance.addPermission(Permissions.TASK_WORK);
		as.saveAuthorization(aProcessDefinition);
		as.saveAuthorization(aProcessInstance);
	}
	
	private void authEmployeesForTask(AuthorizationService as) {
		Authorization aEmployeesTask = as.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
		aEmployeesTask.setGroupId("employees");
		aEmployeesTask.setResource(Resources.TASK);
		aEmployeesTask.setResourceId("Task_16qhfal");
		aEmployeesTask.addPermission(Permissions.TASK_ASSIGN);
		aEmployeesTask.addPermission(Permissions.TASK_WORK);
		aEmployeesTask.addPermission(Permissions.READ_TASK);
		aEmployeesTask.addPermission(Permissions.UPDATE);
		as.saveAuthorization(aEmployeesTask);
	}
	
	private void authCustomersForTask(AuthorizationService as) {
		Authorization aCustomerTask = as.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
		aCustomerTask.setGroupId("customers");
		aCustomerTask.setResource(Resources.TASK);
		aCustomerTask.setResourceId("Task_0eleuzi");
		aCustomerTask.addPermission(Permissions.TASK_ASSIGN);
		aCustomerTask.addPermission(Permissions.TASK_WORK);
		aCustomerTask.addPermission(Permissions.READ_TASK);
		aCustomerTask.addPermission(Permissions.UPDATE);
		as.saveAuthorization(aCustomerTask);
	}
	
	private void authSittersForTask(AuthorizationService as) {
		Authorization aSittersTask = as.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
		aSittersTask.setGroupId("sitters");
		aSittersTask.setResource(Resources.TASK);
		aSittersTask.setResourceId("Task_0eleuzi");
		aSittersTask.addPermission(Permissions.TASK_WORK);
		aSittersTask.addPermission(Permissions.READ_TASK);
		aSittersTask.addPermission(Permissions.UPDATE);
		as.saveAuthorization(aSittersTask);
	}
	
	private void authMainProcess(AuthorizationService as) {
		Authorization aProcessDefinition = as.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
		Authorization aProcessInstance = as.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
		
		aProcessDefinition.setGroupId("employees");
		aProcessInstance.setGroupId("employees");
		aProcessDefinition.setResource(Resources.PROCESS_DEFINITION);
		aProcessInstance.setResource(Resources.PROCESS_INSTANCE);
		aProcessDefinition.setResourceId("Main2_Process");
		aProcessInstance.setResourceId("Main2_Process");
		aProcessDefinition.addPermission(Permissions.READ);
		aProcessInstance.addPermission(Permissions.READ);
		aProcessDefinition.addPermission(Permissions.READ_HISTORY);
		aProcessInstance.addPermission(Permissions.READ_HISTORY);
		aProcessDefinition.addPermission(Permissions.TASK_WORK);
		aProcessInstance.addPermission(Permissions.TASK_WORK);
		aProcessDefinition.addPermission(Permissions.READ_TASK);
		aProcessInstance.addPermission(Permissions.READ_TASK);
		aProcessDefinition.addPermission(Permissions.UPDATE_TASK);
		aProcessInstance.addPermission(Permissions.UPDATE_TASK);
		aProcessDefinition.addPermission(Permissions.TASK_ASSIGN);
		aProcessInstance.addPermission(Permissions.TASK_ASSIGN);
		aProcessDefinition.addPermission(Permissions.TASK_WORK);
		aProcessInstance.addPermission(Permissions.TASK_WORK);
		as.saveAuthorization(aProcessDefinition);
		as.saveAuthorization(aProcessInstance);
	}
	
	private void authSitterProcess(AuthorizationService as) {
		Authorization aProcessDefinition = as.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
		Authorization aProcessInstance = as.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
		
		aProcessDefinition.setGroupId("sitters");
		aProcessInstance.setGroupId("sitters");
		aProcessDefinition.setResource(Resources.PROCESS_DEFINITION);
		aProcessInstance.setResource(Resources.PROCESS_INSTANCE);
		aProcessDefinition.setResourceId("sitter_apply_process");
		aProcessInstance.setResourceId("sitter_apply_process");
		aProcessDefinition.addPermission(Permissions.TASK_WORK);
		aProcessInstance.addPermission(Permissions.TASK_WORK);
		as.saveAuthorization(aProcessDefinition);
		as.saveAuthorization(aProcessInstance);
	}
	
	private void authCustomerProcess(AuthorizationService as) {
		Authorization aProcessDefinition = as.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
		Authorization aProcessInstance = as.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
		
		aProcessDefinition.setGroupId("customers");
		aProcessInstance.setGroupId("customers");
		aProcessDefinition.setResource(Resources.PROCESS_DEFINITION);
		aProcessInstance.setResource(Resources.PROCESS_INSTANCE);
		aProcessDefinition.setResourceId("customer_accept_process");
		aProcessInstance.setResourceId("customer_accept_process");
		aProcessDefinition.addPermission(Permissions.TASK_WORK);
		aProcessInstance.addPermission(Permissions.TASK_WORK);
		as.saveAuthorization(aProcessDefinition);
		as.saveAuthorization(aProcessInstance);
	}
	
	private void authGlobalFilter(FilterService fs, AuthorizationService as, TaskService ts) {
		TaskQuery query = ts.createTaskQuery().taskAssigneeExpression("${currentUser()}");
		Filter filter = fs.newTaskFilter().setName("Meine Aufgaben").setQuery(query).setOwner("demo");
		fs.saveFilter(filter);
		Authorization aFilter = as.createNewAuthorization(Authorization.AUTH_TYPE_GLOBAL);
		aFilter.setResource(Resources.FILTER);
		aFilter.setResourceId(filter.getId());
		aFilter.addPermission(Permissions.READ);
		as.saveAuthorization(aFilter);
	}
	
	private void authGlobalGroupFilter(FilterService fs, AuthorizationService as, TaskService ts) {
		TaskQuery query = ts.createTaskQuery().taskCandidateGroupInExpression("${currentUserGroups()}").taskUnassigned();
		Filter filter = fs.newTaskFilter().setName("Offene Gruppenaufgaben").setQuery(query).setOwner("demo");
		fs.saveFilter(filter);
		Authorization aFilter = as.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
		aFilter.setGroupId("employees");
		aFilter.setResource(Resources.FILTER);
		aFilter.setResourceId(filter.getId());
		aFilter.addPermission(Permissions.READ);
		as.saveAuthorization(aFilter);
	}
}

