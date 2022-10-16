package com.adriancasares.foursquare.base.event;

import java.util.ArrayList;

public interface EventContainer {

    void registerEvent(EventConsumer consumer);
    void deregisterEvents();
    ArrayList<EventConsumer> getEvents();
}
