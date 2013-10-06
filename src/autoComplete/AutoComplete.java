package autoComplete;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 * @author Nikhil Saraf
 *
 */
public class AutoComplete {
	private BufferedReader cin;
	private static Node head;
	
	private static final boolean debugOn = true;
	private static final boolean debugPrintTree = false;
	private static final boolean debugShowWordsAdded = false;
	public static final int WORD_INTERVAL_TO_SKIP = 1;
	
	public AutoComplete() {
		cin = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public static void log(String logString) {
		if(debugOn) {
			System.out.println(logString);
		}
	}	

	private void initTree() {
		log("Populating Tree with dictionary...");
		
		Iterator<String> dictionaryIter = Dictionary.getIterator();
		
		String word;
		int i=0;
		
		try {
			while(dictionaryIter.hasNext()) {
				word = dictionaryIter.next();
				head.addWord(word, 0);
				i++;
				if(debugShowWordsAdded) {
					log("Added word #" + i + ": " + word);
				}
			}
		} catch (java.lang.OutOfMemoryError memE) {
			log("Words Added: " + i);
			throw memE;
		}
		
		log("Finished populating Tree with dictionary");
		log("Words added: " + i);
		log("");
	}
	
	public void init() throws IOException {
		Dictionary.init();
		
		head = new Node("", 0);
		
		initTree();
	
		if(debugOn && debugPrintTree) {
			log("Printing Tree:-\n");
			
			head.printTree();
			
			log("");
			log("Finished printing Tree");
		}
		log("\n");
	}
	
	public void run() {
		while(true) {
			System.out.print("Enter the value to be auto-completed:\t");
			String input = null;
			try {
				input = cin.readLine();			
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
			System.out.println(head.search(input, 0));
			System.out.println();
		}	
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length==1) {
			System.out.println("\n");
			Dictionary.dictionaryFileName = args[0];
			Dictionary.homeDir = "";
			System.out.println("User has specified custom dictionary file:\n" + Dictionary.dictionaryFileName);
			
			System.out.println("\n");
		}
		
		AutoComplete auto = new AutoComplete();
		try {
			auto.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		auto.run();
		
		SWTView view = SWTView.getInstance();
		view.init(head);
		view.run();
	}
}
