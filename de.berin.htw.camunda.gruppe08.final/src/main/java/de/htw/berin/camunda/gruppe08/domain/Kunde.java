package de.htw.berin.camunda.gruppe08.domain;


import java.time.LocalDate;

import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;

public class Kunde extends UserEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6095786494139391153L;

	
	private String street;
	private String plz;
	private String city;
	Anfrage anfrage = null; 
	
	public void createAnfrage(String pet, String petfood, String keeping, Boolean morePets, LocalDate startDate, LocalDate endDate) {
		if(anfrage == null) {
			anfrage = new Anfrage(pet, petfood, keeping, morePets, startDate, endDate);
		}else {
			throw new RuntimeException("Kunde hat bereits eine Anfrage gestellt. Nur eine Anfrage möglich");
		}
	}
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Anfrage getAnfrage() {
		return anfrage;
	}

	public void setAnfragen(Anfrage anfragen) {
		this.anfrage = anfragen;
	}


	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
