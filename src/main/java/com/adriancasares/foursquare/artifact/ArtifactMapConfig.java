package com.adriancasares.foursquare.artifact;

import com.adriancasares.foursquare.FourSquare;
import com.adriancasares.foursquare.base.map.GameMapConfig;
import com.adriancasares.foursquare.base.util.Position;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
@SuppressWarnings("unused")
public class ArtifactMapConfig extends GameMapConfig {
    private Position artifact;
    private Position spawnpoint1;
    private Position spawnpoint2;
    private Position spawnpoint3;
    private Position spawnpoint4;
    private Position respawnpoint1;
    private Position respawnpoint2;
    private Position respawnpoint3;
    private Position respawnpoint4;
    private Position spectatorSpawn;
    private Position lobbySpawn; // TODO lobby in a different world?
    private int cageY;
    private int upperY;
    private int lowerY;

    public ArtifactMapConfig(Position artifact, Position spawnpoint1, Position spawnpoint2, Position spawnpoint3, Position spawnpoint4, Position respawnpoint1, Position respawnpoint2, Position respawnpoint3, Position respawnpoint4, Position spectatorSpawn, Position lobbySpawn, int cageY, int upperY, int lowerY) {
        this.artifact = artifact;
        this.spawnpoint1 = spawnpoint1;
        this.spawnpoint2 = spawnpoint2;
        this.spawnpoint3 = spawnpoint3;
        this.spawnpoint4 = spawnpoint4;
        this.respawnpoint1 = respawnpoint1;
        this.respawnpoint2 = respawnpoint2;
        this.respawnpoint3 = respawnpoint3;
        this.respawnpoint4 = respawnpoint4;
        this.spectatorSpawn = spectatorSpawn;
        this.lobbySpawn = lobbySpawn;
        this.cageY = cageY;
        this.upperY = upperY;
        this.lowerY = lowerY;
    }

    public ArtifactMapConfig(Map<String, Object> deserialize){
        try{
            spawnpoint1 = (Position) deserialize.get("spawnpoint1");
            spawnpoint2 = (Position) deserialize.get("spawnpoint2");
            spawnpoint3 = (Position) deserialize.get("spawnpoint3");
            spawnpoint4 = (Position) deserialize.get("spawnpoint4");

            respawnpoint1 = (Position) deserialize.get("respawnpoint1");
            respawnpoint2 = (Position) deserialize.get("respawnpoint2");
            respawnpoint3 = (Position) deserialize.get("respawnpoint3");
            respawnpoint4 = (Position) deserialize.get("respawnpoint4");

            spectatorSpawn = (Position) deserialize.get("spectatorSpawn");
            lobbySpawn = (Position) deserialize.get("lobbySpawn");

            artifact = (Position) deserialize.get("artifact");

            cageY = (int) deserialize.get("cageY");
            upperY = (int) deserialize.get("upperY");
            lowerY = (int) deserialize.get("lowerY");
        }
        catch(ClassCastException | NullPointerException ex){
            throw new IllegalArgumentException("Failed to build ArtifactMapConfig object from configuration: corrupted data file!", ex);
        }
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        // TODO make sure that all the Position objects are serialized later before storing in config
        Map<String, Object> builder = new HashMap<>();
        builder.put("spawnpoint1", spawnpoint1);
        builder.put("spawnpoint2", spawnpoint2);
        builder.put("spawnpoint3", spawnpoint3);
        builder.put("spawnpoint4", spawnpoint4);

        builder.put("respawnpoint1", respawnpoint1);
        builder.put("respawnpoint2", respawnpoint2);
        builder.put("respawnpoint3", respawnpoint3);
        builder.put("respawnpoint4", respawnpoint4);

        builder.put("spectatorSpawn", spectatorSpawn);
        builder.put("lobbySpawn", lobbySpawn);

        builder.put("artifact", artifact);

        builder.put("cageY", cageY);
        builder.put("upperY", upperY);
        builder.put("lowerY", lowerY);

        return builder;
    }

    @Override
    public String getLinkedGameName() {
        return "Artifact";
    }

    public Position getArtifact() {
        return artifact;
    }

    public void setArtifact(Position artifact) {
        this.artifact = artifact;
    }

    public Position getSpawnpoint1() {
        return spawnpoint1;
    }

    public void setSpawnpoint1(Position spawnpoint1) {
        this.spawnpoint1 = spawnpoint1;
    }

    public Position getSpawnpoint2() {
        return spawnpoint2;
    }

    public void setSpawnpoint2(Position spawnpoint2) {
        this.spawnpoint2 = spawnpoint2;
    }

    public Position getSpawnpoint3() {
        return spawnpoint3;
    }

    public void setSpawnpoint3(Position spawnpoint3) {
        this.spawnpoint3 = spawnpoint3;
    }

    public Position getSpawnpoint4() {
        return spawnpoint4;
    }

    public void setSpawnpoint4(Position spawnpoint4) {
        this.spawnpoint4 = spawnpoint4;
    }

    public Position getRespawnpoint1() {
        return respawnpoint1;
    }

    public void setRespawnpoint1(Position respawnpoint1) {
        this.respawnpoint1 = respawnpoint1;
    }

    public Position getRespawnpoint2() {
        return respawnpoint2;
    }

    public void setRespawnpoint2(Position respawnpoint2) {
        this.respawnpoint2 = respawnpoint2;
    }

    public Position getRespawnpoint3() {
        return respawnpoint3;
    }

    public void setRespawnpoint3(Position respawnpoint3) {
        this.respawnpoint3 = respawnpoint3;
    }

    public Position getRespawnpoint4() {
        return respawnpoint4;
    }

    public void setRespawnpoint4(Position respawnpoint4) {
        this.respawnpoint4 = respawnpoint4;
    }

    public Position getSpectatorSpawn() {
        return spectatorSpawn;
    }

    public void setSpectatorSpawn(Position spectatorSpawn) {
        this.spectatorSpawn = spectatorSpawn;
    }

    public Position getLobbySpawn() {
        return lobbySpawn;
    }

    public void setLobbySpawn(Position lobbySpawn) {
        this.lobbySpawn = lobbySpawn;
    }

    public int getCageY() {
        return cageY;
    }

    public void setCageY(int cageY) {
        this.cageY = cageY;
    }

    public int getUpperY() {
        return upperY;
    }

    public void setUpperY(int upperY) {
        this.upperY = upperY;
    }

    public int getLowerY() {
        return lowerY;
    }

    public void setLowerY(int lowerY) {
        this.lowerY = lowerY;
    }
}
