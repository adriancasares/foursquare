package com.adriancasares.foursquare;

import com.adriancasares.foursquare.base.command.Command;
import com.adriancasares.foursquare.base.command.CommandDetails;
import com.adriancasares.foursquare.base.command.CommandType;
import com.adriancasares.foursquare.base.command.SubCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

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
