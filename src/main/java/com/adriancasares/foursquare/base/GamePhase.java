package com.adriancasares.foursquare.base;

import com.adriancasares.foursquare.base.event.EventConsumer;
import com.adriancasares.foursquare.base.event.EventContainer;
import com.adriancasares.foursquare.base.schedule.ScheduleContainer;
import org.bukkit.Bukkit;

import java.util.ArrayList;

public abstract class GamePhase implements EventContainer, ScheduleContainer {

    private Game parent;

    private String name;

    public GamePhase(String name, Game parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public abstract void onStart();
    public abstract void onEnd();

    public abstract void onPlayerJoin();
    public abstract void onPlayerLeave();

    public abstract void onSpectatorJoin();
    public abstract void onSpectatorLeave();

    public void end() {
        onEnd();
        deregisterEvents();
        deregisterTasks();
    }

    public Game getParent() {
        return parent;
    }
}
