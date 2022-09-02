package com.adriancasares.foursquare.base;

import java.util.ArrayList;

public class Team {

    private ArrayList<Person> people;

    private ArrayList<Person> players;

    private ArrayList<Person> spectators;

    public ArrayList<Person> getPeople() {
        return people;
    }

    public ArrayList<Person> getPlayers() {
        return players;
    }

    public ArrayList<Person> getSpectators() {
        return spectators;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

    public void setSpectators(ArrayList<Person> spectators) {
        this.spectators = spectators;
    }
}
