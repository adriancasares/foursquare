package com.adriancasares.foursquare;

import com.adriancasares.foursquare.artifact.ArtifactMapConfig;
import com.adriancasares.foursquare.base.AdminUtilCommand;
import com.adriancasares.foursquare.base.Game;
import com.adriancasares.foursquare.base.GameInitCommand;
import com.adriancasares.foursquare.base.Team;
import com.adriancasares.foursquare.base.command.Command;
import com.adriancasares.foursquare.base.event.EventSupplier;
import com.adriancasares.foursquare.base.data.DataManager;
import com.adriancasares.foursquare.base.map.GameMapConfig;
import com.adriancasares.foursquare.base.map.WorldManager;
import com.adriancasares.foursquare.base.util.Position;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class FourSquare extends JavaPlugin {

    private static FourSquare fourSquare;

    public static int MAX_TEAM_PLAYERS = 4;

    private Team currentTeam;

    private HashMap<String, Command> commandMap;

    private Game currentGame;

    private EventSupplier eventSupplier;

    private DataManager dataManager;
    private WorldManager worldManager;

    @Override
    public void onLoad(){
        GameMapConfig.register("Artifact", ArtifactMapConfig.class);

        ConfigurationSerialization.registerClass(Position.class);
        ConfigurationSerialization.registerClass(ArtifactMapConfig.class);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        fourSquare = this;
        commandMap = new HashMap<>();
        eventSupplier = new EventSupplier(this);
        worldManager = new WorldManager();

        new GameInitCommand().register();
        new AdminUtilCommand().register();

        this.dataManager = new DataManager();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static FourSquare getFourSquare() {
        return fourSquare;
    }
    public static FourSquare fs() {
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

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        if(this.currentGame != null) {
            this.currentGame.end();
        }

        this.currentGame = currentGame;
        this.currentGame.onStart();
    }

    public EventSupplier getEventSupplier() {
        return eventSupplier;
    }

    public void setEventSupplier(EventSupplier eventSupplier) {
        this.eventSupplier = eventSupplier;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }
}
