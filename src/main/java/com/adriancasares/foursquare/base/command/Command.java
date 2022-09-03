package com.adriancasares.foursquare.base.command;

import com.adriancasares.foursquare.FourSquare;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {

    private List<SubCommand> subCommands;
    private BukkitCommand bukkitCommand;

    private void initBukkitCommand(CommandType type, String name, String description, String usageMessage, List<String> aliases) {
        bukkitCommand = new BukkitCommand(name, description, usageMessage, aliases) {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
                if (type == CommandType.UNIVERSAL || type == CommandType.PLAYER && sender instanceof Player || type == CommandType.CONSOLE && sender instanceof ConsoleCommandSender) {
                    CommandDetails details = new CommandDetails(name, sender, commandLabel, args);

                    if(details.getArgs().size() > 0) {
                        String nextArgument = details.getArgs().get(0).toLowerCase();

                        for(SubCommand sub : subCommands) {
                            if(sub.getAllNames().contains(nextArgument)) {
                                sub.call(details);
                                return false;
                            }
                        }
                    }

                    run(details);
                }

                return false;
            }
        };
    }

    public Command(CommandType type, String name, String description, String usageMessage, List<String> aliases, List<SubCommand> subCommands) {
        initBukkitCommand(type, name, description, usageMessage, aliases);
    }

    public Command(CommandType type, String name, String description, String usageMessage, List<String> aliases, SubCommand... subCommands) {
        this.subCommands = new ArrayList<>();

        for(SubCommand sub : subCommands) {
            this.subCommands.add(sub);
        }

        initBukkitCommand(type, name, description, usageMessage, aliases);
    }

    public abstract void run(CommandDetails details);

    public List<SubCommand> getSubCommands() {
        return subCommands;
    }

    public BukkitCommand getBukkitCommand() {
        return bukkitCommand;
    }

    public void register() {
        ((CraftServer) FourSquare.getFourSquare().getServer()).getCommandMap().register(
                FourSquare.getFourSquare().getName(),
                bukkitCommand
        );
    }
}
