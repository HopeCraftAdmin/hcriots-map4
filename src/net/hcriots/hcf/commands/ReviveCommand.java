package net.hcriots.hcf.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.hcriots.utils.BukkitUtils;

import net.hcriots.hcf.HCF;
import net.hcriots.hcf.deathban.Deathban;
import net.hcriots.hcf.faction.FactionUser;

public class ReviveCommand implements CommandExecutor, TabCompleter
{
    private final HCF plugin;
    
    public ReviveCommand(final HCF plugin) {
        this.plugin = plugin;
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /" + label + " <playerName>");
            return true;
        }
        final OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore() && !target.isOnline()) {
            sender.sendMessage(ChatColor.RED + "Player '" + ChatColor.WHITE + args[0] + ChatColor.RED + "' not found.");
            return true;
        }
        final UUID targetUUID = target.getUniqueId();
        final FactionUser factionTarget = HCF.getInstance().getUserManager().getUser(targetUUID);
        final Deathban deathban = factionTarget.getDeathban();
        if (deathban == null || !deathban.isActive()) {
            sender.sendMessage(ChatColor.RED + target.getName() + " is not death-banned.");
            return true;
        }
        factionTarget.removeDeathban();
        Command.broadcastCommandMessage(sender, ChatColor.GREEN + "A staff member has successfully revived " + target.getName() + ".");
        return false;
    }
    
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length != 1) {
            return Collections.emptyList();
        }
        final List<String> results = new ArrayList<String>();
        for (final FactionUser factionUser : this.plugin.getUserManager().getUsers().values()) {
            final Deathban deathban = factionUser.getDeathban();
            if (deathban != null && deathban.isActive()) {
                final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(factionUser.getUserUUID());
                final String name = offlinePlayer.getName();
                if (name == null) {
                    continue;
                }
                results.add(name);
            }
        }
        return BukkitUtils.getCompletions(args, results);
    }
}

