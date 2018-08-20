/**
 * 
 */
package de.htw.berin.camunda.gruppe08.mail.mailAccess;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.htw.berin.camunda.gruppe08.dao.dbAccess.DbAccess;

/**
 * @project:de.htw.berin.camunda.gruppe08
 * @author: Marcel
 * @created: 17.06.2018
 * @changed: 17.06.2018
 * @changeBy:
 * @description: Stellt die Verbindung zum MailingClient her. Verbindung wird zu Beginn eines Tasks aufgerufen und 
 * 				 am Ende geschlossen.
 * @comments:
 */
public class MailAccess {
	
	private static MailAccess ma;
	private MultiPartEmail email;
	
	
	public static MailAccess getInstance() {
		if(ma == null) {
			ma = new MailAccess();
			ma.establishMailConnection();
		}
		return ma;
	}
	
	private void establishMailConnection() {
		try {
			this.email = new MultiPartEmail();
			this.email.setCharset(IMailing.charset);
			this.email.setSSL(IMailing.ssl);
			this.email.setSmtpPort(IMailing.smtpPort);
			this.email.setHostName(IMailing.hostName);
			this.email.setAuthentication(IMailing.user, IMailing.password);
			this.email.setFrom(IMailing.from);

		}catch(EmailException e) {
			e.printStackTrace();
			this.close();
		}
	}
	
	public void sendMail(String sendTo, String subject, String mailtext) {
		try {
			this.email.addTo(sendTo);
			this.email.setSubject(subject);
			this.email.setMsg(mailtext);
			this.email.send();

		}catch(EmailException e) {
			e.printStackTrace();
			this.close();
		}
		
	}
	
	public void close() {
		ma = null;
	}
	

}
