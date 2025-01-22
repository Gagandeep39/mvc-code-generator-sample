package org.example.assetuijavafx.fxml.utils;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * A custom event class to signal a page switch event in a JavaFX application.
 */
public class PageSwitchEvent<T> extends Event {

    public static final EventType<PageSwitchEvent> PAGE_SWITCH = new EventType<>(Event.ANY, "PAGE_SWITCH");
    private final String page;
    private final T data;

    /**
     * Creates a new PageSwitchEvent instance with the specified page and data.
     *
     * @param page the name of the page being switched to
     * @param data any data associated with the page switch event
     */
    public PageSwitchEvent(String page, T data) {
        super(PAGE_SWITCH);
        this.page = page;
        this.data = data;
    }

    /**
     * Creates a new PageSwitchEvent instance with the specified page and null data.
     * Use this constructor when no data needs to be passed.
     *
     * @param page the name of the page being switched to
     */
    public PageSwitchEvent(String page) {
        this(page, null);
    }

    /**
     * Gets the name of the page being switched to.
     *
     * @return the page name
     */
    public String getPage() {
        return page;
    }

    /**
     * Gets the data associated with the page switch event.
     *
     * @return the event data, or null if no data was provided
     */
    public T getData() {
        return data;
    }

}
