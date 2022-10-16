package com.adriancasares.foursquare.base;

import com.adriancasares.foursquare.FourSquare;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class Team {

    private ArrayList<Person> players;

    private ArrayList<Person> spectators;

    public Team(ArrayList<Person> players, ArrayList<Person> spectators) {
        this.players = players;
        this.spectators = spectators;
    }

    public Team() {
        this.players = new ArrayList<>();
        this.spectators = new ArrayList<>();
    }

    public ArrayList<Person> getPeople() {
        ArrayList<Person> all = (ArrayList<Person>) players.clone();

        all.addAll(spectators);

        return all;
    }

    public ArrayList<Person> getPlayers() {
        return players;
    }

    public ArrayList<Person> getSpectators() {
        return spectators;
    }

    public void setSpectators(ArrayList<Person> spectators) {
        this.spectators = spectators;
    }

    // refreshes spectators
    public void refreshSpectators() {
        spectators = new ArrayList<>();

        ArrayList<UUID> playerIds = new ArrayList<>();

        for(Person player : players) {
            playerIds.add(player.getUuid());
        }

        for(Player person : Bukkit.getOnlinePlayers()) {
            if(playerIds.contains(person.getUniqueId())) continue;

            spectators.add(
                    new Person(
                            person, false
                    )
            );
        }
    }

    // refreshes team players
    public static Team gen() {
        Team team = new Team();

        int i = 0;

        for(Player player : Bukkit.getOnlinePlayers()) {
            if(++i > FourSquare.MAX_TEAM_PLAYERS) break;

            team.getPlayers().add(new Person(player, true));
        }

        if(i > FourSquare.MAX_TEAM_PLAYERS) {
            team.refreshSpectators();
        }

        return team;
    }

    public boolean containsPlayer(Player player) {
        for(Person person : players) {
            if(person.isPlayer() && person.getUuid().equals(player.getUniqueId())) {
                return true;
            }
        }

        return false;
    }
}
