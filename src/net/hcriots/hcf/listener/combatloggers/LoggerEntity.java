package net.hcriots.hcf.listener.combatloggers;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.event.Event;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityAgeable;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;

import net.hcriots.hcf.HCF;
import net.minecraft.server.v1_7_R4.DamageSource;
import net.minecraft.server.v1_7_R4.WorldServer;
import org.bukkit.OfflinePlayer;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.PlayerInteractManager;
import net.minecraft.util.com.mojang.authlib.GameProfile;

import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import net.minecraft.server.v1_7_R4.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.World;
import java.util.UUID;
import com.google.common.base.Function;
import net.minecraft.server.v1_7_R4.EntityVillager;

public abstract interface LoggerEntity
{
    public abstract void postSpawn(HCF paramHCF);

    public abstract CraftPlayer getBukkitEntity();

    public abstract UUID getUniqueID();

    public abstract void destroy();
}
