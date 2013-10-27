package org.moonila.mycalendar.app.view.layout;

import org.moonila.mycalendar.app.core.ManageData;
import org.moonila.mycalendar.app.view.R;
import org.moonila.mycalendar.app.view.activity.MyCalendarMainActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class DeleteDateLayout {

	private static ManageData manageData;

	private static Context context;

	private static View deleteDateView;

	private static int deleteDateViewId;

	private String yearSelected;

	private static class DeleteDateLayoutHolder {
		private final static DeleteDateLayout instance = new DeleteDateLayout();
	}

	public static DeleteDateLayout getInstance(Context context,
			View deleteDateView, ManageData manageData) {
		DeleteDateLayout.context = context;
		DeleteDateLayout.manageData = manageData;
		DeleteDateLayout.deleteDateView = deleteDateView;
		return DeleteDateLayoutHolder.instance;
	}

	public void createDeleteDateLayout() {

		RadioButton deleteAll = (RadioButton) deleteDateView
				.findViewById(R.id.deleteAll);
		deleteAll.setOnClickListener(onSelect);
		deleteDateViewId = R.id.deleteAll;

		createSpinnerList();

		RadioButton deleteByDate = (RadioButton) deleteDateView
				.findViewById(R.id.deleteByDate);
		deleteByDate.setOnClickListener(onSelect);

		RadioButton deleteOne = (RadioButton) deleteDateView
				.findViewById(R.id.deleteOne);
		deleteOne.setOnClickListener(onSelect);

		ImageButton save = (ImageButton) deleteDateView.findViewById(R.id.validate);
		save.setOnClickListener(onDelete);

	}

	private void createSpinnerList() {
		final String[] items = manageData.createListYears();

		Spinner spin = (Spinner) deleteDateView
				.findViewById(R.id.spinnerSelectedYear);
		spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View v,
					int position, long id) {
				yearSelected = items[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				yearSelected = items[0];
			}
		});
		ArrayAdapter<String> aa = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, items);
		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(aa);

	}

	private View.OnClickListener onDelete = new View.OnClickListener() {
		public void onClick(View v) {

			if (v.getId() == R.id.validate) {
				if (deleteDateViewId == R.id.deleteAll) {
					AlertDialog alertDialog = new AlertDialog.Builder(context)
							.create();
					alertDialog.setMessage(context
							.getString(R.string.dialogMessage));
					alertDialog.setTitle(R.string.dialogTitle);

					alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
							context.getString(R.string.dialogOk),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									manageData.deleteAll();

									Intent intent = new Intent(context,
											MyCalendarMainActivity.class);
									context.startActivity(intent);
								}
							});

					alertDialog.show();
				} else if (deleteDateViewId == R.id.deleteByDate) {
					manageData.deleteAllByYear(Integer.valueOf(yearSelected));
					Intent intent = new Intent(context,
							MyCalendarMainActivity.class);
					context.startActivity(intent);
				} else if (deleteDateViewId == R.id.deleteOne) {

				} else {
					Intent intent = new Intent(context,
							MyCalendarMainActivity.class);

					// start the second Activity
					context.startActivity(intent);
				}
			}

			// if (error) {
			// getErrorAlertPopup();
			// } else {
			// // define a new Intent for the second Activity
//			 Intent intent = new Intent(context, MyCalendarMainActivity.class);
			//
			// // start the second Activity
			// context.startActivity(intent);
			// }
		}
	};

	private View.OnClickListener onSelect = new View.OnClickListener() {
		public void onClick(View v) {
			RelativeLayout deleteByDateLayout = (RelativeLayout) deleteDateView
					.findViewById(R.id.deleteByDateLayout);
			TextView txtView2 = (TextView) deleteDateView
					.findViewById(R.id.deleteOneText);
			deleteDateViewId = v.getId();
			if (deleteDateViewId == R.id.deleteAll) {
				deleteByDateLayout.setVisibility(View.GONE);
				txtView2.setVisibility(View.GONE);
			} else if (deleteDateViewId == R.id.deleteByDate) {
				deleteByDateLayout.setVisibility(View.VISIBLE);
				txtView2.setVisibility(View.GONE);
			} else if (deleteDateViewId == R.id.deleteOne) {
				deleteByDateLayout.setVisibility(View.GONE);
				txtView2.setVisibility(View.VISIBLE);
			} else {
				Intent intent = new Intent(context, MyCalendarMainActivity.class);

				// start the second Activity
				context.startActivity(intent);
			}

		}
	};

	public static void getErrorAlertPopup() {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setMessage(context.getString(R.string.dialogMessage));
		alertDialog.setTitle(R.string.dialogTitle);

		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
				context.getString(R.string.dialogOk),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						manageData.deleteAll();
					}
				});

		alertDialog.show();
	}

}
