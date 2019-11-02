package com.neuedu.utils;


import com.mysql.jdbc.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class DateUtils {
    private static final  String FORMAT_DEFAULT="yyyy-MM-dd HH:mm:ss";
    /**
     * 将字符串的日期转成date
     * */
    public static Date strToData(String dateTimeStr,String formatStr){
        DateTimeFormatter dateTimeFormatter= DateTimeFormat.forPattern(formatStr);
        DateTime dateTime=dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }
    public static Date strToData(String dateTimeStr){
        DateTimeFormatter dateTimeFormatter= DateTimeFormat.forPattern(FORMAT_DEFAULT);
        DateTime dateTime=dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }
    /**
     * 将date转字符串
     * */
    public static String dateToStr(Date date,String formatStr){
        if (date==null){
            return null;
        }
        DateTime dateTime=new DateTime(date);
        return dateTime.toString(formatStr);
    }
    public static String dateToStr(Date date){
        if (date==null){
            return null;
        }
        DateTime dateTime=new DateTime(date);
        return dateTime.toString(FORMAT_DEFAULT);
    }
}
