package com.dev;

/**
 * Define an item in the RSS feed
 * @author devashish
 *
 */
public class RSSItem {
	private String TITLE = "title";
	private String DESCRIPTION = "description";
	private String LINK = "link";
	private String PUB_DATE = "pubDate";

	RSSItem(){
	}
	
	void setTitle(String title){
		this.TITLE = title;
	}
	
	void setDescription(String description){
		this.DESCRIPTION = description;
	}
	void setLink(String link){
		this.LINK = link;
	}

	void setPubDate(String pubdate){
		this.PUB_DATE = pubdate;
	}
	
	String getTitle(){
		return (String) this.TITLE;
	}
	
	String getDescription(){
		return (String) this.DESCRIPTION;
	}
	String getLink(){
		return (String) this.LINK;
	}
	
	String getPubDate(){
		return (String) this.PUB_DATE;
	}
	public String toString()
	{
		if (TITLE.length() > 42)
		{
			return TITLE.substring(0, 42) + "...";
		}
		return TITLE;
	}
}
