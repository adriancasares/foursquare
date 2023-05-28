package com.adriancasares.foursquare.artifact;

import com.adriancasares.foursquare.FourSquare;
import com.adriancasares.foursquare.base.util.Position;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ArtifactMapConfig {

    private ArrayList<Position> playerSpawns = new ArrayList<>();
    private ArrayList<Position> playerRespawns = new ArrayList<>();
    private Position spectatorSpawn;
    private Position artifactLocation;

    public ArtifactMapConfig() {
        YamlConfiguration maps = FourSquare.fs().getCustomConfig("maps.yml");

        List<String> mapNames = (List<String>) maps.get("maps");

        int random = (int) (Math.random() * mapNames.size());

        YamlConfiguration map = FourSquare.fs().getCustomConfig("maps/" + mapNames.get(random) + ".yml");

        List<LinkedHashMap> playerSpawnsConfig = (List<LinkedHashMap>) map.getList("player-spawns");

        for (LinkedHashMap playerSpawn : playerSpawnsConfig) {
            Integer x = (Integer) playerSpawn.get("x");
            Integer y = (Integer) playerSpawn.get("y");
            Integer z = (Integer) playerSpawn.get("z");
            Integer yaw = (Integer) playerSpawn.get("yaw");
            Integer pitch = (Integer) playerSpawn.get("pitch");

            playerSpawns.add(new Position(x, y, z, yaw, pitch));
            playerRespawns.add(new Position(x, y, z, yaw, pitch));
        }

        int artifactHeight = (Integer) map.get("artifact-height");

        this.artifactLocation = new Position(0, artifactHeight, 0);
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
