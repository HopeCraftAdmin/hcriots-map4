package net.hcriots.hcf.listener;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import net.hcriots.hcf.HCF;
import net.hcriots.hcf.faction.FactionManager;
import net.hcriots.hcf.faction.FactionUser;
import net.hcriots.hcf.faction.struct.Relation;
import net.hcriots.hcf.faction.type.PlayerFaction;
import net.hcriots.hcf.listener.UserManager;

public class FoundDiamondsListener
implements Listener {
    public static final Material SEARCH_TYPE = Material.DIAMOND_ORE;
    public final Set<String> foundLocations = new HashSet<String>();
    private final HCF plugin;

    public FoundDiamondsListener(HCF plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
    public void onPistonExtend(BlockPistonExtendEvent event) {
        for (Block block : event.getBlocks()) {
            if (block.getType() != SEARCH_TYPE) continue;
            this.foundLocations.add(block.getLocation().toString());
        }
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (block.getType() == SEARCH_TYPE) {
            this.foundLocations.add(block.getLocation().toString());
        }
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        if (player.getItemInHand().getEnchantments().containsKey((Object)Enchantment.SILK_TOUCH)) {
            return;
        }
        Block block = event.getBlock();
        Location blockLocation = block.getLocation();
        if (block.getType() == SEARCH_TYPE && this.foundLocations.add(blockLocation.toString())) {
            int count = 1;
            for (int x = -5; x < 5; ++x) {
                for (int y = -5; y < 5; ++y) {
                    for (int z = -5; z < 5; ++z) {
                        Block otherBlock = blockLocation.clone().add((double)x, (double)y, (double)z).getBlock();
                        if (otherBlock.equals((Object)block) || otherBlock.getType() != SEARCH_TYPE || !this.foundLocations.add(otherBlock.getLocation().toString())) continue;
                        ++count;
                    }
                }
            }
            this.plugin.getUserManager().getUser(player.getUniqueId()).setDiamondsMined(this.plugin.getUserManager().getUser(player.getUniqueId()).getDiamondsMined() + count);
            for (Player on : Bukkit.getServer().getOnlinePlayers()) {
                String message;
                if (this.plugin.getFactionManager().getPlayerFaction(player.getUniqueId()) != null) {
                    message = (Object)this.plugin.getFactionManager().getPlayerFaction(player.getUniqueId()).getRelation((CommandSender)on).toChatColour() + "�8(�d*�8) " + ChatColor.LIGHT_PURPLE + player.getName() + (Object)ChatColor.GRAY + " has found " + ChatColor.DARK_GRAY + "[" + (Object)ChatColor.LIGHT_PURPLE + count + ChatColor.DARK_GRAY + "] " + ChatColor.LIGHT_PURPLE + "diamonds.";
                    on.sendMessage(message);
                    continue;
                }
                message = (Object)ChatColor.DARK_GRAY + "(" + ChatColor.LIGHT_PURPLE + "*" + ChatColor.DARK_GRAY + ") " + ChatColor.LIGHT_PURPLE + player.getName() + (Object)ChatColor.GRAY + " has found " + ChatColor.DARK_GRAY + "[" + (Object)ChatColor.LIGHT_PURPLE + count + ChatColor.DARK_GRAY + "] " + ChatColor.LIGHT_PURPLE + "diamonds.";
                on.sendMessage(message);
            }
        }
    }
}

