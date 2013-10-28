package org.moonila.mycalendar.app.view.layout;

import org.moonila.mycalendar.app.bean.FirstDay;
import org.moonila.mycalendar.app.core.ManageData;
import org.moonila.mycalendar.app.view.R;
import org.moonila.mycalendar.app.view.activity.MyCalendarMainActivity;
import org.moonila.mycalendar.app.view.component.CustomDateTextViewLink;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

public class AddNewDateLayout extends CustomLayout {

    private static CustomDateTextViewLink editDate;

    private static class AddNewDateLayoutHolder {
        private final static AddNewDateLayout instance = new AddNewDateLayout();
    }

    public static AddNewDateLayout getInstance(Context context, View pageView, ManageData manageData) {
        init(context, pageView, manageData);
        return AddNewDateLayoutHolder.instance;
    }

    public void createAddDateLayout() {

        editDate = (CustomDateTextViewLink) pageView.findViewById(R.id.dateModifLink);
        ImageButton save = (ImageButton) pageView.findViewById(R.id.validate);
        save.setOnClickListener(onSave);

    }

    private View.OnClickListener onSave = new View.OnClickListener() {
        public void onClick(View v) {
            boolean error = false;
            if (v.getId() == R.id.validate) {
                error = manageData.addDate(new FirstDay(editDate.getText().toString()));
            }

            if (error) {
                createAlertDialog(context,
                                  context.getString(R.string.dialog_title_error),
                                  context.getString(R.string.dialog_message_formated_date),
                                  onClickListener());

            } else {
                // define a new Intent for the second Activity
                Intent intent = new Intent(context, MyCalendarMainActivity.class);

                // start the second Activity
                context.startActivity(intent);
            }
        }
    };

    private OnClickListener onClickListener() {
        OnClickListener clickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        };

        return clickListener;
    }

}
