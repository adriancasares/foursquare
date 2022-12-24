package com.adriancasares.foursquare.artifact;

import com.adriancasares.foursquare.FourSquare;
import com.adriancasares.foursquare.base.GamePhase;
import com.adriancasares.foursquare.base.Person;
import com.adriancasares.foursquare.base.util.Position;
import com.adriancasares.foursquare.base.util.inventory.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ArtifactPlaying extends GamePhase {

    private HashMap<Person, Integer> scores = new HashMap<>();

    private Person artifactHolder = null;

    private ItemBuilder IRON_SWORD = new ItemBuilder(Material.IRON_SWORD);
    private ItemBuilder IRON_PICKAXE = new ItemBuilder(Material.IRON_PICKAXE);

    private ItemBuilder SCAFFOLDING = new ItemBuilder(Material.WHITE_TERRACOTTA, "Scaffolding");

    private ItemBuilder SNOWBALL = new ItemBuilder(Material.SNOWBALL);

    public ArtifactPlaying(Artifact parent) {
        super("PLAYING", parent);

        getParent().getTeam().getPlayers().forEach((player) -> {
            scores.put(player, 0);
        });
    }



    private void setInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.getInventory().setItem(0, IRON_SWORD.build(1));
        player.getInventory().setItem(1, IRON_PICKAXE.build(1));
        player.getInventory().setItem(2, SCAFFOLDING.build(64));
        player.getInventory().setItem(3, SNOWBALL.build(8));
    }

    private void teleportPlayers() {
        World world = ((Artifact) getParent()).getWorld().getWorld();
        for(int i = 0; i < getParent().getTeam().getPlayers().size(); i++) {
            Person person = getParent().getTeam().getPlayers().get(i);
            Position position = ((Artifact) getParent()).getMapConfig().getPlayerSpawns().get(i);
            person.getPlayer().teleport(position.getPlayerLocation(world));
        }
    }

    @Override
    public void onStart() {
        System.out.println("playing start");

        getParent().getTeam().getPlayers().forEach((player) -> {
            setInventory(player.getPlayer());
        });

        teleportPlayers();
    }

    @Override
    public void onEnd() {

    }

    @Override
    public void onPlayerJoin() {

    }

    @Override
    public void onPlayerLeave() {

    }

    @Override
    public void onSpectatorJoin() {

    }

    @Override
    public void onSpectatorLeave() {

    }
}
