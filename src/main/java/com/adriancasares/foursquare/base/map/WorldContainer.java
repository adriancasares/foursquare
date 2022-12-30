package com.adriancasares.foursquare.base.map;

import com.adriancasares.foursquare.base.Game;
import org.bukkit.World;
import org.checkerframework.checker.units.qual.C;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class WorldContainer {
    private World world;
    private GameMap gameMap;
    private GameMapConfig gameMapConfig;
    private Game associatedGame;
    private Runnable onDelete;

    public WorldContainer(World world, GameMap gameMap, GameMapConfig gameMapConfig, Game associatedGame) {
        this.world = world;
        this.gameMap = gameMap;
        this.gameMapConfig = gameMapConfig;
        this.associatedGame = associatedGame;
    }

    public void onDelete(){
        onDelete.run();
    }

    public void onDelete(Runnable onDelete){
        this.onDelete = onDelete;
    }

    public World getWorld() {
        return world;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public GameMapConfig getGameMapConfig() {
        return gameMapConfig;
    }

    public Game getAssociatedGame() {
        return associatedGame;
    }
}
