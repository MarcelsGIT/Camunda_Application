package de.htw.berin.camunda.gruppe08.main.taskoperations.startprocess;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;

import de.htw.berin.camunda.gruppe08.dao.data.CsvReader;
import de.htw.berin.camunda.gruppe08.domain.Kunde;
import de.htw.berin.camunda.gruppe08.domain.Sitter;

public class SendAnfrageformularAusgefuellt implements JavaDelegate{

	@Override
	public void execute(DelegateExecution de) throws Exception {
		// TODO Auto-generated method stub

		User customer = de.getProcessEngineServices().getIdentityService()
				.createUserQuery().userId
				(
						de.getProcessEngineServices().getIdentityService().getCurrentAuthentication().getUserId()
				)
				.singleResult();
		//Kunde als Referenz zum User erstellen
		Kunde kunde = new Kunde();
		kunde.setId(customer.getId());
		kunde.setFirstName(customer.getFirstName());
		kunde.setLastName(customer.getLastName());
		kunde.setEmail(customer.getEmail());
		kunde.setStreet((String)de.getVariable("Strasse_Start"));
		kunde.setPlz((String)de.getVariable("PLZ_Start"));
		kunde.setCity((String)de.getVariable("Ort_Start"));
		//Kundenanfrage erstellen
		//String pet, String petfood, String keeping, Boolean morePets, LocalDate startDate, LocalDate endDate 
		kunde.createAnfrage((String)de.getVariable("Haustier_Start"), (String)de.getVariable("Futter_Start"), 
							(String)de.getVariable("Haltungsart_Start"), (boolean)de.getVariable("andereTiere_Start"), 
							this.castToLocalDate((Date)de.getVariable("startDatum_Start")), 
							this.castToLocalDate((Date)de.getVariable("endDatum_Start")));

		//Variablen in Prozessinstanz einfügen
		de.setVariable("Anfragender", kunde);
		de.setVariable("SitterMap", this.createSitter());
		de.setVariable("Start_InstanceID", de.getProcessInstanceId());
		de.setVariable("startDatum_String", this.castDateToString((Date)de.getVariable("startDatum_Start")));
		de.setVariable("endDatum_String", this.castDateToString((Date)de.getVariable("endDatum_Start")));
		de.setVariable("Dauer", this.countDays((Date)de.getVariable("startDatum_Start"), (Date)de.getVariable("endDatum_Start")));
		de.setVariable("andereTiere_String", this.boolToString((boolean)de.getVariable("andereTiere_Start")));
		System.out.println("Prozess erfolgreich gestartet...");
		//Prozessinstanz für Main_Prozess starten
		ProcessInstance main = de.getProcessEngineServices().getRuntimeService().startProcessInstanceByMessage("start_main2", de.getVariables());
	}
	
	private String boolToString(boolean bool) {
		if(bool) {
			return "ja";
		}else {
			return "nein";
		}
	}
	
	private HashMap<String, Sitter> createSitter(){
		HashMap<String, Sitter> sitters = null;
		try {
		 sitters = CsvReader.getInstance().readSitterFile();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return sitters;
	}
	
	private LocalDate castToLocalDate(Date date) {
		LocalDate local = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
		return local;
	}
	
	private String castDateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");
		String casted = sdf.format(date);
		return casted;
	}
	
	private long countDays(Date date, Date date2) {
		TimeUnit tu;
		System.out.println(date);
		long diff = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");
		String d1 = sdf.format(date);
		String d2 = sdf.format(date2);
		try{diff = sdf.parse(d2).getTime() - sdf.parse(d1).getTime();}catch(Exception e) {e.printStackTrace();}
		diff = TimeUnit.DAYS.convert(diff, TimeUnit.MICROSECONDS);
		System.out.println(diff);
		return diff;
		
	}

}
