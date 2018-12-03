package net.hcriots.hcf.commands;


import org.bukkit.command.*;

import net.hcriots.hcf.listener.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.apache.commons.lang3.*;


public class RefundCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender cs, final Command cmd, final String s, final String[] args) {
        final String Usage = ChatColor.RED + "/" + s + " <playerName> <reason>";
        if (!(cs instanceof Player)) {
            cs.sendMessage(ChatColor.RED + "You must be a player");
            return true;
        }
        final Player p = (Player)cs;
        if (args.length < 2) {
            cs.sendMessage(Usage);
            return true;
        }
        if (Bukkit.getPlayer(args[0]) == null) {
            p.sendMessage(ChatColor.RED + "Player must be online");
            return true;
        }
        final Player target = Bukkit.getPlayer(args[0]);
        //Check if player has an inventory saved.
        if (DeathListener.PlayerInventoryContents.containsKey(target.getUniqueId())) {
            target.getInventory().setContents((ItemStack[])DeathListener.PlayerInventoryContents.get(target.getUniqueId()));
            target.getInventory().setArmorContents((ItemStack[])DeathListener.PlayerArmorContents.get(target.getUniqueId()));
            final String reason = StringUtils.join((Object[])args, ' ', 2, args.length);
            Command.broadcastCommandMessage((CommandSender)p, ChatColor.GREEN + "Successfully returned " + target.getName() + "'s items");
            DeathListener.PlayerArmorContents.remove(target.getUniqueId());
            DeathListener.PlayerInventoryContents.remove(target.getUniqueId());
            return true;
        }
        //If player does not have an inventory saved.
        p.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + target.getName() + ChatColor.GREEN + "'s has been already rollback by other staff member");
        return false;
    }
}

