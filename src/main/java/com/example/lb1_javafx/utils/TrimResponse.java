package com.example.lb1_javafx.utils;

public  class TrimResponse {

    public static String trimJSON(String s)
    {
        s = s.replace('[',' ');
        s = s.replace(']',' ');
        return s;
    }
}
