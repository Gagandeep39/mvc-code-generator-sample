package org.example.assetuijavafx.utilities;

import java.util.regex.Pattern;

public class ValidationHelper {
    public static boolean isValidString(String string) {
        return !string.isEmpty();
    }

    public static boolean isValidNumber(String num) {
        String regex = "[-+]?\\d*(\\.\\d+)?";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(String.valueOf(num)).matches();
    }
}
