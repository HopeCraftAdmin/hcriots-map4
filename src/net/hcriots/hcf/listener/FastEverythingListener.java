package net.hcriots.hcf.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class FastEverythingListener implements Listener {
	
	private List<BrewingStand> brewingStands = new ArrayList();
	private List<Furnace> furnaces = new ArrayList();
	
	public FastEverythingListener(JavaPlugin hcf) {
		new BukkitRunnable()
		{
			public void run()
			{
				for (BrewingStand stand : FastEverythingListener.this.brewingStands) {
					if ((stand.getLocation().getChunk().isLoaded()) &&
							(stand.getBrewingTime() > 1)) {
						stand.setBrewingTime(Math.max(1,  stand.getBrewingTime() - 15));
						
					}
				}
				for (Furnace furnace : FastEverythingListener.this.furnaces) {
					if (furnace.getInventory().getItem(0) != null)
					{
						furnace.setCookTime((short)(furnace.getCookTime() + 10));
						furnace.setBurnTime((short)(furnace.getBurnTime() + 10));
					}
					else
					{
						furnace.setCookTime((short)0);
						furnace.setBurnTime((short)0);
					}
				}
			}
		}
		
		.runTaskTimer(hcf, 2L, 2L);
	}
	
	@EventHandler
	public void onInteractEvent(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			if ((event.getClickedBlock().getType() == Material.FURNACE) || (event.getClickedBlock().getType() == Material.BURNING_FURNACE)) {
				Furnace furnace = (Furnace)event.getClickedBlock().getState();
				if (!this.furnaces.contains(furnace)) {
					this.furnaces.add(furnace);
				}
			}
			if (event.getClickedBlock().getType() == Material.BREWING_STAND) {
		    	BrewingStand brewingStand = (BrewingStand)event.getClickedBlock().getState();
		    	if (!this.brewingStands.contains(brewingStand)) {
		    		this.brewingStands.add(brewingStand);
		    	}			
			}
				
		}
	}
	
	@EventHandler
	public void onFurnaceSmelt(FurnaceSmeltEvent event) {
		Furnace furnace = (Furnace)event.getFurnace().getState();
		if (!this.furnaces.contains(furnace)) {
			this.furnaces.add(furnace);
		}
	}
	
	
	@EventHandler
	public void onFurnaceBurn(FurnaceBurnEvent event) {
	Furnace furnace = (Furnace)event.getFurnace().getState();
	if (!this.furnaces.contains(furnace)) {
		this.furnaces.add(furnace);
	}
}

    @EventHandler
    public void onBrewEvent(BrewEvent event) {
    	BrewingStand brewingStand = (BrewingStand)event.getBlock().getState();
    	if (!this.brewingStands.contains(brewingStand)) {
    		this.brewingStands.add(brewingStand);
    	}
    }
}
