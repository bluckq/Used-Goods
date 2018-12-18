import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OfferUp extends WebPage
{
	OfferUp()
	{
		super("https://offerup.com/search/");		
	}
	
	@Override
	public ArrayList<String> search(String newSearch)
	{
		return search(newSearch, " ");
	}
	
	public ArrayList<String> search(String newSearch, String newSort)
	{
		//list will store the result of searched items
		ArrayList<String> itemList = new ArrayList<String>();
		Document offerUpDocument;
		
		newSort = sort(newSort);	//get proper sorting keyword
		
		try
		{
			StringBuilder urlSearch = new StringBuilder();
			
			urlSearch.append(url);
			urlSearch.append("?q=");
			urlSearch.append(newSearch);
			
			connection = Jsoup.connect(urlSearch.toString());
			
			offerUpDocument = connection.get();
			
			Elements data = offerUpDocument.select("div[id=react-container]");
			
			for(Element link : data.select("a._109rpto.db-item-tile"))//_1g9xn5a"))
			{
				final String TITLE = link.select("span._1t0lcm64._y9ev9r").text();
				final String PRICE = link.select("span._1hwuc5f4").text();
				final String LINK = link.select("a[href]").attr("href");
				
				String itemDetails = TITLE + "| price: " + PRICE 
						+ "\n" + "link: " + "https://offerup.com" + LINK + "\n";
				
				itemList.add(itemDetails);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return itemList;
	}
	
	public String sort(String newSort)
	{
		
		return null;
	}
}
