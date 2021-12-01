/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class XDate {

    static final SimpleDateFormat DATE_FORMATER = new SimpleDateFormat("MM/dd/yyyy");

    public static Date toDate(String date, String... pattern) {
        try {
            if (pattern.length > 0) {

                DATE_FORMATER.applyPattern(pattern[0]);
            }
            return DATE_FORMATER.parse(date);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String toString(Date date, String... pattern) {
        if (pattern.length > 0) {
            DATE_FORMATER.applyPattern(pattern[0]);
        }
        if (date == null) {
            date = XDate.toDate(XDate.now(), pattern);
        }
        return DATE_FORMATER.format(date);
    }
    
//    public static String toString(Date date, String pattern){
//        DATE_FORMATER.applyPattern(pattern);
//        return DATE_FORMATER.format(date);
//    }
    public static String now() {
        return XDate.toString(new Date());
    }

    public static Date addDays(Date date, int days) {
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        return date;
    }

    public static Date add(int days) {
        Date now = XDate.toDate(XDate.now());
        now.setTime(now.getTime() + days * 24 * 60 * 60 * 1000);
        return now;
    }
    public static Date now1() {
            return new Date();
        }


}
