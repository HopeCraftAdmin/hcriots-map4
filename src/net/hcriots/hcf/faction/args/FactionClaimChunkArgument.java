package net.hcriots.hcf.faction.args;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.hcriots.utils.other.command.CommandArgument;

import net.hcriots.hcf.HCF;
import net.hcriots.hcf.faction.claim.Claim;
import net.hcriots.hcf.faction.claim.ClaimHandler;
import net.hcriots.hcf.faction.struct.Role;
import net.hcriots.hcf.faction.type.PlayerFaction;

public class FactionClaimChunkArgument extends CommandArgument implements Listener {

    private static final int CHUNK_RADIUS = 7;
    private final HCF plugin;

    public FactionClaimChunkArgument(HCF plugin) {
        super("claimchunk", "Claim a chunk of land in the Wilderness.", new String[] { "chunkclaim" });
        this.plugin = plugin;
    }

    @Override
    public String getUsage(String label) {
        return '/' + label + ' ' + getName();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is only executable by players.");
            return true;
        }

        Player player = (Player) sender;
        PlayerFaction playerFaction = plugin.getFactionManager().getPlayerFaction(player);

        if (playerFaction == null) {
            sender.sendMessage(ChatColor.RED + "You are not in a faction.");
            return true;
        }

        if (playerFaction.isRaidable()) {
            sender.sendMessage(ChatColor.RED + "You cannot claim land for your faction while raidable.");
            return true;
        }

        if (playerFaction.getMember(player.getUniqueId()).getRole() == Role.MEMBER) {
            sender.sendMessage(ChatColor.RED + "You must be an officer to claim land.");
            return true;
        }

        Location location = player.getLocation();
        plugin.getClaimHandler().tryPurchasing(
                player,
                new Claim(playerFaction, location.clone().add(CHUNK_RADIUS, ClaimHandler.MIN_CLAIM_HEIGHT, CHUNK_RADIUS), location.clone().add(-CHUNK_RADIUS, ClaimHandler.MAX_CLAIM_HEIGHT,
                        -CHUNK_RADIUS)));

        return true;
    }
}
