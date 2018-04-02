package com.example.pm.bunkmanager;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

public class Stats extends Activity implements OnItemSelectedListener,OnFocusChangeListener,OnClickListener,OnCheckedChangeListener{

	ArrayAdapter<String> adapterForSpinnerSubject;
	Spinner sp_subject=null;
	EditText et_minPerc = null;
    Button b_from,b_to;
    TextView t_from,t_to;
    String fd,td;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stats);
		setTitle("Statistics");
		et_minPerc = (EditText) findViewById(R.id.editText1);
        b_from = (Button) findViewById(R.id.btn_from);
        b_to = (Button) findViewById(R.id.btn_to);
        t_from = (TextView) findViewById(R.id.t_from);
        t_to = (TextView) findViewById(R.id.t_to);
		SQLiteDatabase db =  openOrCreateDatabase("am", MODE_PRIVATE, null);
		try{
			Cursor c = db.rawQuery("SELECT * FROM attendanceBackup", null);
			if(!c.moveToFirst()){
				et_minPerc.setGravity(Gravity.CENTER_HORIZONTAL);
			}
		}catch(Exception e){}

        b_from.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                showDialog(1);
            }
        });

        b_to.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                showDialog(2);
            }
        });
		
		populateSubjectSpinner();
		setListeners();
		CalculateStats();
	}

    @Override
    protected Dialog onCreateDialog(int id) {
        final Calendar c = Calendar.getInstance();

        switch (id){

            case 1: return new DatePickerDialog(this,myDateSetListener,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
            case 2: return new DatePickerDialog(this,myDateSetListener2,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
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

            StringBuilder ss = new StringBuilder();
            ss.append(i);
            ss.append("-");
            ss.append(i1+1);
            ss.append("-");
            ss.append(i2);

            t_from.setText(ss);
            fd = s.toString();

        }
    };

    private DatePickerDialog.OnDateSetListener myDateSetListener2 = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            StringBuilder s1 = new StringBuilder();
            s1.append(i);
            s1.append("-");
            s1.append(i1);
            s1.append("-");
            s1.append(i2);

            StringBuilder s2 = new StringBuilder();
            s2.append(i);
            s2.append("-");
            s2.append(i1+1);
            s2.append("-");
            s2.append(i2);

            t_to.setText(s2);
            td = s1.toString();
        }
    };

    private void CalculateStats() {
		int y=0,n=0;
		int th=75;
		SQLiteDatabase db = openOrCreateDatabase("am", MODE_PRIVATE, null);
		try{
			//String msg=null;
			Cursor c=null;

			String sub = sp_subject.getSelectedItem().toString();
			String minPerc = et_minPerc.getText().toString();
			//msg = "fd="+fd+"\ntd="+td+"\nsub="+sub+"\nminPerc="+minPerc;
			th=Integer.parseInt(minPerc);
			
			if(sub.equalsIgnoreCase("All Subjects")){
				
				c=db.rawQuery("SELECT count(*)\"y\" FROM attendance WHERE (thedate >='"+fd+"' AND thedate<='"+td+"') AND atn='y'", null);
				if(c.moveToFirst()){
					y+=Integer.parseInt(c.getString(c.getColumnIndex("y")));
				}
				c=db.rawQuery("SELECT count(*)\"n\" FROM attendance WHERE (thedate >='"+fd+"' AND thedate<='"+td+"') AND atn='n'", null);
				if(c.moveToFirst()){
				n+=Integer.parseInt(c.getString(c.getColumnIndex("n")));
				}

			}
			else{
				c=db.rawQuery("SELECT count(*)\"y\" FROM attendance WHERE (thedate >='"+fd+"' AND thedate<='"+td+"') AND (subject ='"+sub+"' AND atn='y')", null);
				if(c.moveToFirst()){
				y+=Integer.parseInt(c.getString(c.getColumnIndex("y")));
				}
				c=db.rawQuery("SELECT count(*)\"n\" FROM attendance WHERE (thedate >='"+fd+"' AND thedate<='"+td+"') AND (subject ='"+sub+"' AND atn='n')", null);
				if(c.moveToFirst()){
				n+=Integer.parseInt(c.getString(c.getColumnIndex("n")));
				}

			}
			c.close();
			
		}catch(Exception e){
		}
		finally{
			TextView p = (TextView) findViewById(R.id.textView3);
			TextView a = (TextView) findViewById(R.id.textView4);
			TextView t = (TextView) findViewById(R.id.textView5);
			TextView per = (TextView) findViewById(R.id.textView6);
			TextView summary = (TextView) findViewById(R.id.textView7);
			
			if(y!=0 || n!=0){
				float perc = (y*100)/(y+n);
				
				p.setText("Present ="+y);
				a.setText("Absent  ="+n);
				t.setText("Total   ="+(y+n));
				per.setText("%age   ="+perc);

					//TH range check
					th=(th==100)?99:th;
					th = (int)Math.abs(th)%100 ;
					th=(th==0)?1:th;
					
					EditText et = (EditText)findViewById(R.id.editText1);
					et.setText(""+th);
					
				String sumry="";
				if(perc<th && th!=0){

					double thby100 = th/100.000;
					double totlecs = y+n;
					double denom = 1-thby100;
					
					double haveToSitnew = Math.ceil((thby100*totlecs-y)/denom); 
					sumry+="You have to sit "+(int)haveToSitnew+" lecture(s).";
				}
				else if(perc>=th){
					int canBunk = (int) Math.floor((100*y/th) - (y+n));
					sumry+="You can bunk "+canBunk+" lecture(s).";
				}
				
				summary.setText(sumry);
			}
			else{
				p.setText("");
				a.setText("");
				t.setText("");
				per.setText("");
				summary.setText("No Lectures Conducted.");
			}

			db.close();
		}
		
	}

	private void populateSubjectSpinner() {
		//POPULATION OF Subject SPINNER
		Cursor c = null;
		SQLiteDatabase db = null;
		try{
			db = openOrCreateDatabase("am", MODE_PRIVATE, null);
			sp_subject= (Spinner) findViewById(R.id.spinner3);
			adapterForSpinnerSubject = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item); 
			adapterForSpinnerSubject.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item); 
			sp_subject.setAdapter(adapterForSpinnerSubject);
			c=db.rawQuery("SELECT * FROM subjects", null);
			
			int i=0;
			if (c.moveToFirst()) {
					 adapterForSpinnerSubject.add("All Subjects");
			         do {
			        	 //if(i==0)
			        		// selecteddate = c.getString(c.getColumnIndex("thedate"));
			             adapterForSpinnerSubject.add(c.getString(c.getColumnIndex("subject_name")));
			             i++;
			         } while (c.moveToNext());      
			 }
		}catch(Exception e){}
		finally{
			c.close();
			db.close();
		}

	}


	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		CalculateStats();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		//CalculateStats();
	}
	public void setListeners() {

		sp_subject.setOnItemSelectedListener(this);
		et_minPerc.setOnClickListener(this);
	}


	
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		
	}


	@Override
	public void onClick(View v) {
		if(v.getId()==et_minPerc.getId()){
			CalculateStats();
		}
	}



	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		CalculateStats();
	}
}
