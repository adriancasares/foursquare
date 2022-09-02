package com.adriancasares.foursquare.base;

import org.bukkit.entity.Player;

public class Person {

    private Player player;

    private boolean isPlayer;

    private boolean online;

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
}
