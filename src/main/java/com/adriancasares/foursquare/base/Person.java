package com.adriancasares.foursquare.base;

import com.adriancasares.foursquare.base.util.FSColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Person {

    private Player player;

    private boolean isPlayer;

    private boolean online;

    private UUID uuid;

    private String latestName;

    private int index;
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public void setPlayer(boolean player) {
        isPlayer = player;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isInGame() {
        return isPlayer && online;
    }

    public Person(Player player, boolean isPlayer, int index) {
        this.player = player;
        this.isPlayer = isPlayer;
        this.uuid = player.getUniqueId();
        this.latestName = player.getName();
        this.online = player.isOnline();
        this.index = index;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getLatestName() {
        return latestName;
    }

    public void setLatestName(String latestName) {
        this.latestName = latestName;
    }

    public int getIndex() {
        return index;
    }

    public FSColor getColor() {
        if(index == -1) {
            return FSColor.SPECTATOR;
        }
        else if(index == 0) {
            return FSColor.TEAM1;
        } else if(index == 1) {
            return FSColor.TEAM2;
        } else if(index == 2) {
            return FSColor.TEAM3;
        } else {
            return FSColor.TEAM4;
        }
    }
}
