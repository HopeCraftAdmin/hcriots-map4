package net.hcriots.hcf.listener;

import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreeperPowerEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetEvent;

public class CreeperFriendlyListener implements Listener {

    @EventHandler
    public void onAgro(EntityExplodeEvent evt){
        if (evt.getEntity() instanceof Creeper) {
            evt.setCancelled(true);
        }
    }
}
