package com.adriancasares.foursquare.base.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Position implements ConfigurationSerializable {

    private int x;
    private int y;
    private int z;
    private int yaw;
    private int pitch;

    public Position(int x, int y, int z, int yaw, int pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Position(int x, int y, int z) {
        this(x, y, z, 0, 0);
    }

    public Position(int x, int y, int z, int yaw) {
        this(x, y, z, yaw, 0);
    }

    public Position(Map<String, Object> deserialize){
        try{
            x = (int) deserialize.get("x");
            y = (int) deserialize.get("y");
            z = (int) deserialize.get("z");
            yaw = (int) deserialize.getOrDefault("yaw", 0);
            pitch = (int) deserialize.getOrDefault("pitch", 0);
        }
        catch(ClassCastException | NullPointerException ex){
            throw new IllegalArgumentException("Failed to build Position object from configuration: corrupted data file!", ex);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getYaw() {
        return yaw;
    }

    public int getPitch() {
        return pitch;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setYaw(int yaw) {
        this.yaw = yaw;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public Location getBlockLocation(World world) {
        return new Location(world, x, y, z);
    }

    public Location getPlayerLocation(World world) {
        return new Location(world, x + 0.5, y, z + 0.5, yaw, pitch);
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> builder = new HashMap<>();
        builder.put("x", x);
        builder.put("y", y);
        builder.put("z", z);
        builder.put("yaw", yaw);
        builder.put("pitch", pitch);

        return builder;
    }
}
