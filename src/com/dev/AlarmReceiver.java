package com.dev;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

	private static String TAG = "ALARM_RECV";
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		if(extras != null){
			AppWidgetManager appwidgetmaManager = AppWidgetManager.getInstance(context);
			ComponentName thisAppWidget = new ComponentName(context.getPackageName(), WidgetProvider.class.getName());
			Log.d(TAG, thisAppWidget.toString());
			int [] appWidgetIds = appwidgetmaManager.getAppWidgetIds(thisAppWidget);
			

			
//			Intent newIntent = new Intent(context, InetConnection.class);
//			intent.putExtra("MSG", "NETWORK CHANGE...");
//			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			context.startActivity(newIntent);
			
			for (int i=0; i<appWidgetIds.length; i++) {
	            int appWidgetId = appWidgetIds[i];

//	            RemoteViews updateView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
//	            
//	            if(intent.getStringExtra("QUOTEPRESENT").equals("TRUE")){
//		    		updateView.setTextViewText(R.id.quote, intent.getStringExtra("QUOTEVALUE"));
//		    		updateView.setTextViewText(R.id.author, intent.getStringExtra("AUTHOR"));
//	            }else if(intent.getStringArrayExtra("QUOTEPRESENT").equals("FALSE")){
//	            	updateView.setTextViewText(R.id.quote, intent.getStringExtra("QUOTEVALUE"));
//		    		updateView.setTextViewText(R.id.author, "");
//	            }
//	    		appwidgetmaManager.updateAppWidget(appWidgetId, updateView);
//	    		
//	    		Toast.makeText(context, "onUpdate()", Toast.LENGTH_LONG).show();
//	    		if(FetchQuotes.isFeedPresent)
//	    			Toast.makeText(context, "FEED PRESENT", Toast.LENGTH_LONG).show();
	    		
	    		WidgetProvider.updateAppWidget(context, appwidgetmaManager, appWidgetId);
	        }
		}

	}

}
