package com.dev;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;
/**
 * This handles the Rss feed parsing 
 * @author devashish
 *
 */
public class RSSHandler extends DefaultHandler {
	RSSFeed feed;
	RSSItem item;
	String lastElementName = "";
	boolean bFoundChannel = false;
	final int RSS_TITLE = 1;
	final int RSS_LINK = 2;
	final int RSS_DESCRIPTION = 3;
	final int RSS_PUBDATE = 4;
	
	int depth = 0;
	int currentstate = 0;
	/*
	 * Constructor 
	 */
	RSSHandler(){
	}
	/*
	 * getFeed - this returns our feed when all of the parsing is complete
	 */
	RSSFeed getFeed(){
		return feed;
	}
	
	public void startDocument() throws SAXException {
		// initialize our RSSFeed object - this will hold our parsed contents
		feed = new RSSFeed();
		// initialize the RSSItem object - we will use this as a crutch to grab the info from the channel
		// because the channel and items have very similar entries..
		item = new RSSItem();

	}
	
	public void endDocument() throws SAXException{
	}
	
	/* called before reading every element in the feed. Select the object that is needed by comparing the localname with the tag
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String namespaceURI, String localName,String qName, Attributes atts) throws SAXException
	{
		depth++;
		if (localName.equals("channel")){
			currentstate = 0;
			return;
		}
		
		if (localName.equals("item")){
			item = new RSSItem();
			return;
		}
		
		if (localName.equals("title")){
			currentstate = RSS_TITLE;
			return;
		}
		
		if (localName.equals("description")){
			currentstate = RSS_DESCRIPTION;
			return;
		}
		
		if (localName.equals("link")){
			currentstate = RSS_LINK;
			return;
		}

		if (localName.equals("pubDate")){
			currentstate = RSS_PUBDATE;
			return;
		}
		// if we don't explicitly handle the element, make sure we don't wind up erroneously 
		// storing a newline or other bogus data into one of our existing elements
		currentstate = 0;
	}
	
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException
	{
		depth--;
		if (localName.equals("item"))
		{
			// add our item to the list!
			feed.addItem(item);
			return;
		}
	}
	 
	public void characters(char ch[], int start, int length)
	{
		String theString = new String(ch,start,length);
		Log.i("RSSReader","characters[" + theString + "]");
		
		switch (currentstate)
		{
			case RSS_TITLE:
				item.setTitle(theString);
				currentstate = 0;
				break;
			case RSS_LINK:
				item.setLink(theString);
				currentstate = 0;
				break;
			case RSS_DESCRIPTION:
				item.setDescription(theString);
				currentstate = 0;
				break;
			case RSS_PUBDATE:
				item.setPubDate(theString);
				currentstate = 0;
				break;
			default:
				return;
		}
		
	}
}
