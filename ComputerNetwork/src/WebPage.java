import org.jsoup.nodes.Document;

import java.net.URLConnection;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

public abstract class WebPage 
{
	protected String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";
	protected String url;
	protected Connection connection;
	
	WebPage()
	{
		connection = connect();
		url = " ";
	}
	
	WebPage(String newURL)
	{
		url = newURL;
		connection = connect();
	}
	
	//PRIVATE METHODS-------------------------------------------
	//get connection to url
	private Connection connect()
	{
		return Jsoup.connect(url);
	}
	
	//PUBLIC METHODS--------------------------------------------
	//search webpage and return items as ArrayList
	public abstract ArrayList<String> search(String newSearch);
	
}
