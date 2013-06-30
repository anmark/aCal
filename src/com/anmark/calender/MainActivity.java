package com.anmark.calender;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;

public class MainActivity extends Activity {

	private CalenderView cv1, cv2, cv3, cv4;	
	private LinearLayout layout; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null){
			//do something
		}
		else{
			layout = new LinearLayout(this);
			layout.setBackgroundColor(Color.BLACK);
			layout.setOrientation(LinearLayout.VERTICAL);
			layout.setLayoutParams(new LinearLayout.LayoutParams(
					(LayoutParams.WRAP_CONTENT), (LayoutParams.WRAP_CONTENT), 2.0f));
			cv3 = new CalenderView(this);
			cv4 = new CalenderView(this);
			cv3.setLayoutParams(new LinearLayout.LayoutParams(
					(LayoutParams.WRAP_CONTENT), (LayoutParams.WRAP_CONTENT), 1.0f));
			cv4.setLayoutParams(new LinearLayout.LayoutParams(
					(LayoutParams.WRAP_CONTENT), (LayoutParams.WRAP_CONTENT), 1.0f));
			cv4.setDate(cv4.createCalender(1988, 4, 31));
			layout.addView(cv3);
			layout.addView(cv4);
		}

		// activity_main.xml not used 
		//cv1 = (CalenderView)findViewById(R.id.calenderView1);
		//cv2 = (CalenderView)findViewById(R.id.calenderView2);
		//cv2.setDate(cv4.createCalender(1988, 4, 31));

		setContentView(layout);    

		cv3.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				cv3.nextDay();
			}
		});

		cv4.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				cv4.nextDay();
			}
		});

	}

	protected void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		//savedInstanceState.putLong("param", value);
	}

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		// Checks the orientation of the screen
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			layout.setOrientation(LinearLayout.HORIZONTAL);
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
			layout.setOrientation(LinearLayout.VERTICAL);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void Cal1Clicked(View v){
		// activity_main.xml OnClick not used
		// cv1.nextDay();
	}

	public void Cal2Clicked(View v){
		// activity_main.xml OnClick not used
		// cv2.nextDay();
	}
}
