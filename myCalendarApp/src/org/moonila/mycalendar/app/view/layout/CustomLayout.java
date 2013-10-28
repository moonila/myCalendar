package org.moonila.mycalendar.app.view.layout;

import org.moonila.mycalendar.app.core.ManageData;
import org.moonila.mycalendar.app.view.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;

public class CustomLayout {

    protected static ManageData manageData;

    protected static Context context;

    protected static View pageView;

    public static void init(Context context, View pageView, ManageData manageData) {
        CustomLayout.context = context;
        CustomLayout.manageData = manageData;
        CustomLayout.pageView = pageView;
    }

    public void createAlertDialog(Context context, String title, String message, OnClickListener clickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setTitle(title);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.dialog_ok), clickListener);

        alertDialog.show();
    }

}
