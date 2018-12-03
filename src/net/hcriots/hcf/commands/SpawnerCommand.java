package net.hcriots.hcf.commands;

import org.bukkit.command.*;
import net.hcriots.hcf.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import com.hcriots.utils.*;
import net.minecraft.util.org.apache.commons.lang3.text.*;
import org.bukkit.inventory.*;
import java.util.*;

public class SpawnerCommand implements CommandExecutor, TabCompleter
{
    private final HCF plugin;
    
    public SpawnerCommand(final HCF plugin) {
        this.plugin = plugin;
    }
    
    public String C(final String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "/spawner <entity>");
            return false;
        }
        final String spawner = args[0];
        final Player p = (Player)sender;
        final Inventory inv = (Inventory)p.getInventory();
        inv.addItem(new ItemStack[] { new ItemBuilder(Material.MOB_SPAWNER).displayName(ChatColor.GREEN + "Spawner").loreLine(ChatColor.WHITE + WordUtils.capitalizeFully(spawner)).build() });
        p.sendMessage(this.C("&cYou just got a &e" + spawner + "&c."));
        return false;
    }
    
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        return Collections.emptyList();
    }
}
