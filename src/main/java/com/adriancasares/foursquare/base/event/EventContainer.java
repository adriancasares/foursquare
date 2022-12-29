package com.adriancasares.foursquare.base.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface EventContainer {

    // Map of the EventConsumers stored by each object implementing EventContainer
    HashMap<Object, List<EventConsumer>> eventConsumers = new HashMap<>();

    default void registerEvent(EventConsumer consumer) {
        getEvents().add(consumer);
    }
    default void deregisterEvents() {
        List<EventConsumer> result = eventConsumers.get(this);

        if(result != null){
            for(EventConsumer cons : result){
                cons.cancel();
            }
        }

        eventConsumers.remove(this);
    }
    default List<EventConsumer> getEvents() {
        List<EventConsumer> result = eventConsumers.get(this);

        if(result == null){
            result = new ArrayList<>();
            eventConsumers.put(this, result);
        }

        return result;
    }
}
