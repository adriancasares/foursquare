package com.adriancasares.foursquare.base;

import com.adriancasares.foursquare.FourSquare;
import com.adriancasares.foursquare.base.event.EventConsumer;
import com.adriancasares.foursquare.base.event.EventContainer;
import com.adriancasares.foursquare.base.schedule.ScheduleContainer;
import com.adriancasares.foursquare.base.map.GameMap;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public abstract class Game implements EventContainer, ScheduleContainer {

    String gameName;
    private UUID gameId;
    private Team team;

    private ArrayList<GameMap> worlds = new ArrayList<>();

    private HashMap<Person, Scoreboard> scoreboards = new HashMap<>();

    private GamePhase currentPhase;

    public Game(String gameName, Team team) {
        this.gameName = gameName;
        this.gameId = UUID.randomUUID();
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

    public void end() {
        onEndPhase();
        onEnd();
    }
    public void onEndPhase() {
        currentPhase.end();
    }

    public void registerWorld(GameMap world) {
        worlds.add(world);
    }

    public void deregisterWorlds() {
        for(GameMap world: worlds) {
//            world.delete();
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

    public boolean inPhase(Class<? extends GamePhase> phase) {
        return phase.isAssignableFrom(currentPhase.getClass());
    }

    public void setScoreboard(Person person, Scoreboard scoreboard) {
        scoreboards.put(person, scoreboard);
    }

    public Scoreboard getScoreboard(Person person) {
        return scoreboards.get(person);
    }

    public UUID getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }
}
