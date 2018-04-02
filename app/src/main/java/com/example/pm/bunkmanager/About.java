package com.example.pm.bunkmanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
public class About extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		setTitle("About us");
		TextView tv_about  = (TextView) findViewById(R.id.tv_about);

		String toast = "Presented By:\n\nG.MAHESH VARMA\n(160116737091)\n\nV.PRAVEEN KUMAR\n(160116737102)\n\nIT2-2/4\n";
		tv_about.setText(toast);
	}
}

