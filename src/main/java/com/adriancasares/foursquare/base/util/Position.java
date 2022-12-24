package com.adriancasares.foursquare.base.util;

import org.bukkit.Location;
import org.bukkit.World;

public class Position {

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
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = 0;
        this.pitch = 0;
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
}
