package com.slr;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Problem to be solved:
 * A  group of friends from the Dublin visit the chocolate shop. The shop have different kind of chocolates and the friends decide to buy as many as 3 type of chocolate each if they are available to purchase. Unfortunately, shop have a  restriction that they can not sell more than one chocolate of the same type. So the showowner come up with the following scheme: They ask each person to write down a list of up to 10 chocoloates that they enjoyed and would be happy buying. With this information, please help the showowner maximize the number of chocolates that they can sell to the group of friends.
 * Input 
 * A two-column TSV file with the first column containing the ID (just a string) of a person and the second column the ID of the chocolate that they like. Here are  input data sets of increasing sizes. 
 * person_choc_1.txt
 * Output 
 * First line contains the number of chocolates sold in aggregate with your solution. Each subsequent line should be two columns, tab separated. The first column is an ID of a person and the second column should be the ID of the chocolate that they will buy.
 * Please check your work. Note that the IDs of the output second column should be unique since a single chocolates can not be sold to two people and an ID on the first column can appear at most three times since each person can only buy up to 3 chocolates.
 *
 * @author Sebastien
 *
 */


public class ChocolateFactory {

	public static final String DEFAULT_INPUT_FILE = "resources/sample_input.txt";
	public static final String DEFAULT_OUTPUT_FILE = "resources/sample_output.txt";
	public static final Integer MAX_CHOC_PER_PERSON = 3;
	
	//Map of <ChocolateId, List of persons> storing the input wish list:
	private Map<String, List<String>> chocToPersonsWishMap= new HashMap<String, List<String>>();

	//Map of <ChocolateId, personId> storing the final assignment of chocolates:
	private Map<String, String> chocToPersonDistribMap = new HashMap<String, String>();

	public Map<String, List<String>> getChocToPersonsWishMap() {
		return chocToPersonsWishMap;
	}

	public void setChocToPersonsWishMap(Map<String, List<String>> chocToPersonsWishMap) {
		this.chocToPersonsWishMap = chocToPersonsWishMap;
	}

	public Map<String, String> getChocToPersonDistribMap() {
		return chocToPersonDistribMap;
	}

	public void setChocToPersonDistribMap(Map<String, String> chocToPersonDistribMap) {
		this.chocToPersonDistribMap = chocToPersonDistribMap;
	}
	
	
	/**
	 * Business logic: 
	 * From the list of requests for each chocolate, generates the assignment of chocolates to each person
	 * maximizing the total number of sold chocolates .
	 * @param chocToPersonsWishMap: map holding the list of persons requesting each chocolate
	 * @param chocToPersonDistribMap: map holding the final assignment of chocolate
	 */
	public void findMaximumDistribution() {
		
		//Map of <Person, nb chocolates assigned> maintaining the count of chocolates assigned to each person
		Map<String, Integer> personToNbAssignedMap = new HashMap<String, Integer>();
		
		//Sorting the map from the least requested chocolate to the most requested
		final Map<String, List<String>> chocToPersonsSortedMap = chocToPersonsWishMap.entrySet().stream()
                .sorted(Comparator.comparingInt(e->e.getValue().size()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (elt1, elt2) -> elt1, LinkedHashMap::new));
		
		// We assign the least requested chocolates first as we reduce the probability that the person have already
		//their 3 chocolates		
		Iterator<Entry<String, List<String>>> it = chocToPersonsSortedMap.entrySet().iterator();		
		while (it.hasNext() ) {
			
			Map.Entry<String, List<String>> entry = it.next();
			String choc = entry.getKey();
			List<String> persons = entry.getValue();
			
			//Check if the person has already their 3 chocolates
			//If not assign the chocolate to the person and increment counter of chocolates assigned to the person
			for (String person : persons) {
				boolean personInMap = personToNbAssignedMap.containsKey(person);
				if (!(personInMap && personToNbAssignedMap.get(person) == MAX_CHOC_PER_PERSON)) {
					//Add the chocolate assignment in the final map
					chocToPersonDistribMap.put(choc,person);
					
					//Increment counter of chocolates assigned
					if(personInMap) {
						personToNbAssignedMap.put(person,personToNbAssignedMap.get(person) + 1);
					} else {
						personToNbAssignedMap.put(person,1);
					}
				    
				}
			}			
		}		
	}
	
	public static void main(String args[]) {
				
	    //Uses the following default input files and output files if not provided in arguments:
		String outputFilePath=DEFAULT_OUTPUT_FILE;
		String inputFilePath=DEFAULT_INPUT_FILE;
		
		
		
		// Gives the ability to set the file paths in a command line argument to be able to test different files
		// Command <inputFilePath> <outputFilePath>
		if (args.length > 1) {
			inputFilePath=args[0];
			outputFilePath=args[1];
		} else if (args.length == 1) {
			inputFilePath=args[0];
		}
		
		ChocolateFactory factory = new ChocolateFactory();
		FileConverter converter = new FileConverter();
		
		//Processes input file:
		converter.loadDataFromInputFile(inputFilePath, factory.getChocToPersonsWishMap());
		//Heart of the logic is in the following function:
		factory.findMaximumDistribution();
		//Processes to output file:
		converter.saveToOutputFile(factory.getChocToPersonDistribMap(), outputFilePath);		
		
	}


	
}
	


