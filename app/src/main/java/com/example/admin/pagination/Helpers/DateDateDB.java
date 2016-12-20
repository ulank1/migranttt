package com.example.admin.pagination.Helpers;

import java.util.Calendar;

/**
 * Created by ulan on 11/15/16.
 */
public class DateDateDB {

   public boolean calendar1(String dateDB){
       Calendar calendar1=Calendar.getInstance();
       Calendar calendar=Calendar.getInstance();
       calendar.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH) - 7);
       int day=calendar.get(Calendar.DAY_OF_MONTH);
       int month=calendar.get(Calendar.MONTH);
       int year=calendar.get(Calendar.YEAR);
       String date=day+"."+month+"."+year;
       if (dateDB.equals("ss")){
           return true;
       }else {
           return date.equals(dateDB);
       }

   }
}
