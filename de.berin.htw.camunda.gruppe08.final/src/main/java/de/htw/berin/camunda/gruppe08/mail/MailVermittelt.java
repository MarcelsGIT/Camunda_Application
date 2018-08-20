package de.htw.berin.camunda.gruppe08.mail;

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
public class MailVermittelt implements IMail {

	@Override
	public void sendMail(String mailTo, String vorname) {
		
		String mailtext = "Hallo " + vorname + ",\n" + 
		"die Vermittlung war erfolgreich !\n" + 
		"Viele Grüße,\n" + "\n Dein Camunda08-Team";
		
		String subject = "Erfolgreiche Vermittlung";
		
		MailAccess.getInstance().sendMail(mailTo, subject, mailtext);
		MailAccess.getInstance().close();
		
		
	}

}
