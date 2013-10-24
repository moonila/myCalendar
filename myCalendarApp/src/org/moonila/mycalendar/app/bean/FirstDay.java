package org.moonila.mycalendar.app.bean;

public class FirstDay {

    private int id;
    private long dateTimeStamp;
    private String dateformated;

    public FirstDay() {

    }

    public FirstDay(int id, long dateTimeStamp) {
        this.id = id;
        this.dateTimeStamp = dateTimeStamp;
    }

    public FirstDay(String dateformated) {
        this.dateformated = dateformated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDateTimeStamp() {
        return dateTimeStamp;
    }

    public void setDateTimeStamp(long dateTimeStamp) {
        this.dateTimeStamp = dateTimeStamp;
    }

    public String getDateformated() {
        return dateformated;
    }

    public void setDateformated(String dateformated) {
        this.dateformated = dateformated;
    }

}
