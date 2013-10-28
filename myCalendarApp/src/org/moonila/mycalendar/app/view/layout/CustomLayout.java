package org.moonila.mycalendar.app.view.layout;

import org.moonila.mycalendar.app.core.ManageData;
import org.moonila.mycalendar.app.view.R;
import org.moonila.mycalendar.app.view.activity.MyCalendarMainActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class CustomLayout {

    protected static ManageData manageData;

    protected static Context context;

    protected static View pageView;

    protected static void init(Context context, View pageView, ManageData manageData) {
        CustomLayout.context = context;
        CustomLayout.manageData = manageData;
        CustomLayout.pageView = pageView;
    }

    protected void createAlertDialogWithSingleButton(String title, String message, OnClickListener clickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setTitle(title);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.dialog_ok), clickListener);

        alertDialog.show();
    }

    protected void createAlertDialogWithCancelButton(String title, String message, OnClickListener clickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setTitle(title);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.dialog_ok), clickListener);

        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, context.getString(R.string.dialog_cancel), onDialogClickListener);

        alertDialog.show();
    }

    protected void createSpinnerList(OnItemSelectedListener itemSelectedListener, String[] items, int spinnerViewId) {
        Spinner spin = (Spinner) pageView.findViewById(spinnerViewId);
        spin.setOnItemSelectedListener(itemSelectedListener);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, items);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

    }

    protected OnClickListener onDialogClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            // User clicked OK button
        }
    };
    
    protected OnClickListener onDialogClickListenerWithReturnToMainView = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            Intent intent = new Intent(context, MyCalendarMainActivity.class);
            context.startActivity(intent);
        }
    };

}
