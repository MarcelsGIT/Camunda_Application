package de.htw.berin.camunda.gruppe08.main;

import java.util.List;


import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.FilterService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Groups;
import org.camunda.bpm.engine.filter.Filter;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.GroupQuery;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.identity.UserQuery;
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;


public class Cleaner {

	protected RepositoryService repositoryService;
	protected RuntimeService runtimeService;
	protected IdentityService identityService;
	protected FilterService filterService;
	protected AuthorizationService authorizationService;
	
	private static Cleaner c;
	private Cleaner() {}
	
	public static Cleaner getInstance() {
		if(c == null) {
			c = new Cleaner();
		}
		return c;
	}

	public void clean(ProcessEngine engine) {
		
		repositoryService = engine.getRepositoryService();
		runtimeService = engine.getRuntimeService();
		identityService = engine.getIdentityService();
		filterService = engine.getFilterService();
		authorizationService = engine.getAuthorizationService();

		// Delete all previous data in camunda
		cleanInstances();
		cleanMemberships();
		cleanUsers();
		cleanGroups();
		cleanFilters();
		cleanAuthorizations();
		
		System.out.println("deleted all.");
	}

	private void cleanInstances() {
		List<ProcessDefinition> definitions = repositoryService
				.createProcessDefinitionQuery().list();

		for (ProcessDefinition definition : definitions) {
			List<ProcessInstance> instances = runtimeService
					.createProcessInstanceQuery()
					.processDefinitionId(definition.getId()).list();
			for (ProcessInstance instance : instances) {
				runtimeService.deleteProcessInstance(instance.getId(), null);
				System.out.println("Deleted instance " + instance.getId());
			}
		}
	}

	private void cleanMemberships() {
		identityService.deleteMembership("demo", Groups.CAMUNDA_ADMIN);
		identityService.deleteMembership("demo", "employees");
		identityService.deleteMembership("demo", "customers");
		identityService.deleteMembership("demo", "sitters");
		
		identityService.deleteMembership("chunsa", "customers");
		identityService.deleteMembership("employee", "employees");
		identityService.deleteMembership("sitter01", "sitters");
		identityService.deleteMembership("sitter02", "sitters");
		identityService.deleteMembership("sitter03", "sitters");
		identityService.deleteMembership("sitter04", "sitters");
		identityService.deleteMembership("sitter05", "sitters");
		identityService.deleteMembership("sitter0sitter05", "sitters");
		identityService.deleteMembership("sitter0SitterID", "sitters");
		System.out.println("deleted");
	}

	private void cleanUsers() {
		identityService.deleteUser("demo");
		identityService.deleteUser("chunsa");
		identityService.deleteUser("employee");
		identityService.deleteUser("sitter01");
		identityService.deleteUser("sitter02");
		identityService.deleteUser("sitter03");
		identityService.deleteUser("sitter04");
		identityService.deleteUser("sitter05");
		identityService.deleteUser("sitter0sitter05");
		identityService.deleteUser("sitter0sitter0SitterID");
		identityService.deleteUser("sitter0SitterID");
	}

	private void cleanGroups() {
//		GroupQuery query = identityService.createGroupQuery();
//		
//		for(Group user : query.list()) {
//			identityService.deleteUser(user.getId());
//		}
		System.out.println("deleted");
		identityService.deleteGroup(Groups.CAMUNDA_ADMIN);
		identityService.deleteGroup("employees");
		identityService.deleteGroup("customers");
		identityService.deleteGroup("sitters");
//		identityService.deleteGroup("management-claims");
	}

	private void cleanFilters() {
		List<Filter> filters = filterService.createFilterQuery().list();
		for (Filter f : filters) {
			filterService.deleteFilter(f.getId());
		}
		System.out.println("deleted");
	}

	private void cleanAuthorizations() {
		List<Authorization> auths = authorizationService
				.createAuthorizationQuery().list();
		for (Authorization a : auths) {
			authorizationService.deleteAuthorization(a.getId());
		}
		System.out.println("deleted");
	}
}