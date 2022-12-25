package com.adriancasares.foursquare.base.scoreboard;

import com.adriancasares.foursquare.FourSquare;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;

public class ScoreboardTemplate {

    private Component title;
    private ArrayList<String> lines;

    public ScoreboardTemplate(Component title, ArrayList<String> lines) {
        this.title = title;
        this.lines = lines;
    }

    public Component getTitle() {
        return title;
    }

    public ArrayList<String> getLines() {
        return lines;
    }

    public Objective build(Scoreboard scoreboard) {
        String objectiveName = Integer.toHexString((int) (Math.random() * 1000000));

        Objective objective = scoreboard.registerNewObjective(objectiveName, Criteria.DUMMY, title);
        objective.setDisplaySlot(org.bukkit.scoreboard.DisplaySlot.SIDEBAR);

        for(int i = 0; i < lines.size(); i++) {
            objective.getScore(lines.get(i)).setScore(lines.size() - i);
        }

        return objective;
    }
}
