package com.example.pm.bunkmanager;


import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
public class NewOrImport extends Activity implements OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.neworimport);
		setTitle("Attendance Manager-setup");
		Button b = (Button) findViewById(R.id.btn_gotoSub);
		b.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		SQLiteDatabase db = openOrCreateDatabase("am", MODE_PRIVATE, null);
		switch(v.getId()){
		case R.id.btn_gotoSub:
			db.execSQL("CREATE TABLE IF NOT EXISTS subjects(subject_name varchar)");
			Intent intentNotInstalled = new Intent(NewOrImport.this,SubjectList.class);
			startActivity(intentNotInstalled);
			this.finish();
			break;
		}
	}

}
