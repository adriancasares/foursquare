package com.adriancasares.foursquare.base.command;

import com.adriancasares.foursquare.FourSquare;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Command implements TabCompleter, CommandExecutor {

    private List<SubCommand> subCommands;
    private PluginCommand command;
    private CommandType type;

    public Command(CommandType type, String name, String description, String usageMessage, List<String> aliases, List<SubCommand> subCommands) {
        this.type = type;
        subCommands = new ArrayList<>();
        registerCommand(type, name, description, usageMessage, aliases);
    }

    public Command(CommandType type, String name, String description, String usageMessage, List<String> aliases, SubCommand... subCommands) {
        this.type = type;
        this.subCommands = new ArrayList<>();

        this.subCommands.addAll(Arrays.asList(subCommands));

        registerCommand(type, name, description, usageMessage, aliases);
    }

    // From https://www.spigotmc.org/threads/solved-modifying-a-registered-command-alias.375073/
    public void registerCommand(CommandType type, String name, String description, String usageMessage, List<String> aliases) {
        Class<?> cl = PluginCommand.class;
        Constructor<?> cons = null;
        try {
            cons = cl.getDeclaredConstructor(String.class, Plugin.class);
            cons.setAccessible(true);
        } catch (NoSuchMethodException | SecurityException e1) {
            e1.printStackTrace();
        }
        try {
            command = (PluginCommand) cons.newInstance(name, FourSquare.fs()); // made the instance
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                 | InvocationTargetException e) {
            e.printStackTrace();
        }
        command.setAliases(aliases); // Set aliases
        Bukkit.getCommandMap().register("foursquare", command); // Register on Bukkit's Map
        command.register(Bukkit.getCommandMap()); // Register Map on your Command
        command.setExecutor(this); // Set executor
        command.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command bukkitCommand, @NotNull String commandLabel, @NotNull String[] args) {
        if (type == CommandType.UNIVERSAL || type == CommandType.PLAYER && sender instanceof Player || type == CommandType.CONSOLE && sender instanceof ConsoleCommandSender) {
            CommandDetails details = new CommandDetails(command.getName(), sender, commandLabel, args);

            if(details.getArgs().size() > 0) {
                String nextArgument = details.getArgs().get(0).toLowerCase();

                for(SubCommand sub : subCommands) {
                    if(sub.getAllNames().contains(nextArgument)) {
                        sub.call(details);
                        return true;
                    }
                }
            }

            run(details);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command bukkitCommand, @NotNull String label, @NotNull String[] args) {
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

    public abstract void run(CommandDetails details);

    public List<SubCommand> getSubCommands() {
        return subCommands;
    }

    public PluginCommand getBukkitCommand() {
        return command;
    }

    public List<String> getAllNames() {
        List<String> all = new ArrayList<>(command.getAliases());
        all.add(command.getName());
        return all;
    }

    public CommandType getType() {
        return type;
    }

    public void register() {
//        ((CraftServer) FourSquare.getFourSquare().getServer()).getCommandMap().register(
//                FourSquare.getFourSquare().getName(),
//                command
//        );

//        FourSquare.getFourSquare().getCommandMap().put(command.getName(), this);
    }



}
