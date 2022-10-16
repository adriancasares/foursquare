package com.adriancasares.foursquare.base;

import com.adriancasares.foursquare.FourSquare;
import com.adriancasares.foursquare.base.event.EventConsumer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public abstract class Game {

    private Team team;

    private ArrayList<EventConsumer> events = new ArrayList<>();

    public Game(Team team) {

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

}
