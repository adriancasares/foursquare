package com.adriancasares.foursquare.artifact;

import com.adriancasares.foursquare.FourSquare;
import com.adriancasares.foursquare.base.GamePhase;
import com.adriancasares.foursquare.base.Person;
import com.adriancasares.foursquare.base.scoreboard.ScoreboardTemplate;
import com.adriancasares.foursquare.base.util.FSColor;
import com.adriancasares.foursquare.base.util.Position;
import com.adriancasares.foursquare.base.util.chat.MessageTemplate;
import com.adriancasares.foursquare.base.util.inventory.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.world.damagesource.DamageSource;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.BlockIterator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.adriancasares.foursquare.FourSquare.fs;

public class ArtifactPlaying extends GamePhase {

    private final HashMap<Person, Integer> scores = new HashMap<>();
    private final HashMap<Person, ArtifactRespawn> respawns = new HashMap<>();

    private Person artifactHolder = null;

    private final ItemBuilder STONE_SWORD = new ItemBuilder(Material.STONE_SWORD);
    private final ItemBuilder IRON_PICKAXE = new ItemBuilder(Material.IRON_PICKAXE);
    private final ItemBuilder SCAFFOLDING = new ItemBuilder(Material.WHITE_TERRACOTTA, "Scaffolding");
    private final ItemBuilder SNOWBALL = new ItemBuilder(Material.SNOWBALL);
    private final ItemBuilder BASE_CHESTPLATE = new ItemBuilder(Material.LEATHER_CHESTPLATE);

    private final ItemBuilder GOLDEN_APPLE = new ItemBuilder(Material.GOLDEN_APPLE, "Golden Apple");

    private final ScoreboardTemplate scoreboardTemplate = new ScoreboardTemplate(Component.text("First to 90").decorate(TextDecoration.BOLD),
            new ArrayList<>());

    public ArtifactPlaying(Artifact parent) {
        super("PLAYING", parent);

        getParent().getTeam().getPlayers().forEach((player) -> scores.put(player, 0));
    }



    private void setInventory(Person person) {
        Player player = person.getPlayer();

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.getInventory().setItem(0, STONE_SWORD.build(1));
        player.getInventory().setItem(1, IRON_PICKAXE.build(1));
        player.getInventory().setItem(2, SCAFFOLDING.build(64));
        player.getInventory().setItem(3, SNOWBALL.build(8));

        ItemStack chestplate = BASE_CHESTPLATE.build(1);
        LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
        meta.setUnbreakable(true);

        meta.setColor(Color.fromRGB(
                person.getColor().getPrimary().value()
        ));

        chestplate.setItemMeta(meta);

        player.getInventory().setChestplate(chestplate);
    }

    private boolean isPersonRespawning(Person person) {
        return respawns.containsKey(person);
    }

    private boolean isPlayerRespawning(Player player) {
        Person person = FourSquare.fs().getCurrentTeam().getPerson(player);
        return isPersonRespawning(person);
    }

    private void handleDamage(EntityDamageEvent event) {
//        if (event.getEntity() instanceof Player) {
//            Player player = (Player) event.getEntity();
//        }
    }

    private void updateScoreboard() {
        scoreboardTemplate.getLines().clear();

        for(Person person : getParent().getTeam().getPlayers()) {
            scoreboardTemplate.getLines().add("- " + person.getPlayer().getName() + ": " + scores.get(person));
        }

        getParent().getTeam().getPlayers().forEach((player) -> {
            Scoreboard scoreboard = getParent().getScoreboard(player);
            Objective objective = scoreboardTemplate.build(scoreboard);
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        });
    }

    private void teleportPlayers() {
        World world = ((Artifact) getParent()).getWorld().getWorld();
        for(int i = 0; i < getParent().getTeam().getPlayers().size(); i++) {
            Person person = getParent().getTeam().getPlayers().get(i);
            Position position = ((Artifact) getParent()).getMapConfig().getPlayerSpawns().get(i);
            person.getPlayer().teleport(position.getPlayerLocation(world));
        }
    }

    public void setArtifactHolder(Person person) {
        if(artifactHolder != null) {
            artifactHolder.getPlayer().setGlowing(false);
        }
        if(person != null) {
            person.getPlayer().getWorld().spawnParticle(org.bukkit.Particle.TOTEM, person.getPlayer().getLocation(), 100);
            person.getPlayer().playEffect(EntityEffect.TOTEM_RESURRECT);

            person.getPlayer().setGlowing(true);
        }
        artifactHolder = person;

    }

    private void handleBlockBreak(BlockBreakEvent event) {

        if (event.getBlock().getType() == Material.GOLD_BLOCK) {
            Person person = getParent().getTeam().getPerson(event.getPlayer());

            if (person != null) {
                setArtifactHolder(person);

                updateScoreboard();

                new MessageTemplate(person.getColor())
                        .appendImportant(person.getLatestName())
                        .appendText(" picked up the artifact.")
                        .sendTo(getParent());
            }

            event.getBlock().setType(Material.AIR);
        }

        if (event.getBlock().getType() == Material.WHITE_TERRACOTTA) {
            event.getBlock().setType(Material.AIR);
            event.getPlayer().getInventory().addItem(SCAFFOLDING.build(1));
        }
    }

    private void handleEat(PlayerItemConsumeEvent event) {
        if(event.getItem().getType() == Material.GOLDEN_APPLE) {
            if(event.getPlayer().getHealth() == 20) {
                event.getPlayer().setAbsorptionAmount(4);
            } else {
                event.getPlayer().setHealth(20);
            }

            event.getPlayer().getInventory().removeItem(GOLDEN_APPLE.build(1));

            event.setCancelled(true);
        }
    }

    private void startArtifactTimer() {
        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(fs(), () -> {
            if(artifactHolder == null) {
                return;
            }

            int score = scores.get(artifactHolder);
            score++;
            scores.put(artifactHolder, score);

            updateScoreboard();


        }, 0, 20);

        registerTask(taskId);
    }

    private void onSnowballBreak(ProjectileHitEvent event) {
        if(!event.getEntityType().equals(EntityType.SNOWBALL)) return;

        Snowball snowball = (Snowball) event.getEntity();
        Location location = snowball.getLocation();

        int count = 0;
        BlockIterator iterator = new BlockIterator(location.getWorld(), location.toVector(), snowball.getVelocity().normalize(), 0, 2);

        while(iterator.hasNext()) {
            Block hitBlock = iterator.next();

            if(hitBlock.getType().equals(Material.WHITE_TERRACOTTA))
            {
                hitBlock.setType(Material.AIR);

                if(snowball.getShooter() instanceof Player player) {
                    if(isPlayerRespawning(player)) {
                        continue;
                    }

                    player.getInventory().addItem(SCAFFOLDING.build(1));
                }
            }
        }
    }
    private void startRefillTimer() {
        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(fs(), () -> {
            for(Person person : getParent().getTeam().getPlayers()) {
                if(!person.isInGame() || isPersonRespawning(person)) {
                    continue;
                }

                Player player = person.getPlayer();

                player.getInventory().addItem(SNOWBALL.build(3));

                player.getInventory().addItem(GOLDEN_APPLE.build(1));

                player.getInventory().addItem(SCAFFOLDING.build(16));

                player.sendMessage(ChatColor.GRAY + "Your inventory has been refilled.");

                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 0.5f, 1);
            }
        }, 20 * 20, 20 * 20);

        registerTask(taskId);
    }

    private void startRespawnTimer(Person person) {
        ArtifactRespawn respawn = new ArtifactRespawn(person, Artifact.RESPAWN_TIME, this);

        respawns.put(person, respawn);

        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(fs(), respawn::tick, 0, 20);

        respawn.setOnRespawn(() -> {
            Bukkit.getScheduler().cancelTask(taskId);
            respawns.remove(person);
        });

        registerTask(taskId);
    }
    private void handleDeath(PlayerDeathEvent event) {
        if (artifactHolder != null && artifactHolder.getPlayer().equals(event.getPlayer())) {
            setArtifactHolder(null);
            ((Artifact) getParent()).placeArtifact(((Artifact) getParent()).getWorld().getWorld());
        }

        event.setCancelled(true);

        Person person = getParent().getTeam().getPerson(event.getPlayer());
        if(person != null) {
            startRespawnTimer(person);
        }
    }

    private boolean isRespawning(Person person) {
        return respawns.containsKey(person);
    }

    private void handleMoveEvent(PlayerMoveEvent event) {
        if(event.getTo().getY() < 0) {
            Player player = event.getPlayer();
            if(isPlayerRespawning(player)) {
                return;
            }
            player.setHealth(0);
        }
    }

    @Override
    public void onStart() {
        for(int i = 0; i < getParent().getTeam().getPlayers().size(); i++) {
            Person person = getParent().getTeam().getPlayers().get(i);
            setInventory(person);
        }

        updateScoreboard();

        startArtifactTimer();

        startRefillTimer();

        teleportPlayers();

        registerEvent(FourSquare.fs().getEventSupplier().registerConsumer(BlockBreakEvent.class, this::handleBlockBreak));

        registerEvent(FourSquare.fs().getEventSupplier().registerConsumer(EntityDamageEvent.class, this::handleDamage));

        registerEvent(FourSquare.fs().getEventSupplier().registerConsumer(PlayerDeathEvent.class, this::handleDeath));

        registerEvent(FourSquare.fs().getEventSupplier().registerConsumer(FoodLevelChangeEvent.class, (e) -> e.setCancelled(true)));

        registerEvent(FourSquare.fs().getEventSupplier().registerConsumer(PlayerMoveEvent.class, EventPriority.LOWEST, this::handleMoveEvent));

        registerEvent(FourSquare.fs().getEventSupplier().registerConsumer(PlayerItemConsumeEvent.class, this::handleEat));

        registerEvent(FourSquare.fs().getEventSupplier().registerConsumer(ProjectileHitEvent.class, this::onSnowballBreak));
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
