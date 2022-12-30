package com.adriancasares.foursquare.base.data;

import com.adriancasares.foursquare.FourSquare;
import com.adriancasares.foursquare.base.util.FileUtil;
import com.adriancasares.foursquare.base.map.GameMap;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class DataManager {

    private List<GameMap> maps;

    // This class is meant to center around one-time configuration file loading, reading, etc. -- dealing with files
    // This includes: map files (moving map files from resources to plugins folder), config file
    public DataManager() {
        maps = new ArrayList<>();
        loadMaps();
    }

    public void loadMaps(){
        FourSquare fs = FourSquare.fs();
        fs.getLogger().info("Loading maps...");

        File pluginFolder = FourSquare.fs().getDataFolder();
        File mapsFolder = Path.of(pluginFolder.getPath(), "maps").toFile();

        // Maps folder doesn't exist. We need to manually save all the maps to the maps folder
        // in plugins/FourSquare/maps
        // Why? Because in the jarfile, these maps are zipped (hard to work with); also, moving
        // them to the plugins folder lets users edit them, add maps at will, etc.
        // TODO/Idea: support making maps from WorldEdit Schematic? (easier to work with?)
        if(!mapsFolder.exists() || !mapsFolder.isDirectory()) {
            fs.getLogger().info("Maps folder doesn't exist, creating it (loading defaults)");
            try {
                FileUtil.copyFromJar(getClass(), "maps", mapsFolder.toPath());
            } catch (URISyntaxException | IOException ex) {
                fs.getLogger().log(Level.SEVERE, "Error copying internal maps folder to plugins/FourSquare/maps");
                ex.printStackTrace();
                return;
            }

            fs.getLogger().info("Done copying maps!");
        }

        // NOW: We know we have all the maps in plugins/FourSquare/maps
        // NEXT: We need to loop through these maps and make GameMap for each

        // warning here is for if it's not a directory, but I'm going to assume it is since the above checks
        for(File mapFolder : mapsFolder.listFiles()){
            try{
                maps.add(new GameMap(mapFolder));
            }
            catch(IllegalStateException ex){
                fs.getLogger().log(Level.WARNING, "Invalid map folder " + mapFolder.getPath(), ex);
            }
        }

        fs.getLogger().info("Done loading maps!");
    }

    public List<GameMap> getMaps() {
        return maps;
    }
}
