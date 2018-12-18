import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LetGo extends WebPage
{
	LetGo()
	{
	super("https://us.letgo.com");
	}
	
	LetGo(String newURL)
	{
		super(newURL);
	}
	
	public ArrayList<String> search(String newSearch)
	{
		return search(newSearch, " ");
	}
	
	public ArrayList<String> search(String newSearch, String newSort)
	{
		ArrayList<String> itemList = new ArrayList<String>();
		Document letGoDocument;
		
		newSort = sort(newSort);
		
		try
		{
			StringBuilder urlSearch = new StringBuilder();
			
			urlSearch.append(url);
			urlSearch.append("?q=");
			urlSearch.append(newSearch);
			
			connection = Jsoup.connect(urlSearch.toString());
			
			letGoDocument = connection.get();
			
			//get only the elements from html doc that store item results
			Elements data = letGoDocument.select("ul.rows");
			
			//get all details from each elements and store in arraylist
			for(Element link : data.select("li"))
			{
				//get title,price, and links for item results
				final String TITLE = link.select("a.result-title.hdrlnk").text();
				final String PRICE = link.select("a span.result-price").text();
			//	final String LINKS = link.select("a[href]").attr("href");
				
				//store all details in single string
				String itemDetails = TITLE + "| price: " + PRICE 
						+ "\n" ;//+ "link: " + LINKS + "\n";
				
				itemList.add(itemDetails);	//add string to arraylist	
			}
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return itemList;
	}
	
	private String sort(String newSort)
	{
		return null;
	}
}


