package com.dev;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * This handles the updates/actions that occur on the widget, whether called explicitly
 * or after the expiration of the timer.
 * @author devashish
 *
 */
public class WidgetProvider extends AppWidgetProvider {

	
	public static AlarmManager qotdAlarmManager;
	public static PendingIntent qotdPendingIntent;
	
	private SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy  hh:mm:ss a");
	private String strWidgetText = "";
	
	public static String QOTD_WIDGET_UPDATE = "QOTD_WIDGET_UPDATE";
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	/**
	 * Called when the last instance of this widget is deleted.
	 */
	public void onDisabled(Context context) {
		super.onDisabled(context);
		qotdAlarmManager.cancel(qotdPendingIntent);
	}

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
	}

	/**
	 * Called when the updates for onWidgetUpdate is broadcasted or when a pending intent for this 
	 * provider is fired after the expiration of its time.
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		Bundle extras = intent.getExtras();
		if(extras != null){
			if(QOTD_WIDGET_UPDATE.equals(intent.getAction())){
				AppWidgetManager appwidgetmaManager = AppWidgetManager.getInstance(context);
				ComponentName thisAppWidget = new ComponentName(context.getPackageName(), WidgetProvider.class.getName());
				int [] appWidgetIds = appwidgetmaManager.getAppWidgetIds(thisAppWidget);
				
				for (int i=0; i<appWidgetIds.length; i++) {
		            int appWidgetId = appWidgetIds[i];	    		
		    		WidgetProvider.updateAppWidget(context, appwidgetmaManager, appWidgetId);
		        }
			}
		}
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	
	}
        

	/**
	 * Update all The specified widget with a new quote from the list
	 * @param context
	 * @param appWidgetManager
	 * @param appWidgetId
	 */
	public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){

		RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

		if(FetchQuotes.isFeedPresent){
			Quote quote = FetchQuotes.getLatestQuote();
			updateViews.setTextViewText(R.id.quote, " " + quote.getQuote() + "  ");
			updateViews.setTextViewText(R.id.author, quote.getAuthor());
		}else{
			updateViews.setTextViewText(R.id.quote, " Unable to Retreive Feed, Please Check Connection...");
			updateViews.setTextViewText(R.id.author, "");
		}
		
		appWidgetManager.updateAppWidget(appWidgetId, updateViews);
	}
	
	public static void saveAlarmManager(AlarmManager sAlarmManager, PendingIntent sPendingIntent){
		qotdAlarmManager = sAlarmManager;
		qotdPendingIntent = sPendingIntent;
	}

}
