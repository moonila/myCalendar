package org.moonila.mycalendar.app.view.application;

import android.app.Application;

public class MyCalendarApplication extends Application {

	private static class MyCalendarApplicationHolder {
		private final static MyCalendarApplication instance = new MyCalendarApplication();
	}

	public static MyCalendarApplication getInstance() {

		return MyCalendarApplicationHolder.instance;
	}

}
