package com.adriancasares.foursquare.base;

import com.adriancasares.foursquare.FourSquare;
import com.adriancasares.foursquare.artifact.Artifact;
import com.adriancasares.foursquare.base.command.Command;
import com.adriancasares.foursquare.base.command.CommandDetails;
import com.adriancasares.foursquare.base.command.CommandType;
import com.adriancasares.foursquare.base.command.SubCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameInitCommand extends Command {

    public GameInitCommand() {
        super(
                CommandType.UNIVERSAL,
                "gameinit",
                "Game init command",
                "Game init command",
                Arrays.asList(),
                new SubCommand("artifact", Arrays.asList(), 0) {
                    public void runBaseCall(CommandDetails details) {

                        if(FourSquare.getFourSquare().getCurrentTeam() == null) {
                            details.getSender().sendMessage("Make a team first!");
                            return;
                        }

                        Artifact game = new Artifact(FourSquare.getFourSquare().getCurrentTeam());

                        FourSquare.getFourSquare().setCurrentGame(game);

                        details.getSender().sendMessage("Setting up artifact!");

                    }
                }
        );

    }

    @Override
    public void run(CommandDetails details) {

    }
}
