//Author: Aaron Cervantes
/*Description: Program will receive multiple lines of input from the console 
 * the last line needs to end in .eot.  After that the program will get 
 * data from multiple online sites that store local used items for sales.
 * Then the program will display the data on the console.
*/
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class main {
	
	private static final String VERSION = "1.0.1";	//program version
	
	//LIST OF ALL FIRST LINE COMMANDS  
	private static final String HELP = "-help";	//parameter needed to use help
	private static final String SYNOPSIS = "-synopsis";	//parameter needed to use synopsis
	private static final String BAT = "-bat";
	private static final String BAT_TWO = "-bat=";
			
	//LIST OF ALL SECOND LINE COMMANDS
	private static final String SEARCH = "-search";
	private static final String SORT ="-sort"; 
	private static final String WEBSITE = "-web";
	
	//List of website names
	private static final String CRAIGLIST = "craiglist";
	private static final String FIVEMILES = "5miles";
	private static final String OFFERUP = "offerup";
	
	public static void main(String[] args) throws Exception
	{
		int lines = 2;	//holds the amount of lines to be read
		String input[] = new String[3];	//holds 3 lines of input

		readLines(input, lines);	//read user input
		
		//Check if correct format was typed
		if(input[1].equals(".eot") == false)	//checks end of line parameter	
			System.out.println("ERROR!: Incorrect input, type " + HELP + " for help." 
					+"\nProgram will now terminate.");
		else 
		{
			//FIRSTLINE
			//look in first line see if parameters where typed
			if((input[0].indexOf(HELP) != -1))	
				help();

			//will print simple program information
			if(input[0].indexOf(SYNOPSIS) != -1)
				sypnosis();


			if(input[0].equals(BAT) || input[0].equals(BAT_TWO))
			{
				System.out.println(BAT + " was typed.");
			}
			
			//SECOND LINE
			if(input[0].indexOf(WEBSITE) != -1)
			{
				
				if(input[0].indexOf(SEARCH) != -1)
				{
					ArrayList<String> itemList = new ArrayList<String>();
					String website = commandIgnore(input[1], WEBSITE);
					
					//websites that can be searched
					if(website.indexOf(CRAIGLIST) != -1)
						itemList.addAll(searchCraiglist(input[1]));
					else if(website.indexOf(OFFERUP) != -1)
						itemList.addAll(searchOfferUp(input[1]));
					else if(website.indexOf(FIVEMILES) != -1)
						itemList.addAll(searchFiveMiles(input[1]));
					
					
					//output list of items
					for(int i = 0; i < itemList.size();i++)
						System.out.println(itemList.get(i));
				}
				else
				{
					System.out.println("ERROR: search command must be included with "+ WEBSITE +" command!"
							+ "\nProgram will now terminate.");
				}
			}
			else if(input[0].indexOf(SEARCH) != -1)
			{
				ArrayList<String> itemList = new ArrayList<String>();
				
				itemList.addAll(searchCraiglist(input[1]));
				itemList.addAll(searchOfferUp(input[1]));
				itemList.addAll(searchFiveMiles(input[1]));
				
				//output list of items
				for(int i = 0; i < itemList.size();i++)
					System.out.println(itemList.get(i));
			}
		}
	}
	
	//PRIVATE METHODS*********************************************************
	//reads in string lines from user to an array
	private static void readLines(String stringArray[], int length)
	{
		Scanner scan = new Scanner(System.in);
		
		//read all lines
		for(int i = 0; i < length; i++)
			stringArray[i] = scan.nextLine();
	}

	//will print information about the program
	private static void help() 
	{
		int space = 15;	//number of spaces in formatting
		
		System.out.println("This program will return all the items for "
				+ "sale across multiple websites that sell used goods."
				+ "\nThe following websites are currently supported: " 
				+ "\n-www.craiglist.com"
				+ "\n-www.offerup.com" 
				+ "\n-www.5miles.com");
		
		System.out.println
		(
				"\nThe following parameters are supported:"
				+ "\n" + HELP
				+ "\n" + BAT + "& " + BAT_TWO
				+ "\n" + SEARCH + " item name ('Example: -search car')"
				+ "\n" + WEBSITE + " website name (websites:"
					+ CRAIGLIST + ", " + FIVEMILES + ", " + OFFERUP  +")"
				+ "\n" + "To select a website ('Example: -search car -web craiglist')"
						+ " any order is fine ('Example: -web craiglist -search car') also works"
		);
		
		System.out.printf
		(
				"%-" + space + "s: %s" + "%-" + space + "s: %s" 
				+ "%-" + space + "s: %s" +"%-" + space + "s: %s\n" 
				, "\nVersion" , VERSION 
				, "\nDependencies" , "'Jsoup'"
				, "\nAuthor" , "Aaron Cervantes" 
				, "\nContact" , "bluckq@gmail.com"
		);
	}
	
	//brief sentence of what the program does
	private static void sypnosis()
	{
		System.out.println("\nLists used items for sale from multiple websites.");
	}
	
	//searches craiglist for items
	private static ArrayList<String> searchCraiglist(String searchString)
	{
		Craiglist craiglist = new Craiglist();
		
		return craiglist.search(commandIgnore(searchString, SEARCH));
	}
	
	private static ArrayList<String> searchOfferUp(String searchString)
	{
		OfferUp offerUp = new OfferUp();
		
		return offerUp.search(commandIgnore(searchString, SEARCH));
	}
	
	private static ArrayList<String> searchLetGo(String searchString)
	{
		LetGo letGo = new LetGo();
		
		return letGo.search(commandIgnore(searchString, SEARCH));
	}
	
	private static ArrayList<String> searchFiveMiles(String searchString)
	{
		FiveMiles fiveMiles = new FiveMiles();
		
		return fiveMiles.search(commandIgnore(searchString, SEARCH));
	}
	
	
	//this allows reading of the '-' character which is required before command
	//will get all words before next command 
	private static String commandIgnore(String lineString , String commandString)
	{
		//StringBuilder better for appending strings
		StringBuilder wordsSB = new StringBuilder();
		
		//store words in array after every space
		String[] inputArray = lineString.split(" ");
		//checks every word for '-'
		for(int i = 0; i < inputArray.length;i++)
			//checks for the command for commandString and gets everything in front of it
			if(inputArray[i].equals(commandString))
				//ignore the search command string and read everything after
				//keep reading words until the '-' is detected at beginning of word
				for(int j = i+1; j < inputArray.length;j++)
				{
					//checks if the '-' is in the beginning of the word
					//if it is then stop the loop
					if(inputArray[j].charAt(0) == '-')
						break;
					
					wordsSB.append(inputArray[j] + " ");
				}
		
		return wordsSB.toString();
	}
}
