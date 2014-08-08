package org.moonila.mycalendar.app.view.activity;

import org.moonila.mycalendar.app.core.ManageData;
import org.moonila.mycalendar.app.utils.MyCalendarContants;
import org.moonila.mycalendar.app.view.R;
import org.moonila.mycalendar.app.view.layout.AddNewDateLayout;
import org.moonila.mycalendar.app.view.layout.DeleteDateLayout;
import org.moonila.mycalendar.app.view.layout.GraphicalResultLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MyCalendarActionActivity extends Activity {

	protected ManageData manageData = ManageData.getInstance(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		retriveView();

		ImageButton back = (ImageButton) findViewById(R.id.back);
		back.setOnClickListener(onBack);

	}

	private void retriveView() {
		String activityType = getIntent().getStringExtra(
				MyCalendarContants.ACTIVITY_KEY);

		if (activityType.equals(MyCalendarContants.ACTIVITY_ADD_DATE_VALUE)) {
			setContentView(R.layout.layout_add_new_date);
			AddNewDateLayout.getInstance(this,
					findViewById(R.id.add_new_date_layout), manageData)
					.createAddDateLayout();

		} else if (activityType
				.equals(MyCalendarContants.ACTIVITY_DELETE_DATE_VALUE)) {
			setContentView(R.layout.layout_delete_date);
			DeleteDateLayout.getInstance(this,
					findViewById(R.id.delete_date_layout), manageData)
					.createDeleteDateLayout();

		} else {
			setContentView(R.layout.layout_graphicals);
			GraphicalResultLayout.getInstance(this,
					findViewById(R.id.graphical_layout), manageData)
					.createGraphicalResultLayout();
		}

	}

	private View.OnClickListener onBack = new View.OnClickListener() {
		public void onClick(View v) {
			// define a new Intent for the second Activity
			Intent intent = new Intent(MyCalendarActionActivity.this,
					MyCalendarMainActivity.class);

			// start the second Activity
			startActivity(intent);
			
			finish();
		}
	};

}
