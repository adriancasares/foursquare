package com.adriancasares.foursquare.base;

public abstract class Game {

    private Team team;

    public Game(Team team) {

    }
    public abstract void onStart();

    public abstract void onEnd();

    public abstract void onPlayerJoin();

    public abstract void onPlayerLeave();

    public Team getTeam() {
        return team;
    }
}
