package net.hcriots.hcf.armors.bard;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import net.hcriots.hcf.HCF;
import net.hcriots.hcf.armors.event.PvpClassUnequipEvent;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionEffectExpireEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.UUID;

public class EffectRestorer implements Listener {

	  private final Table<UUID, PotionEffectType, PotionEffect> restores = HashBasedTable.create();
	  
	  public EffectRestorer(HCF plugin) {
	    plugin.getServer().getPluginManager().registerEvents(this, plugin);
	  }
	  
	  @EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
	  public void onPvpClassUnequip(PvpClassUnequipEvent event) {
	    restores.rowKeySet().remove(event.getPlayer().getUniqueId());
	  }
	  
	  public void setRestoreEffect(Player player, PotionEffect effect) {
	    boolean shouldCancel = true;
	    Collection<PotionEffect> activeList = player.getActivePotionEffects();
	    for (PotionEffect active : activeList)
	      if (active.getType().equals(effect.getType()))
	      {

	        if (effect.getAmplifier() < active.getAmplifier()) {
	          return;
	        }
	        if ((effect.getAmplifier() == active.getAmplifier()) && (effect.getDuration() < active.getDuration())) {
	          return;
	        }
	        restores.put(player.getUniqueId(), active.getType(), active);
	        shouldCancel = false;
	        break;
	      }
	    player.addPotionEffect(effect, true);
	    if ((shouldCancel) && (effect.getDuration() > 100) && (effect.getDuration() < BardClass.DEFAULT_MAX_DURATION)) {
	      restores.remove(player.getUniqueId(), effect.getType());
	    }
	  }
	  
	  @EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
	  public void onPotionEffectExpire(PotionEffectExpireEvent event)
	  {
	    LivingEntity livingEntity = event.getEntity();
	    Player player; PotionEffect previous; if (((livingEntity instanceof Player)) && ((previous = (PotionEffect)restores.remove((player = (Player)livingEntity).getUniqueId(), event.getEffect().getType())) != null)) {
	      event.setCancelled(true);
	      player.addPotionEffect(previous, true);
	    }
	  }
	}