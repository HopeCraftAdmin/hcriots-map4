package net.hcriots.hcf.faction.args.staff;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.hcriots.utils.other.command.CommandArgument;

import net.hcriots.hcf.HCF;
import net.hcriots.hcf.faction.FactionMember;
import net.hcriots.hcf.faction.struct.Role;
import net.hcriots.hcf.faction.type.PlayerFaction;

import java.util.Collections;
import java.util.List;

public class FactionForceDemoteArgument extends CommandArgument {

    private final HCF plugin;

    public FactionForceDemoteArgument(HCF plugin) {
        super("forcedemote", "Forces the demotion status of a player.");
        this.plugin = plugin;
        this.permission = "hcf.command.faction.argument." + getName();
    }

    @Override
    public String getUsage(String label) {
        return '/' + label + ' ' + getName() + " <playerName>";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: " + getUsage(label));
            return true;
        }

        PlayerFaction playerFaction = plugin.getFactionManager().getContainingPlayerFaction(args[1]);

        if (playerFaction == null) {
            sender.sendMessage(ChatColor.RED + "Faction containing member with IGN or UUID " + args[1] + " not found.");
            return true;
        }

        FactionMember factionMember = playerFaction.getMember(args[1]);

        if (factionMember == null) {
            sender.sendMessage(ChatColor.RED + "Faction containing member with IGN or UUID " + args[1] + " not found.");
            return true;
        }

        if (factionMember.getRole() != Role.LEADER) {
            sender.sendMessage(ChatColor.RED + factionMember.getName() + " is a " + factionMember.getRole().getName() + "; cannot be demoted.");
            return true;
        }

        factionMember.setRole(Role.MEMBER);
        playerFaction.broadcast(ChatColor.GOLD.toString() + ChatColor.BOLD + sender.getName() + " has been forcefully assigned as a member.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return args.length == 2 ? null : Collections.emptyList();
    }
}
