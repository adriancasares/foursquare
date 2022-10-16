package com.adriancasares.foursquare.base.world;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class WorldWrapper {

    private World world;

    public World getWorld() {
        return world;
    }

    public WorldWrapper(String name, String template, boolean hexSuffix) {
        String withSuffix = name + (hexSuffix ? "-" + Integer.toHexString(ThreadLocalRandom.current().nextInt()) : "");

        if(template != null) {
            Path source = Paths.get(template);
            Path target = Paths.get(withSuffix);

            try {
                FileUtils.copyDirectory(source.toFile(), target.toFile());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        WorldCreator worldCreator = new WorldCreator(withSuffix);
        worldCreator.type(WorldType.FLAT);

        this.world = worldCreator.createWorld();
    }

    public void delete() {
        world.getPlayers().forEach((player) -> {
            player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
        });

        Bukkit.unloadWorld(world, false);

        try {
            FileUtils.deleteDirectory(world.getWorldFolder());
        } catch (Exception e) {
            e.printStackTrace();
        }

        world = null;
    }
}
