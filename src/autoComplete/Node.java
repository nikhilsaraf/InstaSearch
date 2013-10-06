/**
 * 
 */
package autoComplete;

import java.util.HashMap;

/**
 * @author Nikhil Saraf
 *
 */
public class Node {
	/**
	 * contains references to the nodes after this, indexing by character
	 */
	private HashMap<Character, Node> links;
	private String value;
	private Node suggestNode;
	private int numberOfWordsInTree;
	/**
	 * root = 0;
	 * all others are >=1
	 */
	private int level;
	
	public Node(String newValue, int level) {
		value = newValue.toLowerCase().trim();
		/*
		 * will be lazily initialized to conserve heap memory
		 * i.e. all leaf nodes will have links as null
		 */
		links = null;
		this.level = level;
		numberOfWordsInTree = isWord()?1:0;
		suggestNode = null;
	}
	
	public String search(String str, int index) {
		/*
		 * traverse character string using index recursively
		 * first through str chars,
		 * then in else statement we will predict
		 */
		if(index<str.length()) {
			if(links==null) {
				return "<not in dictionary>";
			}
			Node nextNode = links.get(str.charAt(index));
			if(nextNode!=null) {
				return nextNode.search(str, index+1);
			} else {
				return "<not in dictionary>";
			}
		} else {
			if(suggestNode!=null) {
				return suggestNode.search(str, index);
			} else if(isWord()) {
				return value;
			} else {
				return "";
			}	
		}
	}
	
	/**
	 * adds the given word, starting from the given index
	 * @param word
	 * @param index
	 * @return true if a new word was added. This will cause all ancestors to recalculate suggestNode 
	 */
	public boolean addWord(String word, int index) {
		boolean childAdded = false;
		char charToAdd = Character.toLowerCase(word.charAt(index));
		
		Node next = null;
		if(links==null) {
			links = new HashMap<Character, Node>(1);	//lazy initialization to conserve memory (all non-leaves will have initialized links!)
		} else {
			next = links.get(charToAdd);
		}
		
		if(next == null) {
			next = new Node(value + charToAdd, level+1);
			links.put(charToAdd, next);
			childAdded = true;
		}
		
		if(index+1<word.length()) {
			/*
			 * OR return boolean with childAddition return value.
			 * So if during the recursion a new element is created, everyone above will recalculate
			 */
			childAdded |= next.addWord(word, index+1);
		}
		
		/*
		 * recalculating suggestNode and numberOfWordsInTree
		 */
		if(childAdded) {
			numberOfWordsInTree = isWord()?1:0;
			
			for(Node subTree : links.values()) {
				numberOfWordsInTree += subTree.getNumberOfWords();
				if(suggestNode==null) {
					if(subTree.getNumberOfWords()>0 && value.length()>0) {
						suggestNode = subTree;
					}
				} else if(subTree.getNumberOfWords() > suggestNode.getNumberOfWords()) {
					suggestNode = subTree;
				}
			}
		}
		
		return childAdded;
	}
	
	public int getNumberOfWords() {
		return numberOfWordsInTree;
	}
	
	public String getValue() {
		return value;
	}
	
	public boolean isWord() {
		return Dictionary.hasWord(value);
	}
	
	public void printTree() {
		/*
		 * print only last character of value
		 * also append a * if it is a word
		 */
		if(value.length()>0) {
			System.out.print(value.charAt(value.length()-1));
//			System.out.print(value);
		}
		if(isWord()) {
			System.out.print("*");
		}
		
		System.out.print("\t\t" + numberOfWordsInTree);
		
		System.out.println();
		
		for(Node subTree : links.values()) {
			for(int i=0;i<level;i++) {
				System.out.print('.');
			}
			subTree.printTree();
		}
	}
}
