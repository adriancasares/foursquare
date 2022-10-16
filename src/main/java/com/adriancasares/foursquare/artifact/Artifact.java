package com.adriancasares.foursquare.artifact;

import com.adriancasares.foursquare.FourSquare;
import com.adriancasares.foursquare.base.Game;
import com.adriancasares.foursquare.base.Team;
import com.adriancasares.foursquare.base.event.EventConsumer;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerChatEvent;

public class Artifact extends Game {

    public Artifact(Team team) {
        super(team);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onEnd() {
        deregisterEvents();
    }

    @Override
    public void onPlayerJoin() {
        System.out.println("join");
    }

    @Override
    public void onPlayerLeave() {
        System.out.println("leave");
    }

    @Override
    public void onSpectatorJoin() {
        System.out.println("spec join");
    }

    @Override
    public void onSpectatorLeave() {
        System.out.println("spec leave");
    }
}
