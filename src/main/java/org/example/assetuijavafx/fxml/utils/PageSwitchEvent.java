package org.example.assetuijavafx.fxml.utils;

import javafx.event.Event;
import javafx.event.EventType;

public class PageSwitchEvent<T> extends Event {

    public static final EventType<PageSwitchEvent> PAGE_SWITCH = new EventType<>(Event.ANY, "PAGE_SWITCH");
    private final String page;
    private final T data;

    public PageSwitchEvent(String page, T data) {
        super(PAGE_SWITCH);
        this.page = page;
        this.data = data;
    }

    public PageSwitchEvent(String page) {
        super(PAGE_SWITCH);
        this.page = page;
        this.data = null;
    }

    public String getPage() {
        return page;
    }

    public T getData() {
        return data;
    }

}
