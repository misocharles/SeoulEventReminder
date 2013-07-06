package com.hasfun.seoulsubway.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * date util -> java 의 Date 타입은 요기 util 이외에서는 사용 하지않도록 하기 위해 만들어진 util로서 
 * date에 대한 계산은 모두 이 util을 통해서만 처리 한다.
 * @author interwater
 * 
 */
public class DateUtil {
	
	/** The org date format. */
	public static String orgDateFormat = "yyyy-MM-dd";
	
	/** The date format. */
	public static String dateFormat = "yyyy-MM-dd";
	
	/** The sdf. */
	public static SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	

	/**
	 * dateformat 이 org와  틀리면 new sdf.
	 */
	public static void checkSdf() {
		if(!DateUtil.dateFormat.equals(DateUtil.orgDateFormat)) {
			sdf = new SimpleDateFormat(dateFormat);
			orgDateFormat = dateFormat; 
		}
	}

	/**
	 * Gets the org date format.
	 *
	 * @return the org date format
	 */
	public static String getOrgDateFormat() {
		return orgDateFormat;
	}

	/**
	 * Gets the date format.
	 *
	 * @return the date format
	 */
	public static String getDateFormat() {
		return dateFormat;
	}

	/**
	 * Sets the date format.
	 *
	 * @param dateFormat the new date format
	 */
	public static void setDateFormat(String dateFormat) {
		DateUtil.dateFormat = dateFormat;
	}
	
	

	/**
	 * date format 에 맞춰서 millis 값을 reuturn 한다.
	 *
	 * @param date the date
	 * @return TimeMillis (long)
	 */
	public static long dateToMillis(String date){
		checkSdf();
		return sdf.getCalendar().getTimeInMillis();
	}
	
	/**
	 * Millis to date.
	 *
	 * @param millis the millis
	 * @return the string
	 */
	public static String millisToDate(long millis){
		checkSdf();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * Adds the hour.
	 *
	 * @param date the date
	 * @param hour the hour
	 * @return the string
	 * @throws ParseException the parse exception
	 */
	public static String addHour(String date, int hour) throws ParseException {
		checkSdf();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(date));
		calendar.add(Calendar.HOUR, hour);
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * Adds the minute.
	 *
	 * @param date the date
	 * @param minute the minute
	 * @return the string
	 * @throws ParseException the parse exception
	 */
	public static String addMinute(String date, int minute) throws ParseException {
		checkSdf();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(date));
		calendar.add(Calendar.MINUTE, minute);
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * Adds the day.
	 *
	 * @param date the date
	 * @param day the day
	 * @return the string
	 * @throws ParseException the parse exception
	 */
	public static String addDay(String date, int day) throws ParseException {
		checkSdf();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(date));
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * Adds the week.
	 *
	 * @param date the date
	 * @param week the week
	 * @return the string
	 * @throws ParseException the parse exception
	 */
	public static String addWeek(String date, int week) throws ParseException {
		checkSdf();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(date));
		calendar.add(Calendar.WEEK_OF_MONTH, week);
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * Adds the month.
	 *
	 * @param date the date
	 * @param month the month
	 * @return the string
	 * @throws ParseException the parse exception
	 */
	public static String addMonth(String date, int month) throws ParseException {
		checkSdf();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(date));
		calendar.add(Calendar.MONTH, month);
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * Adds the year.
	 *
	 * @param date the date
	 * @param year the year
	 * @return the string
	 * @throws ParseException the parse exception
	 */
	public static String addYear(String date, int year) throws ParseException {
		checkSdf();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(date));
		calendar.add(Calendar.YEAR, year);
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * Gets the week.(sun : 1, mon : 2 ....)  
	 *
	 * @param date the date
	 * @return the week
	 * @throws ParseException the parse exception
	 */
	public static int getWeek(String date) throws ParseException {
		checkSdf();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(date));
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	
	/**
	 * if firstDate > secondDate -> 1
	 * if firstDate == secondDate -> 0
	 * if firstDate < secondDate -> -1
	 * @param firstDate
	 * @param secondDate
	 * @return
	 * @throws ParseException
	 */
	public static int compareDate(String firstDate, String secondDate) throws ParseException {
		checkSdf();
		return sdf.parse(firstDate).compareTo(sdf.parse(secondDate));
	}
}
