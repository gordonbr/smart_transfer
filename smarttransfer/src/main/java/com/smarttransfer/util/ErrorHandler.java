package com.smarttransfer.util;
import io.javalin.Context;

/**
 * Created by jonathasalves on 29/01/2019.
 */
public class ErrorHandler {

    public static boolean isAccountNumberValid(String number) {

        try {
            if(Long.parseLong(number) <= 0) return false;
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static boolean isBalanceValueValid(String number) {

        try {
            if(Double.parseDouble(number) <= 0) return false;
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
