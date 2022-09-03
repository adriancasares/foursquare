package com.adriancasares.foursquare.base.command;

import com.adriancasares.foursquare.FourSquare;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Command {

    private List<SubCommand> subCommands;
    private BukkitCommand bukkitCommand;

    private CommandType type;

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

            @Override
            public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
                List<String> list = new ArrayList<>();

                if(type == CommandType.UNIVERSAL || type == CommandType.PLAYER && sender instanceof Player || type == CommandType.CONSOLE && sender instanceof ConsoleCommandSender) {
                    String nextArgument = args[0].toLowerCase();

                    for(SubCommand sub : subCommands) {
                        if(args.length == 1) {
                            list.addAll(sub.getAllNames());
                        }
                        else if(sub.getAllNames().contains(nextArgument)) {
                            list.addAll(sub.tab(sender, Arrays.asList(args)));
                            break;
                        }
                    }
                }

                Stream<String> stream = list.stream().filter((item) -> item.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));

                return stream.collect(Collectors.toCollection(ArrayList::new));
            }
        };
    }

    public Command(CommandType type, String name, String description, String usageMessage, List<String> aliases, List<SubCommand> subCommands) {
        this.type = type;
        initBukkitCommand(type, name, description, usageMessage, aliases);
    }

    public Command(CommandType type, String name, String description, String usageMessage, List<String> aliases, SubCommand... subCommands) {
        this.type = type;
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

    public List<String> getAllNames() {
        List<String> all = new ArrayList<String>(bukkitCommand.getAliases());
        all.add(bukkitCommand.getName());
        return all;
    }

    public CommandType getType() {
        return type;
    }

    public void register() {
        ((CraftServer) FourSquare.getFourSquare().getServer()).getCommandMap().register(
                FourSquare.getFourSquare().getName(),
                bukkitCommand
        );

        FourSquare.getFourSquare().getCommandMap().put(bukkitCommand.getName(), this);
    }



}
