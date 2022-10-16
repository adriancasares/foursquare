package com.adriancasares.foursquare.base.event;


import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.function.Consumer;
import java.util.logging.Level;

public class EventConsumer<T extends Event> implements Listener {
    private Consumer<T> consumer;
    private EventSupplier parent;
    private Class<T> type;
    private EventPriority priority;

    public EventConsumer(Class<T> type, Consumer<T> consumer, EventPriority priority, EventSupplier parent){
        this.type = type;
        this.consumer = consumer;
        this.parent = parent;
        this.priority = priority;

        try{
            parent.getPlugin().getServer().getPluginManager().registerEvent(type, this, priority, (listener, event) -> {
                if(type.isInstance(event)) //should always be true :)
                    accept((T)event);
            }, parent.getPlugin());
        }
        catch(Exception e){
            parent.getPlugin().getLogger().log(Level.SEVERE, "Failed to dynamically register event", e);
        }
    }

    public void accept(T t){
        consumer.accept(t);
    }

    public void cancel(){
        parent.deregisterConsumer(this);
    }

    public Class<T> getEventType(){
        return type;
    }
}
