package com.adriancasares.foursquare.base.schedule;

import com.adriancasares.foursquare.base.event.EventConsumer;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ScheduleContainer {

    // Map of the tasks stored by each object implementing ScheduleContainer
    HashMap<Object, List<Integer>> tasks = new HashMap<>();

    default void registerTask(int taskId) {
        getTasks().add(taskId);
    }
    default void deregisterTasks() {
        List<Integer> result = tasks.get(this);

        if(result != null){
            for(int taskId : result){
                Bukkit.getScheduler().cancelTask(taskId);
            }
        }

        tasks.remove(this);
    }
    default List<Integer> getTasks() {
        List<Integer> result = tasks.get(this);

        if(result == null){
            result = new ArrayList<>();
            tasks.put(this, result);
        }

        return result;
    }

}