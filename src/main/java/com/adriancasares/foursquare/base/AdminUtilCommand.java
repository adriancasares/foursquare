package com.adriancasares.foursquare.base;

import com.adriancasares.foursquare.FourSquare;
import com.adriancasares.foursquare.artifact.ArtifactMapConfig;
import com.adriancasares.foursquare.base.command.Command;
import com.adriancasares.foursquare.base.command.CommandDetails;
import com.adriancasares.foursquare.base.command.CommandType;
import com.adriancasares.foursquare.base.command.SubCommand;
import com.adriancasares.foursquare.base.map.GameMap;
import com.adriancasares.foursquare.base.util.Position;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class AdminUtilCommand extends Command {

    public AdminUtilCommand() {
        super(
                CommandType.UNIVERSAL,
                "adminutil",
                "Admin utility command",
                "Admin utility command",
                Arrays.asList("autil", "au"),
                new SubCommand("newteam", Arrays.asList(), 0) {
                    public void runBaseCall(CommandDetails details) {
                        details.getSender().sendMessage("Updating team");
                        FourSquare.getFourSquare().setCurrentTeam(Team.gen());
                        details.getSender().sendMessage("Players on team: " + FourSquare.getFourSquare().getCurrentTeam().getPlayers().size());
                    }
                },
                new SubCommand("visitmap", Arrays.asList(), 0) {
                    @Override
                    public void runBaseCall(CommandDetails details) {
                        String mapName = details.getArgs().get(1);
                        GameMap selected = null;
                        for(GameMap map : FourSquare.fs().getDataManager().getMaps()){
                            if(map.getMapName().equalsIgnoreCase(mapName)){
                                selected = map;
                                break;
                            }
                        }

                        if(selected == null){
                            details.getSender().sendMessage("Invalid map name!");
                            return;
                        }

                        World newWorld = FourSquare.fs().getWorldManager().createWorld(selected, UUID.randomUUID().toString());
                        Player p = (Player)details.getSender();
                        p.teleport(new Location(newWorld, 0, 100, 0 ));
                    }
                },
                new SubCommand("listmaps", Arrays.asList(), 0){
                    @Override
                    public void runBaseCall(CommandDetails details) {
                        String maps = "";
                        for(GameMap map : FourSquare.fs().getDataManager().getMaps()){
                            maps += map.getMapName() + "\n";
                        }

                        details.getSender().sendMessage("Maps:\n" + maps);
                    }
                },
                new SubCommand("setmapdata", Arrays.asList(), 0){
                    @Override
                    public void runBaseCall(CommandDetails details) {
                        GameMap selected = null;

                        for(GameMap map : FourSquare.fs().getDataManager().getMaps()){
                            if(map.getMapName().equalsIgnoreCase("Beehive")){
                                selected = map;
                                break;
                            }
                        }

                        assert selected != null;

                        Position p = new Position(0, 0, 0);
                        selected.getGameMapConfigs().add(
                                new ArtifactMapConfig(
                                        p, p, p, p, p, p, p, p, p, p, p, 100, 120, 80
                                ));

                        selected.saveData();

                        details.getSender().sendMessage("Done");
                    }
                }
        );

    }

    @Override
    public void run(CommandDetails details) {

    }
}
