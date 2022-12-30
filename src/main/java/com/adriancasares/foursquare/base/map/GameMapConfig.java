package com.adriancasares.foursquare.base.map;

import com.adriancasares.foursquare.base.Game;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public abstract class GameMapConfig implements ConfigurationSerializable {

    private static final Map<String, Class<? extends GameMapConfig>> GAME_CONFIGS = new HashMap<>();

    public abstract String getLinkedGameName();

    public static Class<? extends GameMapConfig> getConfigClassForGame(String gameName){
        return GAME_CONFIGS.get(gameName);
    }

    public static void register(String gameName, Class<? extends GameMapConfig> clazz){
        GAME_CONFIGS.put(gameName, clazz);
    }
}
