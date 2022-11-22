package com.example.myapplication.utils;

import java.text.DecimalFormat;

public class StringUtil {
    public static String formatCurrency(int number) {
        return new DecimalFormat("#,###").format(number);
    }
}