package org.moonila.mycalendar.app.view.layout;

import org.moonila.mycalendar.app.core.ManageData;
import org.moonila.mycalendar.app.view.R;
import org.moonila.mycalendar.app.view.activity.MyCalendarMainActivity;
import org.moonila.mycalendar.app.view.component.CustomDateTextViewLink;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class DeleteDateLayout extends CustomLayout {

    private static int deleteDateViewId;

    private static CustomDateTextViewLink editDate;

    private String yearSelected;

    private static class DeleteDateLayoutHolder {
        private final static DeleteDateLayout instance = new DeleteDateLayout();
    }

    public static DeleteDateLayout getInstance(Context context, View pageView, ManageData manageData) {
        init(context, pageView, manageData);
        return DeleteDateLayoutHolder.instance;
    }

    public void createDeleteDateLayout() {

        RadioButton deleteAll = (RadioButton) pageView.findViewById(R.id.deleteAll);
        deleteAll.setOnClickListener(onSelect);
        deleteDateViewId = R.id.deleteAll;

        createSpinnerList();

        RadioButton deleteByDate = (RadioButton) pageView.findViewById(R.id.deleteByDate);
        deleteByDate.setOnClickListener(onSelect);

        RadioButton deleteOne = (RadioButton) pageView.findViewById(R.id.deleteOne);
        deleteOne.setOnClickListener(onSelect);

        editDate = (CustomDateTextViewLink) pageView.findViewById(R.id.dateModifLink);

        ImageButton save = (ImageButton) pageView.findViewById(R.id.validate);
        save.setOnClickListener(onDelete);

    }

    private void createSpinnerList() {
        final String[] items = manageData.createListYears();

        Spinner spin = (Spinner) pageView.findViewById(R.id.spinnerSelectedYear);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View v, int position, long id) {
                yearSelected = items[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                yearSelected = items[0];
            }
        });
        ArrayAdapter<String> aa = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, items);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

    }

    private View.OnClickListener onDelete = new View.OnClickListener() {
        public void onClick(View v) {

            if (v.getId() == R.id.validate) {
                if (deleteDateViewId == R.id.deleteAll) {
                    createAlertDialog(context,
                                      context.getString(R.string.dialog_title_warning),
                                      context.getString(R.string.dialog_message_deleted_all_dates),
                                      onClickListener());
                } else if (deleteDateViewId == R.id.deleteByDate) {
                    manageData.deleteAllByYear(Integer.valueOf(yearSelected));
                    Intent intent = new Intent(context, MyCalendarMainActivity.class);
                    context.startActivity(intent);
                } else if (deleteDateViewId == R.id.deleteOne) {
                    manageData.deleteBySpecificDate(editDate.getText().toString());
                    Intent intent = new Intent(context, MyCalendarMainActivity.class);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, MyCalendarMainActivity.class);
                    // start the second Activity
                    context.startActivity(intent);
                }
            }
        }
    };

    private OnClickListener onClickListener() {
        OnClickListener clickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                manageData.deleteAll();

                Intent intent = new Intent(context, MyCalendarMainActivity.class);
                context.startActivity(intent);
            }
        };

        return clickListener;
    }

    private View.OnClickListener onSelect = new View.OnClickListener() {
        public void onClick(View v) {
            RelativeLayout deleteByDateLayout = (RelativeLayout) pageView.findViewById(R.id.deleteByDateLayout);
            LinearLayout txtView2 = (LinearLayout) pageView.findViewById(R.id.deleteByOneDateLayout);
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
}
