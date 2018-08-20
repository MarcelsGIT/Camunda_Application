/**
 * 
 */
package de.htw.berin.camunda.gruppe08.main.taskoperations.service;

import org.camunda.bpm.engine.identity.User;

import de.htw.berin.camunda.gruppe08.domain.Sitter;

/**
 * @project:de.htw.berin.camunda.gruppe08
 * @author: Marcel
 * @created: 17.06.2018
 * @changed: 17.06.2018
 * @changeBy:
 * @description: hier findet der Matchingprozess statt
 * @comments:
 */
public class Bewertung {
	
	private static Bewertung b;
	
	private Bewertung() {}
	
	public static Bewertung getInstance() {
		if(b == null) {
			b = new Bewertung();
		}
		return b;
	}
	
	public String priceEvalutaion(Sitter sitter) {
		String[] values = {"Rückraum","Mittelfeld","Stuermer"};
		Integer score = 0;
		
		if(sitter.getPricePerDay() <= 20) {
			score = 2;
		}else if(sitter.getPricePerDay() > 20 && sitter.getPricePerDay() <= 50) {
			score = 1;
		}
		return values[score];
	}
}
