package com.adriancasares.foursquare.artifact;

import com.adriancasares.foursquare.base.Person;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;

public class ArtifactRespawn {

    private Person person;
    private int respawnTime;
    private Runnable onRespawn;

    private ArtifactPlaying instance;

    private ItemStack[] inventory;

    private void startRespawn() {
        Player player = person.getPlayer();
        inventory = player.getInventory().getContents();

        player.getInventory().clear();

        player.setAllowFlight(true);
        player.setInvulnerable(true);
        player.setFlying(true);
        player.setInvisible(true);
    }

    private void stopRespawn() {
        Player player = person.getPlayer();
        player.getInventory().setContents(inventory);

        player.setAllowFlight(false);
        player.setInvulnerable(false);
        player.setFlying(false);
        player.setInvisible(false);


        player.teleport(
            ((Artifact) instance.getParent())
                    .getMapConfig()
                    .getPlayerSpawns()
                    .get(person.getIndex())
                    .getPlayerLocation(((Artifact) instance.getParent()).getWorld().getWorld())
            );
    }

    public ArtifactRespawn(Person person, int respawnTime, ArtifactPlaying instance) {
        this.person = person;
        this.respawnTime = respawnTime;
        this.instance = instance;

        startRespawn();
    }

    public void setOnRespawn(Runnable onRespawn) {
        this.onRespawn = onRespawn;
    }

    public Person getPerson() {
        return person;
    }

    public int getRespawnTime() {
        return respawnTime;
    }

    private void sendTitle(Player player, int time) {
        Component mainTitle = Component.text("");
        Component subTitle = Component.text("Respawning in " + time + " seconds");
        Title title = Title.title(mainTitle, subTitle, Title.Times.times(Duration.ofSeconds(0), Duration.ofMillis(1500), Duration.ofMillis(500)));
        player.showTitle(title);
    }
    public void tick() {
        if(respawnTime > 0) {
            Player player = person.getPlayer();

            if(player != null) {
                sendTitle(player, respawnTime);
            }

            respawnTime--;
        } else {
            stopRespawn();
            onRespawn.run();
        }
    }
}
