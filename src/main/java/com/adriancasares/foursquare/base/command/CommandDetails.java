package com.adriancasares.foursquare.base.command;

import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class CommandDetails {

    private String name;
    private CommandSender sender;
    private String label;
    private List<String> args;

    public CommandDetails(String name, CommandSender sender, String label, String[] args) {
        this.name = name;
        this.sender = sender;
        this.label = label;
        this.args = Arrays.asList(args);
    }

    public CommandDetails(String name, CommandSender sender, String label, List<String> args) {
        this.name = name;
        this.sender = sender;
        this.label = label;
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public CommandSender getSender() {
        return sender;
    }

    public String getLabel() {
        return label;
    }

    public List<String> getArgs() {
        return args;
    }
}
