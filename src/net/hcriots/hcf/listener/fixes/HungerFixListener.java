package net.hcriots.hcf.listener.fixes;

import net.hcriots.hcf.HCF;

import org.bukkit.event.player.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

public class HungerFixListener implements Listener
{
    private final HCF hcf;
    
    public HungerFixListener(final HCF hcf) {
        this.hcf = hcf;
    }
    
    @EventHandler
    public void onMove(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        final Location from = event.getFrom();
        final Location to = event.getTo();
        if ((from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()) && event.getPlayer().getFoodLevel() < 20 && this.hcf.getFactionManager().getFactionAt(player.getLocation()).isSafezone()) {
            player.setFoodLevel(20);
            player.setSaturation(20.0f);
        }
    }
    
    @EventHandler
    public void onHungerChange(final FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            final Player player = (Player)event.getEntity();
            if (this.hcf.getFactionManager().getFactionAt(player.getLocation()).isSafezone()) {
                player.setSaturation(20.0f);
                player.setHealth(20.0);
            }
            player.setSaturation(10.0f);
        }
    }
}
