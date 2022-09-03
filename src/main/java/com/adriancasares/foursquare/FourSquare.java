package com.adriancasares.foursquare;

import com.adriancasares.foursquare.base.AdminUtilCommand;
import com.adriancasares.foursquare.base.GameInitCommand;
import com.adriancasares.foursquare.base.Team;
import com.adriancasares.foursquare.base.command.Command;
import com.adriancasares.foursquare.base.command.CommandDetails;
import com.adriancasares.foursquare.base.command.CommandType;
import com.adriancasares.foursquare.base.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class FourSquare extends JavaPlugin {

    private static FourSquare fourSquare;

    public static int MAX_TEAM_PLAYERS = 4;

    private Team currentTeam;

    private HashMap<String, Command> commandMap;

    @Override
    public void onEnable() {
        // Plugin startup logic
        fourSquare = this;
        commandMap = new HashMap<>();

        new GameInitCommand().register();
        new AdminUtilCommand().register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static FourSquare getFourSquare() {
        return fourSquare;
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }

    public void setCurrentTeam(Team currentTeam) {
        this.currentTeam = currentTeam;
    }

    public HashMap<String, Command> getCommandMap() {
        return commandMap;
    }

}
