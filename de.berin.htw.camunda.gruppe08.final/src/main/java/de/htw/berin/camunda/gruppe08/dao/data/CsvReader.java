package de.htw.berin.camunda.gruppe08.dao.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import de.htw.berin.camunda.gruppe08.domain.Sitter;

public class CsvReader {
	
	String data[];
	private  static CsvReader r;
	
	private CsvReader() {}
	
	public static CsvReader getInstance() {
		if(r == null) {
			r = new CsvReader();
		}
		return r;
	}
	public HashMap<String, Sitter> readSitterFile() throws IOException, Exception  {
		 Path currentPath = Paths.get("");
		 
		 String path = currentPath.toAbsolutePath().toString();
		 File file;

		
		String currentLine = "";
		String newFileName = path + "\\SitterDB.csv";
		System.out.println(newFileName);
		file = new File(newFileName);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		HashMap<String, Sitter> sitterDB = null;
		while ((currentLine = reader.readLine()) != null) {

			data = currentLine.split(";");
			Boolean morePets = false;
			
			for (int i = 0; i < data.length; i++) {
				if (data[4].equalsIgnoreCase("ja")) {
					morePets = true;
				}

			}

			if (data.length != 10) {
				throw new Exception("In Zeile" + currentLine + " ist ein Fehler aufgetreten.");
			}
		
			if(sitterDB == null) {	
				sitterDB = new HashMap<String, Sitter>();
			}	
				sitterDB.put("sitter0" + data[9], new Sitter(data[0], data[1], data[7], data[6], data[5],data[2], data[3], data[8],morePets,"sitter0" + data[9]));
				
		

		} 
		reader.close();
		return sitterDB;
	}

}
