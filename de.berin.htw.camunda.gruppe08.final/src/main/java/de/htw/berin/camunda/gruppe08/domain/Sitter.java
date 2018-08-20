package de.htw.berin.camunda.gruppe08.domain;


import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;

/** @project:de.htw.berin.camunda.gruppe08
* @author: Marcel
* @created: 17.06.2018
* @changed: 17.06.2018
* @changeBy:
* @description: Bildet Datenbankobjekte ab. Attribute müssen den Datenbankeinträgen entsprechen.
* @comments:
*/
public class Sitter extends UserEntity {
	
	//Überlegen wie man enum in city, pet, petfood, keeping macht -> Array ?
	/**
	 * 
	 */
	private static final long serialVersionUID = -1251150926412420270L;
	
	private String street;
	private String plz;
	private String city;
	private String pet;
	private String petfood;
	private String keeping;
	private Boolean morePets;
	private long pricePerDay;
	
	
	public Sitter(String firstname, String lastname, String street, String plz, String city, String pet, String petfood,String keeping, Boolean morePets, String Id){
		super.setId(Id);
		super.setFirstName(firstname);
		super.setLastName(lastname);
		this.street = street;
		this.plz = plz;
		this.city = city;
		this.pet = pet;
		this.petfood = petfood;
		this.keeping = keeping;
		this.morePets = morePets;
		
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


	public long getPricePerDay() {
		return pricePerDay;
	}


	public void setPricePerDay(long pricePerDay) {
		this.pricePerDay = pricePerDay;
	}



		
}
	
	


