package com.adriancasares.foursquare.base.schedule;

import com.adriancasares.foursquare.base.event.EventConsumer;
import org.bukkit.Bukkit;

import java.util.ArrayList;

public interface ScheduleContainer {

    void registerTask(int taskId);
    void deregisterTasks();

    ArrayList<Integer> getTasks();

}
