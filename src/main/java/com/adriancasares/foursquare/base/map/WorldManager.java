package com.adriancasares.foursquare.base.map;

import com.adriancasares.foursquare.base.Game;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class WorldManager {
    private List<WorldContainer> worlds;

    // This class manages the
    public WorldManager(){
        worlds = new ArrayList<>();
    }

    public WorldContainer createWorld(Game associatedGame, GameMap map){
        World world = createWorld(map, associatedGame.getGameId().toString());

        GameMapConfig gameMapConfig = map.getGameMapConfigs().stream().filter(c -> c.getLinkedGameName().equalsIgnoreCase(associatedGame.getName())).collect(Collectors.toList()).get(0);
        WorldContainer worldContainer = new WorldContainer(world, map, gameMapConfig, associatedGame);

        worlds.add(worldContainer);
        return worldContainer;
    }

    public World createWorld(GameMap map, String id){
        int i = 0;
        String worldName;
        File target;
        // If a game has multiple associated worlds, number then _1, _2, _3, etc.
        do{
            worldName = "gameworld_" + id + (i == 0 ? "" : "_" + i);
            target = Path.of(worldName).toFile();
            i++;
        }
        while(target.exists());

        try {
            FileUtils.copyDirectory(map.getTemplateFolder(), target);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        WorldCreator worldcreator = new WorldCreator(worldName);
        worldcreator.type(WorldType.FLAT);
        World world = Bukkit.createWorld(worldcreator);
        world.getWorldBorder().setSize(192.0D);
        world.setDifficulty(Difficulty.HARD);
        world.setWeatherDuration(2147483647);
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setFullTime(0L);

        return world;
    }

}
