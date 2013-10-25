package org.moonila.mycalendar.app.view.layout;

import java.util.Calendar;
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
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;

public class AddNewDateLayout {

    private static ManageData manageData;

    private static Context context;

    private static View addNewDateView;

    private static String dateValue;

    private static TextView editDate;

    private static class AddNewDateLayoutHolder {
        private final static AddNewDateLayout instance = new AddNewDateLayout();
    }

    public static AddNewDateLayout getInstance(Context context, View addNewDateView, ManageData manageData) {
        AddNewDateLayout.context = context;
        AddNewDateLayout.manageData = manageData;
        AddNewDateLayout.addNewDateView = addNewDateView;
        return AddNewDateLayoutHolder.instance;
    }

    protected boolean itsCustomDatePicker;

    public void createAddDateLayout() {

        Date date = new Date();
        dateValue = DateUtils.formatDate(date.getTime());
        
//        editDate = (Button) addNewDateView.findViewById(R.id.addDate);
//        editDate.setText(dateValue);
//        editDate.setOnClickListener(onChangeDate);
        
        editDate = (TextView) addNewDateView.findViewById(R.id.addDate);
//        editDate.setText(dateValue);
        SpannableString content = new SpannableString(dateValue);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        editDate.setText(content);
        editDate.setOnClickListener(onChangeDate);

        Button save = (Button) addNewDateView.findViewById(R.id.validate);
        save.setOnClickListener(onSave);

    }

    private View.OnClickListener onChangeDate = new View.OnClickListener() {
        public void onClick(View v) {

            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            itsCustomDatePicker = true;
            final FrameLayout custom;
            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.HONEYCOMB) {
                custom = new CustomDatePicker(context);
                custom.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        ((CustomDatePicker) custom).setValues();
                        return false;
                    }
                });
            } else {
                itsCustomDatePicker = false;
                custom = new DatePicker(context);
                custom.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        ((DatePicker) custom).updateDate(((DatePicker) custom).getYear(),
                                                         ((DatePicker) custom).getMonth(),
                                                         ((DatePicker) custom).getDayOfMonth());
                        return false;
                    }
                });
            }
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, context.getString(R.string.dialogOk), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (AddNewDateLayout.this.itsCustomDatePicker) {
                        dateValue = ((CustomDatePicker) custom).getValues();
                    } else {
                        dateValue = getValues(((DatePicker) custom).getYear(),
                                              ((DatePicker) custom).getMonth(),
                                              ((DatePicker) custom).getDayOfMonth());
                    }

                    SpannableString content = new SpannableString(dateValue);
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    editDate.setText(content);
                }
            });

            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.dialogCancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Do nothing
                }
            });

            alertDialog.setView(custom);
            alertDialog.show();
        }
    };

    public String getValues(int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, dayOfMonth);
        return DateUtils.formatDate(cal.getTimeInMillis());

    }

    private View.OnClickListener onSave = new View.OnClickListener() {
        public void onClick(View v) {
            boolean error = false;
            if (v.getId() == R.id.validate) {
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

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.dialogOk), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });

        alertDialog.show();
    }

}
