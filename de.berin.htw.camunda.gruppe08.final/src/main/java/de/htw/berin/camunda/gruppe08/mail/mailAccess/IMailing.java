package de.htw.berin.camunda.gruppe08.mail.mailAccess;

/**
 * @project:de.htw.berin.camunda.gruppe08
 * @author: Marcel
 * @created: 17.06.2018
 * @changed: 17.06.2018
 * @changeBy:
 * @description:
 * @comments:
 */
public interface IMailing {
	
	final Integer smtpPort = 465;
	final String hostName = "smtp.gmail.com";
	final String user = "camundagruppe08@gmail.com";
	final String password = "projektarbeit";
	final String from = "camundagruppe08@gmail.com";
	final String charset = "utf-8";
	final boolean ssl = true;
}
