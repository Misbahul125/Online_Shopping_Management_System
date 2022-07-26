/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Misbahul Haque
 */
public class DateHelper {

    public DateHelper() {
    }
    
    public String getOrderDate() {

        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DAY_OF_MONTH);

        String dayNumberSuffix = getDayNumberSuffix(date);
        
        DateFormat df1 = new SimpleDateFormat("dd'" + dayNumberSuffix + " of" + "' MMMM, yyy");

        String orderDate = df1.format(calendar.getTime());
        
        return orderDate;
        
    }

    public String getDeliveryDate() {

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE, 8);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        //System.out.println(i);
        String dayNumberSuffix = getDayNumberSuffix(date);
        //System.out.println(dayNumberSuffix);

        DateFormat df1 = new SimpleDateFormat("dd'" + dayNumberSuffix + " of" + "' MMMM, yyy");

        //System.out.println(calendar.getTime());
        String deliveryDate1 = df1.format(calendar.getTime());

        
        calendar.add(Calendar.DATE, 7);
        date = calendar.get(Calendar.DAY_OF_MONTH);
        //System.out.println(i);
        dayNumberSuffix = getDayNumberSuffix(date);
        //System.out.println(dayNumberSuffix);

        DateFormat df2 = new SimpleDateFormat("dd'" + dayNumberSuffix + " of" + "' MMMM, yyy");
        
        //System.out.println(calendar.getTime());
        String deliveryDate2 = df2.format(calendar.getTime());

        String fullDeliverydate = deliveryDate1 + " - " + deliveryDate2;

        //System.out.println(fullDeliverydate);

        return fullDeliverydate;

    }

    private String getDayNumberSuffix(int day) {
        String s = "";
        if (day >= 11 && day <= 13) {
            s = "th";
            return s;
        }
        switch (day % 10) {
            case 1:
                s = "st";
                break;
            case 2:
                s = "nd";
                break;
            case 3:
                s = "rd";
                break;
            default:
                s = "th";
        }

        return s;
    }

}