package com.adriancasares.foursquare.base;

import com.adriancasares.foursquare.base.event.EventConsumer;
import com.adriancasares.foursquare.base.event.EventContainer;
import com.adriancasares.foursquare.base.schedule.ScheduleContainer;
import org.bukkit.Bukkit;

import java.util.ArrayList;

public abstract class GamePhase implements EventContainer, ScheduleContainer {

    private Game parent;

    private String name;

    private ArrayList<EventConsumer> events = new ArrayList<>();

    private ArrayList<Integer> tasks = new ArrayList<>();

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

    @Override
    public void registerEvent(EventConsumer consumer) {
        events.add(consumer);
    }

    @Override
    public void deregisterEvents() {
        events.forEach(EventConsumer::cancel);
    }

    @Override
    public ArrayList<EventConsumer> getEvents() {
        return events;
    }

    @Override
    public void registerTask(int taskId) {
        tasks.add(taskId);
    }

    @Override
    public void deregisterTasks() {
        for(int taskId : tasks) {
            Bukkit.getScheduler().cancelTask(taskId);
        }
    }

    @Override
    public ArrayList<Integer> getTasks() {
        return tasks;
    }

    public Game getParent() {
        return parent;
    }
}
