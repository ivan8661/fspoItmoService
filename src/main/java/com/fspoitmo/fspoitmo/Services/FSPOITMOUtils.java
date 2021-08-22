package com.fspoitmo.fspoitmo.Services;


public class FSPOITMOUtils {


    public static String getFSPOEven(String even, String odd){
        if(even != null && even.equals("1"))
            return "even";
        if(odd != null && odd.equals("1"))
            return "odd";
        return null;
    }

    public static String getFSPOday(String day) {
        switch (day){
            case "Понедельник":
                return "mon";
            case "Вторник":
                return "tue";
            case "Среда":
                return "wed";
            case "Четверг":
                return "thu";
            case "Пятница":
                return "fri";
            case "Суббота":
                return "sat";
            case "Воскресенье":
                return "sun";
            default:
                return "NAN";
        }
    }



}
