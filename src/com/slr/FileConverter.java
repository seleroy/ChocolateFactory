package com.slr;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileConverter {
	
	public static final String CSV_SEPARATOR = "\t";
	
	/**
	 * Adds an element to a map of (chocolate->persons requesting the chocolate)
	 * from a text line
	 * @param line: Line of text to be parsed
	 * @param chocToPersonsWishMap: map in which the element must be added
	 */
	public void loadMapFromLine(List<String> line,  Map<String, List<String>> chocToPersonsWishMap) {
		
		if (line.size() > 1) {
			String personId = line.get(0).trim();
			String chocolateId = line.get(1).trim();
			
			//Add chocolate to the existing list or create a new element of map with it:
			List<String> persons = chocToPersonsWishMap.get(chocolateId);			
			if (persons != null) {
				persons.add(personId);
			} else {
				persons = new ArrayList<String>();
				persons.add(personId);
				chocToPersonsWishMap.put(chocolateId, persons);
			}
		}		
	}
	
	/**
	 * Loads a tab delimited csv file and stores each line and add the parsed element to a map
	 * @param filePath: absolute file path to the input csv file
	 * @param map: map in which the data from the file are loaded
	 */
	public void loadDataFromInputFile(String filePath,  Map<String, List<String>> chocToPersonsWishMap) {
		try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
			List<List<String>> values = lines.map(csvline -> Arrays.asList(csvline.split(CSV_SEPARATOR))).collect(Collectors.toList());
			values.forEach(value -> loadMapFromLine(value, chocToPersonsWishMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Saves the final assignment of chocolate->person into a csv file
	 * @param chocToPersonDistribMap: map holding the final assignment of chocolates
	 * @param filePath: absolute file path to the input csv file
	 */
	public void saveToOutputFile(Map<String, String> chocToPersonDistribMap, String filePath) {
		FileWriter fWriter;
		try {
			fWriter = new FileWriter(filePath);
			BufferedWriter bufWriter = new BufferedWriter(fWriter);
		
			//First line is meant to store the amount of chocolates:
			bufWriter.write(Integer.toString(chocToPersonDistribMap.size()));
			bufWriter.newLine();

			//Then we iterate on the map and print the personId and ChocolateId separated by a tab
			Iterator<Entry<String, String>> it = chocToPersonDistribMap.entrySet().iterator();

			while (it.hasNext() ) {
				Map.Entry<String, String> pair = it.next();
				bufWriter.write(pair.getValue() + CSV_SEPARATOR + pair.getKey());
				bufWriter.newLine();
			}
			
			bufWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
