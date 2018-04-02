package com.example.pm.bunkmanager;


import android.animation.Animator;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainTimeTable extends ListActivity implements OnClickListener,OnItemSelectedListener{
	
	TextView tv_today;
	String today=null;
	String fullDateatn="na";
	String DayOfWeek=null;
	
    FloatingActionButton fab, fab1, fab2, fab3, fab4, fab5;
    LinearLayout fabLayout1;
    LinearLayout fabLayout2;
    LinearLayout fabLayout3;
    LinearLayout fabLayout4;
    LinearLayout fabLayout5;
    //LinearLayout fabLayout6;
    View fabBGLayout;
    boolean isFABOpen=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.maintimetable);
		
		//GETTING SELF DAY AND DATE
		today=getIntent().getExtras().getString("date");
		DayOfWeek=getIntent().getExtras().getString("day");
		
		Log.d("AM", "MTT today="+today);
		Log.d("AM", "MTT DayOfWeek="+DayOfWeek);
		
		//SETTING UP CALENDAR BUTTON
		Button btn_calendar = (Button) findViewById(R.id.btn_calendar);
		btn_calendar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				showDialog(1);//SHOWS DATE SELECTION DIALOG
			}
		});

		fab = (FloatingActionButton) findViewById(R.id.fab);
        fabLayout1= (LinearLayout) findViewById(R.id.fab_layout1);
        fabLayout2= (LinearLayout) findViewById(R.id.fab_layout2);
        fabLayout3 = (LinearLayout) findViewById(R.id.fab_layout3);
        fabLayout4 = (LinearLayout) findViewById(R.id.fab_layout4);
        fabLayout5 = (LinearLayout) findViewById(R.id.fab_layout5);
        //fabLayout6 =(LinearLayout) findViewById(R.id.fab_layout6);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2= (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab4 = (FloatingActionButton) findViewById(R.id.fab4);
        fab5 = (FloatingActionButton) findViewById(R.id.fab5);
       // fab6= (FloatingActionButton) findViewById(R.id.fab);
        fabBGLayout=findViewById(R.id.fabBGLayout);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        fab1.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainTimeTable.this,Stats.class);
                startActivity(intent);
            }
        });

        fab2.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainTimeTable.this);
                builder.setMessage("Are you sure you want to clear timetable data");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase db = openOrCreateDatabase("am", MODE_PRIVATE,null);
                        db.execSQL("DROP TABLE IF EXISTS timetable");
                        db.execSQL("DROP TABLE IF EXISTS attendance");
                        db.execSQL("DROP TABLE IF EXISTS trivial");
                        Intent intent = new Intent(MainTimeTable.this,Main.class);
                        startActivity(intent);
                        MainTimeTable.this.finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        fab3.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainTimeTable.this);
                builder.setMessage("Are you sure you want to clear attendance data?");
                builder.setCancelable(true); //User can enter yes or no.. he can get out of it
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = openOrCreateDatabase("am", MODE_PRIVATE,null);
                        db.execSQL("DROP TABLE IF EXISTS attendance");

                        Intent intent = new Intent(MainTimeTable.this,Main.class);
                        startActivity(intent);
                        MainTimeTable.this.finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        fab4.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainTimeTable.this);
                builder.setMessage("Are you sure you want to reset everything?");
                builder.setCancelable(true); //User can enter yes or no.. he can get out of it
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SQLiteDatabase db = openOrCreateDatabase("am", MODE_PRIVATE,null);
                        db.execSQL("DROP TABLE IF EXISTS subjects");
                        db.execSQL("DROP TABLE IF EXISTS timetable");
                        db.execSQL("DROP TABLE IF EXISTS attendance");
                        db.execSQL("DROP TABLE IF EXISTS trivial");
                        db.execSQL("DROP TABLE IF EXISTS attendanceBackUp");

                        Intent intent = new Intent(MainTimeTable.this,Main.class);
                        startActivity(intent);
                        MainTimeTable.this.finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        fab5.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainTimeTable.this,MainActivity1.class);
                startActivity(intent);
            }
        });
        fabBGLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
            }
        });
		
		tv_today = (TextView) findViewById(R.id.sp_Day);
		Calendar cal = Calendar.getInstance();
		String date[] = today.split("-");
		int year=Integer.parseInt(date[0]);
		int month=Integer.parseInt(date[1]);
		int day_of_month=Integer.parseInt(date[2]);
		//cal.set(year,month,day_of_month);
		//cal.get(Calendar.)
		Date Ddate = new Date(year,month,day_of_month); // is same as date = new

		SimpleDateFormat dformat = new SimpleDateFormat("dd-MMM-yy");
		String formattedDate = dformat.format(Ddate);
		Log.d("AM", "SET TEXTVIEW:"+formattedDate);
		tv_today.setText(formattedDate);
		
		
		
		Button markFull = (Button) findViewById(R.id.btn_markFull);
		
		markFull.setOnClickListener(this);
		
		setListData();
		
	}

    private void showFABMenu(){
        isFABOpen=true;
        fabLayout1.setVisibility(View.VISIBLE);
        fabLayout2.setVisibility(View.VISIBLE);
        fabLayout3.setVisibility(View.VISIBLE);
        fabLayout4.setVisibility(View.VISIBLE);
        fabLayout5.setVisibility(View.VISIBLE);
       // fabLayout6.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(180);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_110));
        fabLayout3.animate().translationY(-getResources().getDimension(R.dimen.standard_165));
        fabLayout4.animate().translationY(-getResources().getDimension(R.dimen.standard_220));
        fabLayout5.animate().translationY(-getResources().getDimension(R.dimen.standard_275));
      //  fabLayout6.animate().translationY(-getResources().getDimension(R.dimen.standard_12));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fabBGLayout.setVisibility(View.GONE);
        fab.animate().rotationBy(-180);
        fabLayout1.animate().translationY(0);
        fabLayout2.animate().translationY(0);
        fabLayout3.animate().translationY(0);
        fabLayout4.animate().translationY(0);
        fabLayout5.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(!isFABOpen){
                    fabLayout1.setVisibility(View.GONE);
                    fabLayout2.setVisibility(View.GONE);
                    fabLayout3.setVisibility(View.GONE);
                    fabLayout4.setVisibility(View.GONE);
                    fabLayout5.setVisibility(View.GONE);
                  //  fabLayout6.setVisibility(View.GONE);
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
	
	
    private void setListData() {
    	
    	Log.d("AM", "MMT setlistdata( called for "+today);
    	ArrayList<Lecture> lectures = populateList(today);
    	
    	for(Lecture lec: lectures){
    		Log.d("AM", lec.getSubject()+" "+lec.getTime()+" "+lec.getAtn());
    	}
    	
		if (lectures!=null)
		{
			LectureArrayAdapter LAA = new LectureArrayAdapter(this, R.layout.lectureinlist, lectures);
	        setListAdapter(LAA);
		}
		else
			Log.d("AM", "LIST EMPTY");
	}
    
    
	// define the listener which is called once a user selected the date.

    @Override
    protected Dialog onCreateDialog(int id) {
        // this method is called after invoking 'showDialog' for the first time
        // here we initiate the corresponding DateSlideSelector and return the dialog to its caller
    	
    	// get today's date and time
        final Calendar c = Calendar.getInstance();
        
        switch (id) {
        case 1:
            return new DatePickerDialog(this,myDateSetListener,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateSetListener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            StringBuilder s = new StringBuilder();
            s.append(i);
            s.append("-");
            s.append(i1);
            s.append("-");
            s.append(i2);
            s.append("-");
            SharedPreferences prefs = getSharedPreferences(
                    "ASN", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("DATE", s.toString()).commit();
            startActivity(new Intent(MainTimeTable.this,DaysTab.class));
            MainTimeTable.this.finish();
        }
    };


	private ArrayList<Lecture> populateList(String date) {
			ArrayList<Lecture> lectures = null;
			Lecture lecture = null;
			if(date!=null){
			try{
				SQLiteDatabase db =openOrCreateDatabase("am", MODE_PRIVATE, null);
				Cursor c = db.rawQuery("SELECT * FROM attendance WHERE thedate='"+date+"' ORDER BY cellNo", null);
				
				lectures = new ArrayList<Lecture>();
			
				int count = c.getCount();
				Log.d("AM", "COUNT="+count);
				
				int i=0;
				
				Cursor c_cell =null;
				
				if(c.moveToFirst()){
					do{
						lecture = new Lecture();
						
						//Set Subject
						String subject = c.getString(c.getColumnIndex("subject"));
						lecture.setSubject(subject);
						
						//Set Time
						c_cell = db.rawQuery("SELECT * FROM timetable where cellNo='"+c.getString(c.getColumnIndex("cellNo"))+"'", null);
						c_cell.moveToFirst();
						String time = c_cell.getString(c_cell.getColumnIndex("st_hour"))+":"+c_cell.getString(c_cell.getColumnIndex("st_min"));
						lecture.setTime(time);
						
						//Set Attendance
						String atn = c.getString(c.getColumnIndex("atn"));
						lecture.setAtn(atn);

						c_cell.close();
						i++;
						if(lecture!=null)
							lectures.add(lecture);
					}while(c.moveToNext());
				}
				//Log.d("WHERE", "after setting adapter");
				c.close();
				db.close();
			}
			catch(Exception e){}
		}
			else{
				Log.d("AM", "MTT date is null");
			}
			return lectures;
	}


	private String getSelectedDate() {
		return today;
	}

	
	@Override
	public void onClick(View v) {
		
		if(v.getId()==R.id.btn_markFull){
			//String cellno =getcellno(position+1);
			String date = getSelectedDate();
			SQLiteDatabase db = openOrCreateDatabase("am", MODE_PRIVATE, null);
			Cursor c = db.rawQuery("SELECT * FROM attendance WHERE thedate ='"+date+"'", null);
			if(c.moveToFirst()){
				
				fullDateatn = roll(fullDateatn);
				db.execSQL("UPDATE attendance SET atn='"+fullDateatn+"' WHERE thedate ='"+date+"'");
				//Log.d("ATN", "attendance update on cellno="+cellno+"   date="+date+"   atn="+atn);
				setListData();
			}
			c.close();
			db.close();
			
		}
		
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		setListData();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		setListData();
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		//Toast t = Toast.makeText(MainTimeTable.this, "LIST item clicked", 2000);
		//t.show();
		String cellno =getcellno(position+1);
		String date = getSelectedDate();
		SQLiteDatabase db = openOrCreateDatabase("am", MODE_PRIVATE, null);
		Cursor c = db.rawQuery("SELECT * FROM attendance WHERE cellNo='"+cellno+"' AND thedate ='"+date+"'", null);
		if(c.moveToFirst()){
			String atn = c.getString(c.getColumnIndex("atn"));
			atn = roll(atn);
			db.execSQL("UPDATE attendance SET atn='"+atn+"' WHERE cellNo='"+cellno+"' AND thedate ='"+date+"'");
			//Log.d("ATN", "attendance update on cellno="+cellno+"   date="+date+"   atn="+atn);
			setListData();
		}
		c.close();
		db.close();
		
	}
	private String roll(String atn) {
		if (atn.equalsIgnoreCase("na"))
			atn = "y";
		else if (atn.equalsIgnoreCase("y"))
			atn = "n";
		else if (atn.equalsIgnoreCase("n"))
			atn = "na";
		return atn;
	}

	private String getcellno(int position){
		int n = 0;
		if (DayOfWeek.equalsIgnoreCase("monday")){
			n = 2;
		}if(DayOfWeek.equalsIgnoreCase("tuesday")){
			n = 3;
		}if(DayOfWeek.equalsIgnoreCase("wednesday")){
			n = 4;
		}if(DayOfWeek.equalsIgnoreCase("thursday")){
			n = 5;
		}if(DayOfWeek.equalsIgnoreCase("friday")){
			n = 6;
		}if(DayOfWeek.equalsIgnoreCase("saturday")){
			n = 7;
		}if(DayOfWeek.equalsIgnoreCase("sunday")){
			n = 1;
		}
		return n+" "+position;
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	return true;
    }

    private String getDateFromCal(Calendar cal) {
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day_of_month = cal.get(Calendar.DAY_OF_MONTH);
		
		String Date = year+"-"+month+"-"+day_of_month;
		return Date;
	}

    @Override
    public void onBackPressed() {
        if(isFABOpen){

            closeFABMenu();
        }
        else{

            AlertDialog.Builder builder = new AlertDialog.Builder(MainTimeTable.this);
            builder.setCancelable(true);
            builder.setMessage("Do You Want To Exit");
            builder.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainTimeTable.this.finish();
                }
            });

            builder.setNegativeButton("No",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
