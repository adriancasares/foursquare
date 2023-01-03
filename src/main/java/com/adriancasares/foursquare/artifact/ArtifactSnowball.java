package com.adriancasares.foursquare.artifact;

import com.adriancasares.foursquare.FourSquare;
import com.adriancasares.foursquare.base.GamePhase;
import com.adriancasares.foursquare.base.event.EventConsumer;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ArtifactSnowball extends GamePhase {

    public ArtifactSnowball(Artifact snowball) {
        super("SNOWBALL", snowball);
    }

    public void onProjectileHit(ProjectileHitEvent event) {
        if(event.getEntity().getShooter() instanceof Player) {
            if(event.getEntityType().equals(EntityType.SNOWBALL)) {
                if(event.getHitBlock() == null) {
                    if (event.getHitEntity().getType() == EntityType.PLAYER) {
                        Player target = (Player) event.getHitEntity();
                        target.damage(0.1, event.getEntity());
                    }
                }
                else if(event.getHitEntity() == null) {
                    if (event.getHitBlock().getType() == Material.WHITE_TERRACOTTA) {
                        event.getHitBlock().setType(Material.AIR);
                    }
                }
            }
        }
    }

    @Override
    public void onStart() {
        registerEvent(FourSquare.fs().getEventSupplier().registerConsumer(ProjectileHitEvent.class, this::onProjectileHit));
    }

    @Override
    public void onEnd() {
        EventConsumer projectileHit = FourSquare.fs().getEventSupplier().registerConsumer(ProjectileHitEvent.class, (e) -> {
            e.setCancelled(true); });

        registerEvent(projectileHit);
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
