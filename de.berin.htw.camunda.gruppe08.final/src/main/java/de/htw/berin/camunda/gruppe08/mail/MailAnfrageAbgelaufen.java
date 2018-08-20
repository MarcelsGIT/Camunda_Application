package de.htw.berin.camunda.gruppe08.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.htw.berin.camunda.gruppe08.dao.Connector;
import de.htw.berin.camunda.gruppe08.mail.mailAccess.MailAccess;

/**
 * 
 * @project:de.htw.berin.camunda.gruppe08
 * @author: Marcel
 * @created: 17.06.2018
 * @changed: 17.06.2018
 * @changeBy:
 * @description: Hier wird der Emailtext zusammen gebaut.
 * @comments:
 */
public class MailAnfrageAbgelaufen implements IMail {
	

	@Override
	public void sendMail(String mailTo, String vorname) {
		
		String mailtext = "Hallo " + vorname + ",\n" + 
		"leider müssen wir Deine Anfrage ablehnen, da wir keinen passenden Sitter gefunden haben. \n" + 
		"Viele Grüße,\n" + "\n Dein Camunda08-Team";
		
		String subject = "Absage Deiner Anfrage";
		
		MailAccess.getInstance().sendMail(mailTo, subject, mailtext);
		MailAccess.getInstance().close();
		
		
	}
	
}
