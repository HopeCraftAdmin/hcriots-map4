package net.hcriots.hcf.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class MediaCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender p, Command command, String label, String[] args) {
        if (!(p instanceof Player)) {
            p.sendMessage(ChatColor.RED + "This command is only executable by players.");
            return true;
        }
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&m------------------------------------------- "));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lYouTuber Requirements"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7* &cYou must have &4&l500 &csubscribers"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7* &cYou need to upload at least &4&l2 &cvideos on the server"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7* &cYou are required to have atleast &4&l200 - 300 views on recent videos."));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7* &4&lFamous Requirements"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7* &cYou must have &4&l1500 &csubscribers"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7* &cYou need to upload at least &4&l2 &cvideos on the server"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7* &cYou are required to have atleast &4&l750 views on recent videos."));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&m------------------------------------------- "));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}