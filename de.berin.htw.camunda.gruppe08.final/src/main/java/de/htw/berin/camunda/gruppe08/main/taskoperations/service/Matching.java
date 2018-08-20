/**
 * 
 */
package de.htw.berin.camunda.gruppe08.main.taskoperations.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.camunda.bpm.engine.identity.User;

import de.htw.berin.camunda.gruppe08.domain.Kunde;
import de.htw.berin.camunda.gruppe08.domain.Sitter;

/**
 * @project:de.htw.berin.camunda.gruppe08
 * @author: Marcel
 * @created: 17.06.2018
 * @changed: 17.06.2018
 * @changeBy:
 * @description: Hier findet der Matchingprozess statt
 * @comments:
 */
public class Matching {

	private static Matching m;
	
	
	private Matching() {}
	
	public static Matching getInstance() {
		if(m == null) {
			m = new Matching();
		}return m;
	}
	
	public HashMap<String, Sitter> match(HashMap<String, Sitter> sitters, Kunde customer)throws NullPointerException{

		HashMap<String, Sitter>  matchedSitters = this.compareAndStore(sitters, customer);
		if(matchedSitters == null) { throw new NullPointerException(); }
		System.out.println(customer.getFirstName());
		return matchedSitters;
	}
	
	private HashMap<String, Sitter> compareAndStore(HashMap<String, Sitter> sitters, Kunde customer)throws NullPointerException{
		HashMap<String, Sitter> matchedSitters = null;
		
		for(Sitter sitter : sitters.values()) {
			Integer comparisonResult = 0;
			System.out.println(sitter.getId());
			comparisonResult += sitter.getCity().compareToIgnoreCase(customer.getCity());
			System.out.println(sitter.getCity() + " " + customer.getCity());
			comparisonResult += this.compareKeeping(sitter.getKeeping(), customer.getAnfrage().getKeeping());
			System.out.println(sitter.getKeeping() + " " + customer.getAnfrage().getKeeping());
			comparisonResult += sitter.getMorePets().compareTo(customer.getAnfrage().getMorePets());
			System.out.println(sitter.getMorePets() + " " + customer.getAnfrage().getMorePets());
			comparisonResult += this.comparePetfood(sitter.getPetfood(), customer.getAnfrage().getPetfood());
			System.out.println(sitter.getPetfood() + " " + customer.getAnfrage().getPetfood());
			comparisonResult += this.comparePetValue(sitter.getPet(), customer.getAnfrage().getPet());
			System.out.println(comparisonResult);
			if(comparisonResult == 0) {
				if(matchedSitters == null) {
					matchedSitters = new HashMap<String, Sitter>();
				}
				matchedSitters.put(sitter.getId(), sitter);
				System.out.println("Matched");
			}
		}
		return matchedSitters;
	}
	
	private Integer comparePetValue(String sitterPet, String customerPet)throws NullPointerException {
		Integer comparisonResult = -1000; 
		String[] sitterPets = sitterPet.split(",");
		for(String pet : sitterPets) {
			if(pet.trim().compareToIgnoreCase(customerPet.trim()) == 0) {
				comparisonResult = 0;
				break;
			}
		}
		return comparisonResult;
		
	}
	
	private Integer comparePetfood(String sitterPetfood, String customerPetfood) {
		Integer comparisonResult = -1000; 
		String[] sitterPetfoods = sitterPetfood.split(",");
		for(String food : sitterPetfoods) {
			if(food.trim().compareToIgnoreCase(customerPetfood.trim()) == 0) {
				comparisonResult = 0;
				break;
			}
		}
		return comparisonResult;
	}
	
	private Integer compareKeeping(String sitterKeeping, String customerKeeping) {
		Integer comparisonResult = -1000; 
		String[] sitterPetfoods = sitterKeeping.split(",");
		for(String keep : sitterPetfoods) {
			if(keep.trim().compareToIgnoreCase(customerKeeping.trim()) == 0) {
				comparisonResult = 0;
				break;
			}
		}
		return comparisonResult;
	}
}
