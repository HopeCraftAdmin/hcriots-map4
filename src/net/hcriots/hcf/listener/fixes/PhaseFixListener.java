package net.hcriots.hcf.listener.fixes;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import net.hcriots.hcf.HCF;
import net.hcriots.hcf.faction.FactionManager;
import net.hcriots.hcf.faction.type.Faction;
import net.hcriots.hcf.faction.type.PlayerFaction;
import net.hcriots.hcf.listener.PlayTimeManager;
import net.md_5.bungee.api.ChatColor;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.hcriots.utils.internal.com.hcriots.base.BasePlugin;

public class PhaseFixListener
implements Listener {
    long gravityBlock = TimeUnit.HOURS.toMillis(6);
    long utilityBlock = TimeUnit.HOURS.toMillis(3);

    @EventHandler
    public void onMove(PlayerInteractEvent e) {
        if (e.getPlayer().getLocation().getBlock() != null && e.getPlayer().getLocation().getBlock().getType() == Material.TRAP_DOOR && !HCF.getInstance().getFactionManager().getFactionAt(e.getPlayer().getLocation()).equals(HCF.getInstance().getFactionManager().getPlayerFaction(e.getPlayer().getUniqueId()))) {
            e.getPlayer().sendMessage((Object)ChatColor.RED + "Glitch detected. Now reporting, and fixing.");
            e.getPlayer().teleport(e.getPlayer().getLocation().add(0.0, 1.0, 0.0));
        }
    }

    @EventHandler
    public void onPlayerTimePlace(BlockPlaceEvent event) {
        if (!event.getPlayer().hasPermission("command.phase.bypass") && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            Player player = event.getPlayer();
            if (event.getBlockPlaced().getType() == Material.SAND || event.getBlockPlaced().getType() == Material.GRAVEL) {
                if (BasePlugin.getPlugin().getPlayTimeManager().getTotalPlayTime(player.getUniqueId()) <= this.gravityBlock) {
                    player.sendMessage((Object)ChatColor.RED + "You must wait another " + DurationFormatUtils.formatDurationWords((long)(this.gravityBlock - BasePlugin.getPlugin().getPlayTimeManager().getTotalPlayTime(player.getUniqueId())), (boolean)true, (boolean)true) + " before placing a gravity block.");
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
}


