package com.wifiyou.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author dengzhiren@wifiyou.net (Dean)
 *         6/11/16
 */
public class DateUtils {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static String getTodayDateAsString() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(new Date());
    }

    public static String getDateAsString(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(new Date(date));
    }

    public static String getTimeAsString(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        return sdf.format(new Date(date));
    }

    public static long getDateTime(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.parse(date).getTime();
    }

    public static int subtractionUnitAsDay(String now, String compare) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date nowDate = sdf.parse(now);
            Date compareDate = sdf.parse(compare);
            return (int) ((nowDate.getTime() - compareDate.getTime()) / (1000 * 60 * 60 * 24));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getTimeStamp(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date date = sdf.parse(dateString);
        return date.getTime();
    }


}
