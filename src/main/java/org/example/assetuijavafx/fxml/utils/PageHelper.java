package org.example.assetuijavafx.fxml.utils;

public class PageHelper {

    public static String formatPageName(Object page) {
        String pageName = page.getClass().getSimpleName();
        if (pageName.startsWith("TO")) {
            return pageName.substring(2); // Remove the first two characters ("TO")
        } else {
            return pageName;
        }
    }
}
