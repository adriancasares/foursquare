package com.adriancasares.foursquare.base.map;

import com.adriancasares.foursquare.FourSquare;
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
    int cageY;
    int upperY;
    int lowerY;

    static {
        ConfigurationSerialization.registerClass(ArtifactMapConfig.class);
        GameMapConfig.register("Artifact", ArtifactMapConfig.class);
    }

    public ArtifactMapConfig(Position artifact, Position spawnpoint1, Position spawnpoint2, Position spawnpoint3, Position spawnpoint4, int cageY, int upperY, int lowerY) {
        this.artifact = artifact;
        this.spawnpoint1 = spawnpoint1;
        this.spawnpoint2 = spawnpoint2;
        this.spawnpoint3 = spawnpoint3;
        this.spawnpoint4 = spawnpoint4;
        this.cageY = cageY;
        this.upperY = upperY;
        this.lowerY = lowerY;
    }

    public ArtifactMapConfig(Map<String, Object> deserializable){
        Logger log = FourSquare.fs().getLogger();
        log.info("Deserializing ArtifactMapConfig!!!");
        log.info(deserializable.get("spawnpoint1").toString());
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        // TODO make sure that all the Position objects are serialized later before storing in config
        Map<String, Object> builder = new HashMap<>();
        builder.put("spawnpoint1", spawnpoint1);
        builder.put("spawnpoint2", spawnpoint2);
        builder.put("spawnpoint3", spawnpoint3);
        builder.put("spawnpoint4", spawnpoint4);
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
