package com.adriancasares.foursquare.base;

import com.adriancasares.foursquare.FourSquare;
import com.adriancasares.foursquare.base.command.Command;
import com.adriancasares.foursquare.base.command.CommandDetails;
import com.adriancasares.foursquare.base.command.CommandType;
import com.adriancasares.foursquare.base.command.SubCommand;

import java.util.Arrays;

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
                }
        );

    }

    @Override
    public void run(CommandDetails details) {

    }
}
