package com.adriancasares.foursquare.artifact;

import com.adriancasares.foursquare.base.Game;
import com.adriancasares.foursquare.base.Team;
import com.adriancasares.foursquare.base.world.WorldWrapper;
import org.bukkit.Bukkit;

public class Artifact extends Game {

    public Artifact(Team team) {
        super(team);
    }

    private WorldWrapper world;

    @Override
    public void onStart() {

        world = new WorldWrapper("artifact", null, true);

        setCurrentPhase(new ArtifactStarting(this));
        world.create();

        registerWorld(world);

        getTeam().getPlayers().forEach((player) -> {
            player.getPlayer().teleport(world.getWorld().getSpawnLocation());
        });

    }

    @Override
    public void onEnd() {
        deregisterEvents();
        deregisterWorlds();
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

    public WorldWrapper getWorld() {
        return world;
    }
}
