import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Craiglist extends WebPage
{
	//CONSTRUCTORS************************************************
	Craiglist()
	{
		super("https://inlandempire.craigslist.org/search/");
	}
	
	Craiglist(String newURL)
	{
		super(newURL);
	}
	
	//PUBLIC METHODS************************************************
	//Overloaded method if no sorting value is entered defaults to relevant
	public ArrayList<String> search(String newSearch)
	{
		return search(newSearch, "date");
	}
	
	//searches Craiglist and returns: title, price, and link in an ArrayList
	//Takes in word needed for search and sorting method
	public ArrayList<String> search(String newSearch, String newSort)
	{
		//list will store the results of searched items
		ArrayList<String> itemList = new ArrayList<String>();
		Document craiglistDocument;
		
		newSort = sort(newSort); //get proper sorting keyword
	
		try 
		{
			//simulates using search bar in craiglist
			connection.timeout(3000)
			.data("action", "/search/")
			.data("clicked", "1")
			.data("sort", newSort)	
			.data("query", newSearch)
			.method(Connection.Method.GET)
			.userAgent(userAgent)
			.execute();
			
			//get connection html content to the document
			craiglistDocument = connection.get();
			
			//get only the elements from html doc that store item results
			Elements data = craiglistDocument.select("ul.rows");
			
			//get all details from each elements and store in arraylist
			for(Element link : data.select("li"))
			{
				//get title,price, and links for item results
				final String TITLE = link.select("a.result-title.hdrlnk").text();
				final String PRICE = link.select("a span.result-price").text();
				final String LINKS = link.select("a[href]").attr("href");
				
				//store all details in single string
				String itemDetails = TITLE + "| price: " + PRICE 
						+ "\n" + "link: " + LINKS + "\n";
				
				itemList.add(itemDetails);	//add string to arraylist	
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return itemList;
	}
	
	//PRIVATE METHODS*********************************************
	//converts sorting words to proper craiglist sorting keywords
	//called by search method
	private String sort(String newSort)
	{
		//Sorting keywords from craiglist: 'rel','date',?,?
		if(newSort.equals("newest"))
			newSort = "date";
		else if(newSort.equals("relevant"))
			newSort = "rel";
		else
			newSort = "rel";
			
		return newSort;
	}
}