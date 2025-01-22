package org.example.assetuijavafx.fxml.utils;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class FormHelper {

    public static String formatTextMessage(Exception e) {
        return switch (e) {
            case NumberFormatException ne -> "Invalid input number";
            case InvalidInputException ie -> ie.getMessage();
            default -> "An unexpected error occurred: " + e.getMessage();
        };
    }

    public static void triggerAfterDelay(Runnable action, int delay) {
        PauseTransition pause = new PauseTransition(Duration.seconds(delay));
        pause.setOnFinished(event -> {
            // Trigger the event after 2 seconds
            action.run();
        });
        pause.play();
    }
}
