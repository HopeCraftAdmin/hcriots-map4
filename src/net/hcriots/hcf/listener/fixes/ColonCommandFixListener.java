package net.hcriots.hcf.listener.fixes;

import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.hcriots.hcf.HCF;

public class ColonCommandFixListener implements org.bukkit.event.Listener
{
  
  @org.bukkit.event.EventHandler
  public void onPlayerColonCommand(PlayerCommandPreprocessEvent e) {
    if ((e.getMessage().startsWith("/minecraft:")) || (e.getMessage().startsWith("bukkit:"))) {
      e.setCancelled(true);
    } else if (((e.getMessage().startsWith("/ver")) || (e.getMessage().startsWith("/about"))) && (!e.getPlayer().isOp())) {
      e.setCancelled(true);
    }
  }
}
