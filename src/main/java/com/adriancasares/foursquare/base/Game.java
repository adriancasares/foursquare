package com.adriancasares.foursquare.base;

import com.adriancasares.foursquare.FourSquare;
import com.adriancasares.foursquare.base.event.EventConsumer;
import com.adriancasares.foursquare.base.event.EventContainer;
import com.adriancasares.foursquare.base.schedule.ScheduleContainer;
import com.adriancasares.foursquare.base.world.WorldWrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Game implements EventContainer, ScheduleContainer {

    private Team team;

    private ArrayList<EventConsumer> events = new ArrayList<>();

    private ArrayList<WorldWrapper> worlds = new ArrayList<>();

    private ArrayList<Integer> tasks = new ArrayList<>();

    private HashMap<Person, Scoreboard> scoreboards = new HashMap<>();

    private GamePhase currentPhase;

    public Game(Team team) {
        this.team = team;

        EventConsumer<PlayerJoinEvent> joinEvent = FourSquare.fs().getEventSupplier().registerConsumer(PlayerJoinEvent.class, (e) -> {
            Player player = e.getPlayer();

            if(team.containsPlayer(player)) {
                onPlayerJoin();
            }
            else {
                onSpectatorJoin();
            }
        });

        registerEvent(joinEvent);

        EventConsumer<PlayerQuitEvent> quitEvent = FourSquare.fs().getEventSupplier().registerConsumer(PlayerQuitEvent.class, (e) -> {
            Player player = e.getPlayer();

            if(team.containsPlayer(player)) {
                onPlayerLeave();
            }
            else {
                onSpectatorLeave();
            }
        });

        registerEvent(joinEvent);
    }
    public abstract void onStart();

    public abstract void onEnd();

    public abstract void onPlayerJoin();

    public abstract void onPlayerLeave();

    public abstract void onSpectatorJoin();

    public abstract void onSpectatorLeave();

    public Team getTeam() {
        return team;
    }

    public void registerEvent(EventConsumer consumer) {
        events.add(consumer);
    }

    public void deregisterEvents() {
        for(EventConsumer event : events) {
            event.cancel();
        }
    }

    public void end() {
        onEndPhase();
        onEnd();
    }
    public void onEndPhase() {
        currentPhase.end();
    }

    public void registerWorld(WorldWrapper world) {
        worlds.add(world);
    }

    public void deregisterWorlds() {
        for(WorldWrapper world: worlds) {
            world.delete();
        }
    }

    public GamePhase getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(GamePhase currentPhase) {
        if(this.currentPhase != null) {
            this.currentPhase.end();
        }

        this.currentPhase = currentPhase;

        this.currentPhase.onStart();
    }

    public boolean inPhase(String phaseName) {
        return currentPhase.getName().equals(phaseName);
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
        System.out.println("Cancelling tasks, size: " + tasks.size());
        for(int taskId : tasks) {
            Bukkit.getScheduler().cancelTask(taskId);
        }
    }

    @Override
    public ArrayList<Integer> getTasks() {
        return tasks;
    }

    public void setScoreboard(Person person, Scoreboard scoreboard) {
        scoreboards.put(person, scoreboard);
    }

    public Scoreboard getScoreboard(Person person) {
        return scoreboards.get(person);
    }
}
