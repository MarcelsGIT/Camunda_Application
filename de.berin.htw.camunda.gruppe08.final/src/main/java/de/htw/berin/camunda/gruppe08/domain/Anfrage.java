package de.htw.berin.camunda.gruppe08.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * @project:de.htw.berin.camunda.gruppe08
 * @author: Marcel
 * @created: 17.06.2018
 * @changed: 17.06.2018
 * @changeBy:
 * @description: Bildet Datenbankobjekte ab. Attribute müssen den Datenbankeinträgen entsprechen.
 * @comments:
 */
public class Anfrage implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7712482442463348078L;
	
	String pet;
	String petfood;
	String keeping;
	Boolean morePets;
	LocalDate startDate;
	LocalDate endDate;
	
	
	public Anfrage(String pet, String petfood, String keeping, Boolean morePets, LocalDate startDate, LocalDate endDate){
		
		this.pet = pet;
		this.petfood = petfood;
		this.keeping = keeping;
		this.morePets = morePets;
		this.startDate = startDate;
		this.endDate = endDate;
		
	}


	public String getPet() {
		return pet;
	}


	public void setPet(String pet) {
		this.pet = pet;
	}


	public String getPetfood() {
		return petfood;
	}


	public void setPetfood(String petfood) {
		this.petfood = petfood;
	}


	public String getKeeping() {
		return keeping;
	}


	public void setKeeping(String keeping) {
		this.keeping = keeping;
	}


	public Boolean getMorePets() {
		return morePets;
	}


	public void setMorePets(Boolean morePets) {
		this.morePets = morePets;
	}


	public LocalDate getStartDate() {
		return startDate;
	}


	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}


	public LocalDate getEndDate() {
		return endDate;
	}


	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	

}

