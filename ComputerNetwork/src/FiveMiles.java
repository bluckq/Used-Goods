import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FiveMiles extends WebPage
{

	FiveMiles()
	{
		super("https://www.5miles.com/cq/pomona_ca/");
	}
	
	@Override
	public ArrayList<String> search(String newSearch)
	{
		return search(newSearch, " ");
	}
	
	public ArrayList<String> search(String newSearch, String newSort)
	{
		ArrayList<String> itemList = new ArrayList<String>();
		Document fiveMilesDocument;
		
		newSort = sort(newSort);
		
		try
		{
			StringBuilder urlSearch = new StringBuilder();
			
			urlSearch.append(url);
			urlSearch.append(newSearch);
			
			connection = Jsoup.connect(urlSearch.toString());
			
			fiveMilesDocument = connection.get();
			
			Elements data = fiveMilesDocument.select("div.waterItem.waterItemInit.waterItemInvisible");
			
			for(Element link : data.select("div.waterItem_info"))//_1g9xn5a"))
			{
				final String TITLE = link.select("a.waterItem_title_a").text();
				final String PRICE = link.select("span.waterItem_price_now").text();
				final String LINK = link.select("a[href]").attr("href");
				
				String itemDetails = TITLE + "| price: " + PRICE 
						+ "\n" + "link: " + url + LINK + "\n";
				
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
