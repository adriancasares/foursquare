package com.adriancasares.foursquare.base.event;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class EventSupplier {
    private Plugin plugin;

    public EventSupplier(Plugin plugin){
        this.plugin = plugin;
    }
    public <T extends Event> EventConsumer<T> registerConsumer(Class<T> type, EventPriority priority, Consumer<T> consumer){
        EventConsumer<T> newConsumer = new EventConsumer<>(type, consumer, priority,this);
        return newConsumer;
    }

    public <T extends Event> EventConsumer<T> registerConsumer(Class<T> type, Consumer<T> consumer){
        return registerConsumer(type, EventPriority.NORMAL, consumer);
    }

    public void deregisterConsumer(EventConsumer<?> consumer){
        HandlerList.unregisterAll(consumer); // unregister from Bukkit
    }

    public Plugin getPlugin() {
        return plugin;
    }
}