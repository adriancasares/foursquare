package com.adriancasares.foursquare.base.map;

import com.adriancasares.foursquare.base.util.Position;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class GameMap {

    private File templateFolder;
    private String mapName;
    private String mapCreator;
    private List<GameMapConfig> gameMapConfigs;

    public GameMap(File templateFolder, String mapName, String mapCreator, List<GameMapConfig> gameMapConfigs) {
        this.templateFolder = templateFolder;
        this.mapName = mapName;
        this.mapCreator = mapCreator;
        this.gameMapConfigs = gameMapConfigs;
    }

    //    public WorldWrapper(File templateFolder, String worldName, String worldCreator, List) {
//        this.worldName = worldName;

//        if (template != null) {
//            Path source = Paths.get(template);
//            Path target = Paths.get(withSuffix);
//
//            try {
//                FileUtils.copyDirectory(source.toFile(), target.toFile());
//            } catch (IOException exception) {
//                exception.printStackTrace();
//            }
//        }

    public GameMap(File templateFolder){
        this.templateFolder = templateFolder;
        loadData();
    }

    public void loadData() throws IllegalStateException {
        // 1: find config file
        File mapConfigFile = getMapConfigFile();

        FileConfiguration config = YamlConfiguration.loadConfiguration(mapConfigFile);

        // 2: read all data from config file
        try {
            mapName = config.getString("map_name");
            mapCreator = config.getString("map_creator", "");
            gameMapConfigs = new ArrayList<>();
            if(config.getConfigurationSection("game_configs") != null){
                System.out.println("LALALALA "  + mapName);
                // Get all game configs under game_configs (as key)
                for(String key : config.getConfigurationSection("game_configs").getKeys(false)){
                    // Get what game this config is for (Artifact, etc.)
                    Class<? extends GameMapConfig> gameConfigType = GameMapConfig.getConfigClassForGame(config.getString(key + ".game"));
                    // Get the game config for this game through deserialization
                    GameMapConfig gameMapConfig = config.getSerializable(key, gameConfigType);
                    gameMapConfigs.add(gameMapConfig);
                }
            }
        }
        catch(ClassCastException | NullPointerException ex){
            throw new IllegalArgumentException("Failed to populate GameMap object from configuration: corrupted config file " + mapConfigFile.getPath(), ex);
        }
    }

    public void saveData(){
        // TODO implement saving this data from the GameMap back to the foursquare_conf.yml file
        File configFile = getMapConfigFile();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        config.set("map_name", mapName);
        config.set("map_creator", mapCreator);
        config.set("game_configs", gameMapConfigs);
        config.set("map_name", mapName);

        try{
            config.save(configFile);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public File getMapConfigFile(){
        File mapConfigFile;
        try{
            // warning here is for if file doesn't exist; handled below
            mapConfigFile = templateFolder.listFiles(file -> file.getName().equals("foursquare_conf.yml"))[0];
        }
        catch(NullPointerException ex){
            throw new IllegalStateException("Map in folder " + templateFolder.getName() + " doesn't have a foursquare_conf.yml", ex);
        }

        return mapConfigFile;
    }

    public File getTemplateFolder() {
        return templateFolder;
    }

    public String getMapName() {
        return mapName;
    }

    public String getMapCreator() {
        return mapCreator;
    }

    public List<GameMapConfig> getGameMapConfigs() {
        return gameMapConfigs;
    }

    //    public void create() {
//        WorldCreator worldCreator = new WorldCreator(name);
//        worldCreator.type(WorldType.FLAT);
//
//        this.world = worldCreator.createWorld();
//    }
//
//    public void delete() {
//        world.getPlayers().forEach((player) -> {
//            player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
//        });
//
//        Bukkit.unloadWorld(world, false);
//
//        try {
//            FileUtils.deleteDirectory(world.getWorldFolder());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        world = null;
//    }


//    public String getWorldId() {
//        return worldId;
//    }
}
