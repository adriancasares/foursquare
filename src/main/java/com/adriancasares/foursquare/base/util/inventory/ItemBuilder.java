package com.adriancasares.foursquare.base.util.inventory;

import com.destroystokyo.paper.Namespaced;
import com.google.common.collect.Multimap;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class ItemBuilder {

    private Material material;
    private String name;
    private List<String> lore;

    public ItemBuilder(Material material) {
        this.material = material;
    }
    public ItemBuilder(Material material, String name) {
        this.material = material;
        this.name = name;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public void setLore(String... lore) {
        this.lore = Arrays.asList(lore);
    }

    public ItemStack build(int amount) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        if(name != null) {
            meta.displayName(Component.text(name).decoration(TextDecoration.ITALIC, false));

        }

        if(lore != null && !lore.isEmpty()) {
            meta.lore(lore.stream().map(Component::text).collect(Collectors.toList()));
        }

        item.setItemMeta(meta);
        return item;
    }
}
