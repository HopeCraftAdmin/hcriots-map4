package net.hcriots.hcf.listener.fixes;


import java.util.*;
import com.google.common.cache.*;
import java.util.concurrent.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.projectiles.*;

public class PearlLandListener implements Listener
{
    public Map pearlMap;
    
    public PearlLandListener() {
        this.pearlMap = CacheBuilder.newBuilder().expireAfterWrite(1L, TimeUnit.MINUTES).build().asMap();
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerDeath(final PlayerDeathEvent event) {
        final Player player = event.getEntity();
        final EnderPearl pearl;
        if ((pearl = (EnderPearl) this.pearlMap.remove(player.getUniqueId())) != null) {
            pearl.remove();
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerTeleport(final PlayerTeleportEvent event) {
        final Player player = event.getPlayer();
        final EnderPearl pearl;
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.UNKNOWN && (pearl = (EnderPearl) this.pearlMap.remove(player.getUniqueId())) != null) {
            pearl.remove();
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerPearl(final ProjectileLaunchEvent event) {
        final Projectile projectile = event.getEntity();
        final ProjectileSource shooter = projectile.getShooter();
        if (shooter instanceof Player && projectile instanceof EnderPearl) {
            final Player player = (Player)shooter;
            final EnderPearl enderPearl = (EnderPearl)projectile;
            this.pearlMap.put(player.getUniqueId(), enderPearl);
        }
    }
}
