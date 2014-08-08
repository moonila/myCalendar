package org.moonila.mycalendar.app.core;

import java.util.List;

import org.moonila.mycalendar.app.bean.FirstDay;
import org.moonila.mycalendar.app.dao.DatabaseHandler;
import org.moonila.mycalendar.app.utils.DateUtils;

import android.content.Context;

public class ManageData {

    private static final long ONE_HOUR = 1000 * 60 * 60;// //1000 millisecondes
    // * 60 secondes * 60
    // minutes = 1 heure

    private static final long ONE_DAY = ONE_HOUR * 24; // 1 heure * 24 = 1 jour

    private static Context context;

    private static int currentYear;

    private DatabaseHandler db = DatabaseHandler.getInstance(context);

    private static class ManageDataHolder {
        private final static ManageData instance = new ManageData();
    }

    public static ManageData getInstance(Context context) {
        ManageData.context = context;
        currentYear = DateUtils.retrieveCurrentYear();
        return ManageDataHolder.instance;
    }

    public String[] createListYears() {

        String[] listYears = new String[5];
        listYears[0] = String.valueOf(currentYear);
        for (int i = 0; i < 4; i++) {
            int oldDate = currentYear - (i + 1);
            listYears[i + 1] = String.valueOf(oldDate);
        }
        return listYears;
    }

    public long calculAverage(int year) {

        List<FirstDay> allDate = db.getAllDateForCurrentYear(year);

        long average = 0;
        for (int i = 0; i < allDate.size(); i++) {
            if (i > 0) {
                average += (allDate.get(i - 1).getDateTimeStamp() - allDate.get(i).getDateTimeStamp());
            }
        }

        if (allDate.size() > 0) {
        	int days = allDate.size() - 1;
            return (average / days) / ONE_DAY;
        } else {
            return 0;
        }
    }

    public String calculProbablyDate(long average, long lastDateTime) {

        long dateProbably = lastDateTime + (average * ONE_DAY);

        return DateUtils.formatDate(dateProbably);
    }

    public FirstDay getLastDate() {
        return db.getLastDate();
    }

    public List<FirstDay> getAllDateForCurrentYear() {
        return db.getAllDateForCurrentYear(currentYear);
    }

    public void deleteAll() {
        db.deleteAll();
    }

    public void deleteAllByYear(int year) {
        List<FirstDay> allDaysWillBeDeleted = db.getAllDateForCurrentYear(year);
        for (FirstDay day : allDaysWillBeDeleted) {
            db.deleteDateById(day);
        }
    }

    public int deleteBySpecificDate(String date) {
        FirstDay firstDay = db.getDateByDate(date);
        if (firstDay != null) {
            db.deleteDateById(firstDay);
            return 1;
        } else {
            return 0;
        }

    }

    public boolean addDate(FirstDay firstDay) {
        return db.addDate(firstDay);
    }

}
