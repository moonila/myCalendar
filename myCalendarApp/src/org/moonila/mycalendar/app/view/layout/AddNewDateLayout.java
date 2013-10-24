package org.moonila.mycalendar.app.view.layout;

import java.util.Date;

import org.moonila.mycalendar.app.bean.FirstDay;
import org.moonila.mycalendar.app.core.ManageData;
import org.moonila.mycalendar.app.utils.DateUtils;
import org.moonila.mycalendar.app.view.R;
import org.moonila.mycalendar.app.view.activity.MyCalendarMainActivity;
import org.moonila.mycalendar.app.view.widget.CustomDatePicker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class AddNewDateLayout {

	private static ManageData manageData;

	private static Context context;

	private static View addNewDateView;

	private static String dateValue;

	private static Button editDate;

	private static class AddNewDateLayoutHolder {
		private final static AddNewDateLayout instance = new AddNewDateLayout();
	}

	public static AddNewDateLayout getInstance(Context context,
			View addNewDateView, ManageData manageData) {
		AddNewDateLayout.context = context;
		AddNewDateLayout.manageData = manageData;
		AddNewDateLayout.addNewDateView = addNewDateView;
		return AddNewDateLayoutHolder.instance;
	}

	public void createAddDateLayout() {

		editDate = (Button) addNewDateView.findViewById(R.id.addDate);
		Date date = new Date();
		dateValue = DateUtils.formatDate(date.getTime());
		editDate.setText(dateValue);
		editDate.setOnClickListener(onChangeDate);

		Button save = (Button) addNewDateView.findViewById(R.id.validate);
		save.setOnClickListener(onSave);

	}

	
//	private View.OnClickListener onChangeDate = new View.OnClickListener() {
//
//		@SuppressLint("NewApi")
//		public void onClick(View v) {
//
//			final Calendar dateAndTime = Calendar.getInstance();
//
//			DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
//				public void onDateSet(DatePicker view, int year,
//						int monthOfYear, int dayOfMonth) {
//					dateAndTime.set(Calendar.YEAR, year);
//					dateAndTime.set(Calendar.MONTH, monthOfYear);
//					dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//					view.setCalendarViewShown(false);
//					dateValue = DateUtils.formatDate(dateAndTime
//							.getTimeInMillis());
//					editDate.setText(dateValue);
//				}
//			};
//
//			DatePickerDialog datePicker = new DatePickerDialog(context,
//					DatePickerDialog.THEME_HOLO_DARK, d, dateAndTime.get(Calendar.YEAR),
//					dateAndTime.get(Calendar.MONTH),
//					dateAndTime.get(Calendar.DAY_OF_MONTH));
//			if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.HONEYCOMB) {
//				datePicker.getDatePicker().setCalendarViewShown(false);
//			}
//
//			datePicker.show();
//
//		}
//	};
	
	private View.OnClickListener onChangeDate = new View.OnClickListener() {
		public void onClick(View v) {
			
			

			AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//			int test = org.holoeverywhere.R.styleable.NumberPicker_virtualButtonPressedDrawable;
//			Drawable d = context.getResources().getDrawable(org.holoeverywhere.R.styleable.NumberPicker_virtualButtonPressedDrawable);

			
			final CustomDatePicker custom = new CustomDatePicker(context);
			custom.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					custom.setValues();
					return false;
				}
			});
			
//			final DatePicker datepicker = new DatePicker(context);
//			datepicker.setOnTouchListener(new OnTouchListener() {
//				@Override
//				public boolean onTouch(View v, MotionEvent event) {
////					custom.setValues();
//					datepicker.updateDate(datepicker.getYear(), datepicker.getMonth(), datepicker.getDayOfMonth());
//					return false;
//				}
//			});

			alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL,
					context.getString(R.string.dialogOk),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// User clicked OK button
//							Calendar cal = Calendar.getInstance();
//							cal.set(datepicker.getYear(), datepicker.getMonth(), datepicker.getDayOfMonth());
//							dateValue = DateUtils.formatDate(cal.getTimeInMillis());
							dateValue = custom.getValues();
							editDate.setText(dateValue);
						}
					});

			alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
					context.getString(R.string.dialogCancel),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							//Do nothing
						}
					});

			alertDialog.setView(custom);
			alertDialog.show();
		}
	};

	private View.OnClickListener onSave = new View.OnClickListener() {
		public void onClick(View v) {
			boolean error = false;
			if (v.getId() == R.id.validate) {
				// EditText newDate = (EditText) addNewDateView
				// .findViewById(R.id.newAddForm);

				// String newDateValue = newDate.getText().toString();
				error = manageData.addDate(new FirstDay(dateValue));
			}

			if (error) {
				getErrorAlertPopup();
			} else {
				// define a new Intent for the second Activity
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
						// User clicked OK button
					}
				});

		alertDialog.show();
	}

}
