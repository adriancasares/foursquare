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

        SubCommand debugCmd = new SubCommand("debug", Arrays.asList("debug2"), 0) {
            @Override
            public void runBaseCall(CommandDetails details) {
                getLogger().info("debug command run");
            }
        };

        Command cmd = new Command(CommandType.UNIVERSAL, "test", "test command", "test command", Arrays.asList("test2"), debugCmd) {
            @Override
            public void run(CommandDetails details) {
                getLogger().info("command run");
            }
        };

        cmd.register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static FourSquare getFourSquare() {
        return fourSquare;
    }
}
