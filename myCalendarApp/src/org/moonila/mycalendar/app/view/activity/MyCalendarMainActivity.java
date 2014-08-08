package org.moonila.mycalendar.app.view.activity;

import org.moonila.mycalendar.app.bean.FirstDay;
import org.moonila.mycalendar.app.core.ManageData;
import org.moonila.mycalendar.app.utils.MyCalendarContants;
import org.moonila.mycalendar.app.view.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MyCalendarMainActivity extends Activity {

	private ManageData manageData = ManageData.getInstance(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_my_calendar);

		// remove all data
		// manageData.deleteAll();
		// // Inserting Contacts
		// Log.d("Insert: ", "Inserting ..");
		// manageData.addDate(new FirstDay("18/03/2013"));
		// manageData.addDate(new FirstDay("18/06/2013"));
		// manageData.addDate(new FirstDay("18/07/2013"));
		// manageData.addDate(new FirstDay("18/04/2013"));
		// manageData.addDate(new FirstDay("18/02/2013"));

		FirstDay firstDay = manageData.getLastDate();

		if (firstDay == null) {
			// define a new Intent for the second Activity
			Intent intent = new Intent(MyCalendarMainActivity.this,
					MyCalendarActionActivity.class);
			intent.putExtra(MyCalendarContants.ACTIVITY_KEY,
					MyCalendarContants.ACTIVITY_ADD_DATE_VALUE);

			// start the second Activity
			startActivity(intent);
		} else {
			String[] dateSplited = firstDay.getDateformated().split("/");
			long average = manageData.calculAverage(Integer.valueOf(dateSplited[2]));

			String probablyDate = manageData.calculProbablyDate(average,
					firstDay.getDateTimeStamp());

			TextView lastDateView = (TextView) findViewById(R.id.lastDate);
			lastDateView.setText(firstDay.getDateformated());

			TextView probablyDateView = (TextView) findViewById(R.id.probablyDate);
			probablyDateView.setText(probablyDate);

			TextView averageView = (TextView) findViewById(R.id.daysAverage);
			averageView.setText(String.valueOf(average));
		}

		ImageButton newDate = (ImageButton) findViewById(R.id.newDateButton);
		newDate.setOnClickListener(onLoad);

		ImageButton viewGraph = (ImageButton) findViewById(R.id.viewGraph);
		viewGraph.setOnClickListener(onLoad);

		ImageButton deleteDate = (ImageButton) findViewById(R.id.deleteAction);
		deleteDate.setOnClickListener(onLoad);

	}

	private View.OnClickListener onLoad = new View.OnClickListener() {
		public void onClick(View v) {

			// define a new Intent for the second Activity
			Intent intent = new Intent(MyCalendarMainActivity.this,
					MyCalendarActionActivity.class);

			if (v.getId() == R.id.newDateButton) {
				intent.putExtra(MyCalendarContants.ACTIVITY_KEY,
						MyCalendarContants.ACTIVITY_ADD_DATE_VALUE);

			} else if (v.getId() == R.id.viewGraph) {
				intent.putExtra(MyCalendarContants.ACTIVITY_KEY,
						MyCalendarContants.ACTIVITY_GRAPHICAL_VALUE);
			} else {
				intent.putExtra(MyCalendarContants.ACTIVITY_KEY,
						MyCalendarContants.ACTIVITY_DELETE_DATE_VALUE);

			}

			// start the second Activity
			startActivity(intent);
			finish();
		}
	};
}
