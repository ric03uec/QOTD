package com.dev;

import java.net.URL;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.util.Log;

/**
 * Spawn a separate thread to fetch the quotes so that the main program
 * does not get interrupted
 * @author devashish
 *
 */
public class FetchQuotes implements Runnable {

	private String feedSource;
	private RSSFeed feed = null;
	public static HashMap<Integer, Quote> quoteMap = new HashMap<Integer, Quote>();
	public static boolean isFeedPresent = false;
	public static int FEED_INDEX = 0;
	Thread quotesFetcher;
	
	public FetchQuotes(String feedSource){
		this.feedSource = feedSource;
		this.quotesFetcher = new Thread(FetchQuotes.this);
		this.quotesFetcher.start();
	}
	
	@Override
	public void run() {
		feed = getFeed(this.feedSource);
		parseFeedIntoQuotes();
		
	}
	
	private RSSFeed getFeed(String urlToRssFeed)
    {
    	try{ 
    	   URL url = new URL(urlToRssFeed);
           SAXParserFactory factory = SAXParserFactory.newInstance();
           SAXParser parser = factory.newSAXParser();
           
           XMLReader xmlreader = parser.getXMLReader();
           RSSHandler theRssHandler = new RSSHandler();
           xmlreader.setContentHandler(theRssHandler);
           InputSource is = new InputSource(url.openStream());
           xmlreader.parse(is);
           return theRssHandler.getFeed();
    	}
    	catch (Exception e){
    		return null;
    	}
    }
	
	private void parseFeedIntoQuotes(){      
        if (feed == null){
        	isFeedPresent = false;
        	return;
        }
        
        if(feed.getAllItems().size() > 0)
        	isFeedPresent = true;
        else return;
        
        for(int i = 0 ; i < feed.getAllItems().size(); i++){
        	
        	Quote quote = new Quote();
        	String author = feed.getAllItems().get(i).getTitle().toString();
        	String quoteValue  = feed.getAllItems().get(i).getDescription().toString();
        	
        	
        	quote.setAuthor(author);
        	quote.setQuote(quoteValue);
        	
        	quoteMap.put(i, quote);
        	
        	Log.d("QOTD: ", "QUOTE at pos " + i + "IS ---------> " + quoteMap.get(i).getQuote());

        }
    }
	
	public static int getQuoteMapSize(){
		return quoteMap.size();
	}
	
	public static Quote getLatestQuote(){
		if(FEED_INDEX == quoteMap.size())
			FEED_INDEX = 0;
		return quoteMap.get(FEED_INDEX++);
	}
	
	public static HashMap<Integer, Quote> getQuoteMap(){
		return quoteMap; 
	}
}
