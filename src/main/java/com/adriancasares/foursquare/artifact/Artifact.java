package com.adriancasares.foursquare.artifact;

import com.adriancasares.foursquare.FourSquare;
import com.adriancasares.foursquare.base.Game;
import com.adriancasares.foursquare.base.Person;
import com.adriancasares.foursquare.base.Team;
import com.adriancasares.foursquare.base.event.EventConsumer;
import com.adriancasares.foursquare.base.util.FSColor;
import com.adriancasares.foursquare.base.world.WorldWrapper;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

public class Artifact extends Game {

    public static final int RESPAWN_TIME = 5;
    public Artifact(Team team) {
        super(team);
    }

    private WorldWrapper world;

    private ArtifactMapConfig mapConfig = new ArtifactMapConfig();

    private void initScorecardTeams(Scoreboard scoreboard) {
        org.bukkit.scoreboard.Team team1 = scoreboard.registerNewTeam("team1");
        org.bukkit.scoreboard.Team team2 = scoreboard.registerNewTeam("team2");
        org.bukkit.scoreboard.Team team3 = scoreboard.registerNewTeam("team3");
        org.bukkit.scoreboard.Team team4 = scoreboard.registerNewTeam("team4");
        org.bukkit.scoreboard.Team spectators = scoreboard.registerNewTeam("spectators");
        team1.color(NamedTextColor.AQUA);
        team2.color(NamedTextColor.GOLD);
        team3.color(NamedTextColor.DARK_PURPLE);
        team4.color(NamedTextColor.LIGHT_PURPLE);
        spectators.color(NamedTextColor.GRAY);

        for(int i = 0; i < getTeam().getPlayers().size(); i++) {
            Person person = getTeam().getPlayers().get(i);
            switch(i) {
                case 0:
                    team1.addEntry(person.getPlayer().getName());
                    break;
                case 1:
                    team2.addEntry(person.getPlayer().getName());
                    break;
                case 2:
                    team3.addEntry(person.getPlayer().getName());
                    break;
                case 3:
                    team4.addEntry(person.getPlayer().getName());
                    break;
            }
        }

        for(Person person : getTeam().getSpectators()) {
            spectators.addEntry(person.getPlayer().getName());
        }
    }

    private void handleMoveEvent(PlayerMoveEvent event) {
        // teleport player back to spawn if they move out of bounds
        if(event.getTo().getY() < 0) {
            event.getPlayer().teleport(
                    getMapConfig().getSpectatorSpawn().getPlayerLocation(getWorld().getWorld())
            );
        }
    }

    @Override
    public void onStart() {

        world = new WorldWrapper("artifact", "template-temple", true);

        setCurrentPhase(new ArtifactStarting(this));

        world.create();

        registerWorld(world);

        getTeam().getPlayers().forEach((player) -> {
            player.getPlayer().teleport(world.getWorld().getSpawnLocation());
        });

        getTeam().getPlayers().forEach((player) -> {
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

            player.getPlayer().setScoreboard(scoreboard);
            player.getPlayer().setGlowing(false);
            player.getPlayer().setAllowFlight(false);
            player.getPlayer().setFlying(false);
            player.getPlayer().setHealth(20);
            player.getPlayer().setFoodLevel(20);
            player.getPlayer().setSaturation(20);
            player.getPlayer().setExhaustion(0);
            player.getPlayer().setLevel(0);
            player.getPlayer().setExp(0);
            player.getPlayer().getInventory().clear();
            player.getPlayer().getInventory().setArmorContents(null);
            player.getPlayer().getInventory().setHeldItemSlot(0);
            player.getPlayer().setInvulnerable(false);
            player.getPlayer().setInvisible(false);
            player.getPlayer().setCollidable(true);
            player.getPlayer().getActivePotionEffects().forEach((effect) -> {
                player.getPlayer().removePotionEffect(effect.getType());
            });




            setScoreboard(player, scoreboard);
            initScorecardTeams(scoreboard);
        });

        EventConsumer blockPlace = FourSquare.fs().getEventSupplier().registerConsumer(BlockPlaceEvent.class, (e) -> {
            if (e.getBlock().getType() != Material.WHITE_TERRACOTTA) {
                e.setCancelled(true);
            }
        });
        EventConsumer blockBreak = FourSquare.fs().getEventSupplier().registerConsumer(BlockBreakEvent.class, (e) -> {
            e.setCancelled(true);
        });

        registerEvent(blockPlace);
        registerEvent(blockBreak);

        registerEvent(FourSquare.fs().getEventSupplier().registerConsumer(PlayerMoveEvent.class, EventPriority.LOW, this::handleMoveEvent));

    }

    public void placeArtifact(World world) {
        ArtifactMapConfig mapConfig = getMapConfig();

        mapConfig.getArtifactLocation().getBlockLocation(world).getBlock().setType(Material.GOLD_BLOCK);
    }

    @Override
    public void onEnd() {
        deregisterEvents();
        deregisterWorlds();
        deregisterTasks();
    }

    @Override
    public void onPlayerJoin() {
        System.out.println("join");
    }

    @Override
    public void onPlayerLeave() {
        System.out.println("leave");
    }

    @Override
    public void onSpectatorJoin() {
        System.out.println("spec join");
    }

    @Override
    public void onSpectatorLeave() {
        System.out.println("spec leave");
    }

    public WorldWrapper getWorld() {
        return world;
    }

    public ArtifactMapConfig getMapConfig() {
        return mapConfig;
    }
}
