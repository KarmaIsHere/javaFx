package com.example.lb1_javafx.utils;

public class Validation {

    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }

    public boolean isDouble (String input) {
        try
        {
            Double.parseDouble(input);
            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }
    }
}
