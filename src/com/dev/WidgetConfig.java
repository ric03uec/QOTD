package com.dev;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class handles the initial configuration of the widget.
 * Also sets the timers for update intervals.
 * @author devashish
 *
 */
public class WidgetConfig extends ListActivity {
	private static final String TAG = "WIDGET_CONFIG";
	
	private static Integer UPDATE_TIME_MINS 	= 240;
	private Button configOkButton;
	private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	public static HashMap<Integer, Quote> quoteMap = new HashMap<Integer, Quote>();
	
	/**
	 * Called when the activity is created, used for initializing the values
	 */
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		String feedSource = null;
		if(1 == position){
			feedSource = getString(R.string.brainy_quote_source);
		}else if(2 == position){
			feedSource = getString(R.string.quotation_page_source);
		}
		
		Thread quoteFetcher = new Thread(new FetchQuotes(feedSource));
		quoteFetcher.start();
		
		setResult();
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setResult(RESULT_CANCELED);
		
		String [] options = getResources().getStringArray(R.array.mainMenu);
		this.getListView().addHeaderView(getLayoutInflater().inflate(R.layout.config_header, null));
		this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options));
		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if(extras != null){
			mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
			setAlarm();
//			setResult();
			
		}
		
		if(mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID){
//			finish();
		}
	}
	
	/**
	 * Set the alarm which sends an intent to refresh the quote
	 */
	private void setAlarm(){		
		Intent intent = new Intent(WidgetConfig.this, WidgetProvider.class);
		intent.setAction(WidgetProvider.QOTD_WIDGET_UPDATE);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(WidgetConfig.this, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 0);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 86400, pendingIntent);
		
		WidgetProvider.saveAlarmManager(alarmManager, pendingIntent);
	}
	
	private void setResult(){	
		Intent resultValue = new Intent();
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
		setResult(RESULT_OK, resultValue);
		finish();
	}
}