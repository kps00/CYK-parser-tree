package parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import logic.Rule;

public class Loader {
	public static List<Rule> loadRules() throws IOException{
		List<Rule> rules = new ArrayList<Rule>();
		BufferedReader CSVFile = new BufferedReader(new FileReader("rules.csv"));
			  String dataRow = CSVFile.readLine(); 
			  while (dataRow != null){
				  if(dataRow.matches("^.+->.+")){
						String[] rule = dataRow.split("->");
						String[] parts = rule[1].split(";");
						rules.add(new Rule(rule[0],parts));
						System.out.println("[OK][Loading][Rule]" + dataRow); 					  
				  }
				  dataRow = CSVFile.readLine();
			  }
			  CSVFile.close();	
		CSVFile = new BufferedReader(new FileReader("words.csv"));
				  dataRow = CSVFile.readLine(); 
				  while (dataRow != null){
					  if(dataRow.matches("^.+->.+")){
							String[] rule = dataRow.split("->");
							String[] parts = rule[1].split(";");
							rules.add(new Rule(rule[0],parts));
							System.out.println("[OK][Loading][Word]" + dataRow);
					  }
					  dataRow = CSVFile.readLine(); 
				  }
				  CSVFile.close();		  
			  
		return rules;
	}
	
	public static List<Rule> loadRulesProb() throws IOException{
		List<Rule> rules = new ArrayList<Rule>();
		BufferedReader CSVFile = new BufferedReader(new FileReader("rules.csv"));
			  String dataRow = CSVFile.readLine(); 
			  while (dataRow != null){
				  if(dataRow.matches("^.+->.+[0-9]")){
						String[] rule = dataRow.split("->");
						String[] data = rule[1].split("-");
						double p = Double.parseDouble(data[1]);
						String[] parts = data[0].split(";");
						rules.add(new Rule(rule[0],parts,p));
						System.out.println("[OK][Loading][Rule]" + dataRow); 					  
				  }
				  else{
					  if(dataRow.matches("^.+->.+")){
							String[] rule = dataRow.split("->");
							String[] parts = rule[1].split(";");
							rules.add(new Rule(rule[0],parts));
							System.out.println("[OK][Loading][Rule]" + dataRow); 					  
					  }
				  }
				  dataRow = CSVFile.readLine();
			  }
			  CSVFile.close();	
		CSVFile = new BufferedReader(new FileReader("words.csv"));
				  dataRow = CSVFile.readLine(); 
				  while (dataRow != null){
					  if(dataRow.matches("^.+->.+[0-9]")){
							String[] rule = dataRow.split("->");
							String[] data = rule[1].split("-");
							double p = Double.parseDouble(data[1]);
							String[] parts = data[0].split(";");
							rules.add(new Rule(rule[0],parts,p));
							System.out.println("[OK][Loading][Rule]" + dataRow); 					  
					  }
					  else{					  
						  if(dataRow.matches("^.+->.+")){
								String[] rule = dataRow.split("->");
								String[] parts = rule[1].split(";");
								rules.add(new Rule(rule[0],parts));
								System.out.println("[OK][Loading][Word]" + dataRow);
						  }
					  }
					  dataRow = CSVFile.readLine(); 
				  }
				  CSVFile.close();		  
			  
		return rules;
	}
}
