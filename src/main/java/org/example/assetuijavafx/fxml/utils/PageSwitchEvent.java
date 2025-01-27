package org.example.assetuijavafx.fxml.utils;

import javafx.event.Event;
import javafx.event.EventType;
import org.example.assetuijavafx.model.NavigationState;

/**
 * A custom event class to signal a page switch event in a JavaFX application.
 */
public class PageSwitchEvent extends Event {

    public static final EventType<PageSwitchEvent> PAGE_SWITCH = new EventType<>(Event.ANY, "PAGE_SWITCH");
    private NavigationState<?> navigationState = null;

    public PageSwitchEvent(NavigationState<?> state) {
        super(PAGE_SWITCH);
        navigationState = state;
    }

    public NavigationState<?> getNavigationState() {
        return navigationState;
    }
}
