/**
 * 
 */
package autoComplete;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author Nikhil Saraf
 *
 */
public class Dictionary {
	private static HashSet<String> dictionary = new HashSet<String>();
	public static String dictionaryFileName = "dictionary2.txt";
	public static String homeDir = "/Users/nikhilsaraf/Documents/EclipseWorkspaces/InterviewWorkspace/Google Interview Practice Fall 2011/src/autoComplete/";
	private static BufferedReader reader;
	
	private Dictionary() {
	}

	public static void init() throws IOException {
		try {
			reader = new BufferedReader(new FileReader(homeDir + dictionaryFileName));
		} catch (FileNotFoundException e) {
			System.out.println("File was not found.");
			System.out.println("Please enter full path name for dictionary in quotes");
			System.out.println("Make sure '" + dictionaryFileName + "' exists");
			/*
			 * exit as error
			 */
			System.exit(1);
		}
		
		String word;
		
		AutoComplete.log("Adding words from file to dictionary...");
		
		while( (word=reader.readLine()) !=null) {
			for(int i=0;i<AutoComplete.WORD_INTERVAL_TO_SKIP && (reader.readLine() !=null);i++) {
				// consume line
			}
			Dictionary.addWord(word);
		}
		
		AutoComplete.log("...Finished adding words from file to dictionary");
		AutoComplete.log("");
		AutoComplete.log("Words in Dictionary: " + getSize());
		AutoComplete.log("");
	}
	
	public static int getSize() {
		return dictionary.size();
	}
	
	public static void addWord(String word) {
		dictionary.add(word.toLowerCase());
	}
	
	public static boolean hasWord(String word) {
		return dictionary.contains(word.toLowerCase());
	}

	public static Iterator<String> getIterator() {
		return dictionary.iterator();
	}
}
