package org.moonila.mycalendar.app.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

public class DateUtils {

    public static String formatDate(long dateTimeStamp) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateFormated = dateFormat.format(dateTimeStamp);

        return dateFormated;
    }

    public static long dateStringToLong(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        long dateTimeStamp = 0;
        try {
            dateTimeStamp = (dateFormat.parse(dateString)).getTime();
        } catch (ParseException e) {
            Log.e("error: ", e.getMessage());
        }

        return dateTimeStamp;
    }

    public static int retrieveCurrentYear() {
        Date d = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int year = c.get(Calendar.YEAR);

        return year;
    }

}
