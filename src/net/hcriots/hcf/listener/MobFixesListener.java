package net.hcriots.hcf.listener;

import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
	public class MobFixesListener implements Listener
	{
	    @EventHandler
	    public void onCreatureSpawn(final CreatureSpawnEvent e) {
	        if (e.getEntity() instanceof Horse || (e.getEntity() instanceof Skeleton && ((Skeleton)e.getEntity()).getSkeletonType() == Skeleton.SkeletonType.WITHER)) {
	            e.setCancelled(true);
	        }
	    }
	    
	    @EventHandler
	    public void onEndermanDamage(final EntityDamageByEntityEvent event) {
	        if (event.getDamager() instanceof Enderman || event.getDamager() instanceof MagmaCube || event.getDamager() instanceof Slime) {
	            event.setCancelled(true);
	        }
	    }
	}