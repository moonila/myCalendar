package org.moonila.mycalendar.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.moonila.mycalendar.app.bean.FirstDay;
import org.moonila.mycalendar.app.utils.DateUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "myCalendarManager";

    // Dates table name
    private static final String TABLE_DATES = "dates";

    // Dates Table Columns names
    private static final String KEY_DATES_ID = "id";
    private static final String KEY_DATE = "date";

    private static Context context;

    private static class DatabaseHandlerHolder {
        private final static DatabaseHandler instance = new DatabaseHandler();
    }

    public static DatabaseHandler getInstance(Context context) {
        DatabaseHandler.context = context;
        return DatabaseHandlerHolder.instance;
    }

    public DatabaseHandler() {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_DATES + "(" + KEY_DATES_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " INTEGER)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATES);

        // Create tables again
        onCreate(db);
    }

    // Adding new mentrus
    public boolean addDate(FirstDay firstDay) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        long dateTimeStamp = DateUtils.dateStringToLong(firstDay.getDateformated());
        if (dateTimeStamp != 0) {
            values.put(KEY_DATE, dateTimeStamp);
            db.insert(TABLE_DATES, null, values);
            db.close();
            return false;
        } else {
            return true;
        }
    }

    // Getting single mentrus
    public FirstDay getDate(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DATES,
                                 new String[] { KEY_DATES_ID, KEY_DATE },
                                 KEY_DATES_ID + "=?",
                                 new String[] { String.valueOf(id) },
                                 null,
                                 null,
                                 null,
                                 null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        FirstDay firstDay = createFirstDayObject(cursor);

        return firstDay;
    }
    
    public FirstDay getDateByDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        long dateTimeStamp = DateUtils.dateStringToLong(date);
        Cursor cursor = db.query(TABLE_DATES,
                                 new String[] { KEY_DATES_ID, KEY_DATE },
                                 KEY_DATE + "=?",
                                 new String[] { String.valueOf(dateTimeStamp) },
                                 null,
                                 null,
                                 null,
                                 null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        FirstDay firstDay = createFirstDayObject(cursor);

        return firstDay;
    }

    public FirstDay getLastDate() {
        String selectQuery = "SELECT * FROM " + TABLE_DATES + " ORDER BY " + KEY_DATE + " DESC LIMIT 1";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();

        }

        if (cursor.getCount() > 0) {
            FirstDay firstDay = createFirstDayObject(cursor);
            return firstDay;
        } else {
            return null;
        }

    }
    
    

    // Getting All mentrus
    public List<FirstDay> getAllDates() {
        String selectQuery = "SELECT  * FROM " + TABLE_DATES + " ORDER BY " + KEY_DATE + " DESC";

        List<FirstDay> allDates = retrieveFirstDayList(selectQuery);

        Log.d("getAllDates: ", "allDates size : " + String.valueOf(allDates.size()));
        return allDates;
    }

    public List<FirstDay> getAllDateForCurrentYear(int year) {

        String selectQuery = createQueryBetweenDate(String.valueOf(year));

        List<FirstDay> allDates = retrieveFirstDayList(selectQuery);

        Log.d("getAllDateForCurrentYear: ", "currentYear : " + year + ", allDates size : " + String.valueOf(allDates.size()));
        return allDates;
    }

    public List<FirstDay> getAllDatesForASpecificYear(String year) {
        String selectQuery = createQueryBetweenDate(year);

        List<FirstDay> allDates = retrieveFirstDayList(selectQuery);

        Log.d("getAllDatesForASpecificYear: ", "year : " + year + ", allDates size : " + String.valueOf(allDates.size()));
        return allDates;
    }

    // Updating single date
    public int updateDate(FirstDay firstDay) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, firstDay.getDateTimeStamp());

        // updating row
        return db.update(TABLE_DATES, values, KEY_DATES_ID + " = ?", new String[] { String.valueOf(firstDay.getId()) });
    }

    // Deleting single date by id
    public void deleteDateById(FirstDay firstDay) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DATES, KEY_DATES_ID + " = ?", new String[] { String.valueOf(firstDay.getId()) });
        db.close();
    }

    // Deleting single date by date
    public void deleteMentrusByDate(FirstDay firstDay) {
        SQLiteDatabase db = this.getWritableDatabase();
        long dateTimeStamp = DateUtils.dateStringToLong(firstDay.getDateformated());
        db.delete(TABLE_DATES, KEY_DATE + " = ?", new String[] { String.valueOf(dateTimeStamp) });
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DATES, null, null);
        db.close();
    }

    private List<FirstDay> retrieveFirstDayList(String selectQuery) {
        List<FirstDay> allDates = new ArrayList<FirstDay>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FirstDay firstDay = createFirstDayObject(cursor);

                // Adding dates to list
                allDates.add(firstDay);
            } while (cursor.moveToNext());
        }
        return allDates;
    }

    private FirstDay createFirstDayObject(Cursor cursor) {

        long dateTimeStamp = Long.parseLong(cursor.getString(1));

        FirstDay firstDay = new FirstDay(Integer.parseInt(cursor.getString(0)), dateTimeStamp);
        firstDay.setDateformated(DateUtils.formatDate(dateTimeStamp));

        return firstDay;
    }

    private String createQueryBetweenDate(String year) {
        long dateBefore = DateUtils.dateStringToLong("01/01/" + year);
        long dateAfter = DateUtils.dateStringToLong("31/12/" + year);

        String selectQuery = "SELECT  * FROM " + TABLE_DATES + " where " + KEY_DATE + " BETWEEN '" + dateBefore + "' AND '" + dateAfter
                + "' ORDER BY " + KEY_DATE + " DESC";
        return selectQuery;
    }
}
