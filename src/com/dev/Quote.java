package com.dev;

import java.io.Serializable;
import java.util.HashMap;

public class Quote implements Serializable {

	protected static String AUTHOR = "author";
	protected static String Quote  = "quote";
	
	protected HashMap<String, String> map = new HashMap<String, String>();
	public Quote(){
	}
	
	public String getAuthor(){
		return (String) map.get(AUTHOR);
	}
	
	public void setAuthor(String author){
		map.put(AUTHOR, author);
	}
	
	public String getQuote(){
		return (String) map.get(Quote);
	}
	
	public void setQuote(String quote){
		map.put(Quote, quote);
	}
	
	public void setValue(String author, String quote){
		
	}
}
