package net.hcriots.hcf.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class ClearChatCommand implements CommandExecutor, TabCompleter {

	@Override
    public boolean onCommand(CommandSender p, Command command, String label, String[] args) {
        if (!(p instanceof Player)) {
            p.sendMessage(ChatColor.RED + "This command is only executable by players.");
            return true;
        }
        for (int i = 0; i < 100; i++) {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ""));  	
        }
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&aGlobal chat has been cleared by an staff member"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}