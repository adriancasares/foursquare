package com.adriancasares.foursquare.base.command;

import java.util.ArrayList;
import java.util.List;

public abstract class SubCommand {

    private String name;
    private List<String> alias;
    private int argumentPosition;

    private List<SubCommand> subCommands;

    public SubCommand(String name, List<String> alias, int argumentPosition, SubCommand... subCommands) {
        this.name = name;
        this.alias = alias;
        this.argumentPosition = argumentPosition;

        this.subCommands = new ArrayList<>();

        for(SubCommand sub : subCommands) {
            this.subCommands.add(sub);
        }
    }

    public SubCommand(String name, List<String> alias, int argumentPosition, List<SubCommand> subCommands) {
        this.name = name;
        this.alias = alias;
        this.argumentPosition = argumentPosition;
        this.subCommands = subCommands;
    }

    public void call(CommandDetails details) {
        if(details.getArgs().size() > argumentPosition) {
            String nextArgument = details.getArgs().get(argumentPosition).toLowerCase();

            for(SubCommand sub : subCommands) {
                if(sub.getAllNames().contains(nextArgument)) {
                    sub.call(details);
                    return;
                }
            }
        }

        runBaseCall(details);
    }

    public abstract void runBaseCall(CommandDetails details);

    public String getName() {
        return name;
    }

    public List<String> getAlias() {
        return alias;
    }

    public List<String> getAllNames() {
        List<String> all = new ArrayList<String>(alias);
        all.add(name);
        return all;
    }

    public int getArgumentPosition() {
        return argumentPosition;
    }

    public List<SubCommand> getSubCommands() {
        return subCommands;
    }
}
