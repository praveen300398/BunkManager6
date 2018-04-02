package com.example.pm.bunkmanager;


import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import java.util.Calendar;

public class DaysTab extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hometab);
		
		Calendar cal = Calendar.getInstance();
		SharedPreferences prefs = this.getSharedPreferences(
			      "ASN", Context.MODE_PRIVATE);
		
		String fulldate = prefs.getString("DATE", "");
		Log.d("AM", "DaysTab prefdate "+fulldate);
		String date[] = fulldate.split("-");
		
		int year=Integer.parseInt(date[0]);
		int month=Integer.parseInt(date[1]);
		int day_of_month=Integer.parseInt(date[2]);
		cal.set(year,month,day_of_month);
		int daynumber = cal.get(Calendar.DAY_OF_WEEK);
		//Getting to start of week
		while(cal.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY)
			cal.add(Calendar.DATE, -1);
		
		
		String monday = getDateFromCal(cal);    cal.add(Calendar.DATE, 1);
		String tuesday = getDateFromCal(cal);   cal.add(Calendar.DATE, 1);
		String wednesday = getDateFromCal(cal); cal.add(Calendar.DATE, 1);
		String thursday = getDateFromCal(cal);  cal.add(Calendar.DATE, 1);
		String friday = getDateFromCal(cal);    cal.add(Calendar.DATE, 1);
		String saturday = getDateFromCal(cal);  cal.add(Calendar.DATE, 1);
		String sunday = getDateFromCal(cal);
		
		final TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab
	    
		intent = new Intent().setClass(this, MainTimeTable.class);
		intent.putExtra("date", monday);
		intent.putExtra("day", "monday");
	    spec = tabHost.newTabSpec("mon").setIndicator("Mo")
	    					.setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, MainTimeTable.class);
	    intent.putExtra("date", tuesday);
	    intent.putExtra("day", "tuesday");
	    spec = tabHost.newTabSpec("tue").setIndicator("Tu")
	    					.setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, MainTimeTable.class);
	    intent.putExtra("day", "wednesday");
	    intent.putExtra("date", wednesday);
	    spec = tabHost.newTabSpec("wed").setIndicator("Wd")
	    					.setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, MainTimeTable.class);
	    intent.putExtra("day", "thursday");
	    intent.putExtra("date", thursday);
	    spec = tabHost.newTabSpec("thurs").setIndicator("Th")
	    					.setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, MainTimeTable.class);
	    intent.putExtra("date", friday);
	    intent.putExtra("day", "friday");
	    spec = tabHost.newTabSpec("fri").setIndicator("Fr")
	    					.setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, MainTimeTable.class);
	    intent.putExtra("date", saturday);
	    intent.putExtra("day", "saturday");
	    spec = tabHost.newTabSpec("sat").setIndicator("St")
	    					.setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, MainTimeTable.class);
	    intent.putExtra("date", sunday);
	    intent.putExtra("day", "sunday");
	    spec = tabHost.newTabSpec("sun").setIndicator("Sn")
	    					.setContent(intent);
	    tabHost.addTab(spec);
	    
	    
	    int select=daynumber==1?6:(daynumber-2);
	    tabHost.setCurrentTab(select);
	    
	}
	private String getDateFromCal(Calendar cal) {
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day_of_month = cal.get(Calendar.DAY_OF_MONTH);
		
		String Date = year+"-"+month+"-"+day_of_month;
		return Date;
	}
	
}
