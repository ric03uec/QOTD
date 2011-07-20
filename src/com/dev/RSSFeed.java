package com.dev;

import java.util.List;
import java.util.Vector;

/**
 * Defines the list of RSSFeed Items and related methods
 * @author devashish
 *
 */
public class RSSFeed {
	private String title = null;
	private String pubDate = null;
	private int itemCount = 0;
	private List<RSSItem> itemList;
	
	/**
	 * Initialize the feed
	 */
	RSSFeed(){
		itemList = new Vector(0); 
	}
	
	/**
	 * Add an RSS Item to the feed
	 * @param item
	 * @return count
	 */
	int addItem(RSSItem item){
		itemList.add(item);
		itemCount++;
		return itemCount;
	}
	
	/**
	 * Get item at specified localation
	 * @param location
	 * @return
	 */
	RSSItem getItem(int location){
		return itemList.get(location);
	}
	
	/**
	 * Get all items
	 * @return list of all the items
	 */
	List<RSSItem> getAllItems(){
		return itemList;
	}
	
	/**
	 * Get the current item count in the feed lists
	 * @return
	 */
	int getItemCount(){
		return itemCount;
	}
	
	/**
	 * Set the title of the feed s
	 * @param title
	 */
	void setTitle(String feedTitle){
		title = feedTitle;
	}
	
	/**
	 * Set the publish date of the feed
	 * @param pubdate
	 */
	void setPubDate(String feedPubDate){
		pubDate = feedPubDate;
	}
	
	/**
	 * Get the title of the feed
	 * @return title
	 */
	String getTitle(){
		return title;
	}
	
	/**
	 * Get the publish date of the feed
	 * @return date
	 */
	String getPubDate(){
		return pubDate;
	}
}
