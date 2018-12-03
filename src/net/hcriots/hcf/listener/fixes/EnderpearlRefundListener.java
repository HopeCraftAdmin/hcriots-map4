package net.hcriots.hcf.listener.fixes;

import org.bukkit.event.player.*;

import net.hcriots.hcf.HCF;

import org.bukkit.entity.*;
import org.bukkit.event.*;

public class EnderpearlRefundListener implements Listener
{
    private final HCF hcf;
    
    public EnderpearlRefundListener(final HCF hcf) {
        this.hcf = hcf;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerPearl(final PlayerTeleportEvent event) {
        if (event.isCancelled() && event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            final Player player = event.getPlayer();
            if (this.hcf.getTimerManager().enderPearlTimer.getRemaining(player) > 0L) {
                this.hcf.getTimerManager().enderPearlTimer.refund(player);
            }
        }
    }
}
