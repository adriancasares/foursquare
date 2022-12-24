package com.adriancasares.foursquare.artifact;

import com.adriancasares.foursquare.base.util.Position;

import java.util.ArrayList;

public class ArtifactMapConfig {

    private ArrayList<Position> playerSpawns = new ArrayList<>();
    private ArrayList<Position> playerRespawns = new ArrayList<>();
    private Position spectatorSpawn;
    private Position artifactLocation;

    public ArtifactMapConfig() {

    }

    public void addPlayerSpawn(Position position) {
        playerSpawns.add(position);
    }

    public void addPlayerRespawn(Position position) {
        playerRespawns.add(position);
    }

    public void setSpectatorSpawn(Position position) {
        spectatorSpawn = position;
    }

    public void setArtifactLocation(Position position) {
        artifactLocation = position;
    }

    public ArrayList<Position> getPlayerSpawns() {
        return playerSpawns;
    }

    public ArrayList<Position> getPlayerRespawns() {
        return playerRespawns;
    }

    public Position getSpectatorSpawn() {
        return spectatorSpawn;
    }

    public Position getArtifactLocation() {
        return artifactLocation;
    }

    public static ArtifactMapConfig createDefault() {
        ArtifactMapConfig config = new ArtifactMapConfig();
        for (int i = 0; i < 4; i++) {
            config.addPlayerSpawn(new Position(0, 5, 0));
            config.addPlayerRespawn(new Position(0, 5, 0));
        }
        config.setSpectatorSpawn(new Position(0, 5, 0));
        config.setArtifactLocation(new Position(0, 5, 0));
        return config;
    }
}
