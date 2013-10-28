package org.moonila.mycalendar.app.view.component;

import java.util.Calendar;
import java.util.Date;

import org.moonila.mycalendar.app.utils.DateUtils;
import org.moonila.mycalendar.app.view.R;
import org.moonila.mycalendar.app.view.widget.CustomDatePicker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;

public class CustomDateTextViewLink extends TextView {

    protected boolean itsCustomDatePicker;
    private String dateValue;
    private Context context;

    public CustomDateTextViewLink(Context context) {
        this(context, null, R.style.Custom_TextLinkStyle);
    }

    public CustomDateTextViewLink(Context context, AttributeSet attrs) {
        this(context, attrs, R.style.Custom_TextLinkStyle);
    }

    public CustomDateTextViewLink(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        Date date = new Date();
        dateValue = DateUtils.formatDate(date.getTime());

        init();
    }

    private void init() {
        SpannableString content = new SpannableString(dateValue);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        setText(content);
        setOnClickListener(onChangeDate);
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
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, context.getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (CustomDateTextViewLink.this.itsCustomDatePicker) {
                        dateValue = ((CustomDatePicker) custom).getValues();
                    } else {
                        dateValue = getValues(((DatePicker) custom).getYear(),
                                              ((DatePicker) custom).getMonth(),
                                              ((DatePicker) custom).getDayOfMonth());
                    }

                    SpannableString content = new SpannableString(dateValue);
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    setText(content);
                }
            });

            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Do nothing
                }
            });

            alertDialog.setView(custom);
            alertDialog.show();
        }
    };

    private String getValues(int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, dayOfMonth);
        return DateUtils.formatDate(cal.getTimeInMillis());

    }

}
