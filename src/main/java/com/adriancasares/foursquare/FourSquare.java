package com.adriancasares.foursquare;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class FourSquare extends JavaPlugin {

    private static FourSquare fourSquare;

    @Override
    public void onEnable() {
        // Plugin startup logic
        fourSquare = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static FourSquare getFourSquare() {
        return fourSquare;
    }
}
