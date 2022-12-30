package com.adriancasares.foursquare.artifact;

import com.adriancasares.foursquare.FourSquare;
import com.adriancasares.foursquare.base.map.GameMapConfig;
import com.adriancasares.foursquare.base.util.Position;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ArtifactMapConfig extends GameMapConfig {
    Position artifact;
    Position spawnpoint1;
    Position spawnpoint2;
    Position spawnpoint3;
    Position spawnpoint4;
    Position respawnpoint1;
    Position respawnpoint2;
    Position respawnpoint3;
    Position respawnpoint4;
    Position spectatorSpawn;
    Position lobbySpawn; // TODO lobby in a different world?
    int cageY;
    int upperY;
    int lowerY;

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
}
