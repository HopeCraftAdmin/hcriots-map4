package net.hcriots.hcf.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.hcriots.hcf.listener.Crowbar;

public class GiveCrowbarCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String [] args) {
        Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("crowgive")) {
            if (args.length == 0) {
                p.sendMessage(ChatColor.RED + "Usage: /"+ label +  " <playerName>");
                return true;
            }
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null) {
                p.sendMessage(ChatColor.RED + "That player is not online!");
                return true;
            }

            ItemStack stack = new Crowbar().getItemIfPresent();
            target.getInventory().addItem(new ItemStack[]{stack});
            target.sendMessage(ChatColor.GREEN + "You were given a crowbar from " + sender.getName());
            p.sendMessage(ChatColor.GREEN + "You have given " + target.getName() + " a crowbar");
            return true;
        }
        return false;
    }
}
