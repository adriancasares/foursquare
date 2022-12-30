package com.adriancasares.foursquare.artifact;

import com.adriancasares.foursquare.FourSquare;
import com.adriancasares.foursquare.base.GamePhase;
import com.adriancasares.foursquare.base.event.EventConsumer;
import com.adriancasares.foursquare.base.map.GameMap;
import org.bukkit.Bukkit;
import org.bukkit.event.world.WorldLoadEvent;

import static com.adriancasares.foursquare.FourSquare.fs;

public class ArtifactStarting extends GamePhase {

    static final int STARTING_TIME = 5;
    private int countdown = STARTING_TIME;

    public ArtifactStarting(Artifact parent) {
        super("STARTING", parent);

    }

    private void startCountdown() {
        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(fs(), () -> {
            if(countdown == 0) {
                getParent().setCurrentPhase(new ArtifactPlaying((Artifact) getParent()));
                return;
            }

            Bukkit.getServer().broadcastMessage("Starting in " + countdown);

            countdown--;
        }, 0, 20);

        registerTask(taskId);
    }

    @Override
    public void onStart() {

        Artifact artifact = (Artifact) getParent();

        GameMap world = artifact.getWorld();


        EventConsumer load = FourSquare.fs().getEventSupplier().registerConsumer(WorldLoadEvent.class, (e) -> {
//            if(world.getName().equals(e.getWorld().getName())) {
//                startCountdown();
//                ((Artifact) getParent()).placeArtifact(e.getWorld());
//            }
        });

        registerEvent(load);
    }

    @Override
    public void onEnd() {

    }

    @Override
    public void onPlayerJoin() {

    }

    @Override
    public void onPlayerLeave() {

    }

    @Override
    public void onSpectatorJoin() {

    }

    @Override
    public void onSpectatorLeave() {

    }

}
