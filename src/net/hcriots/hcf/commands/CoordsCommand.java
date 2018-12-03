package net.hcriots.hcf.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.hcriots.utils.BukkitUtils;

public class CoordsCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender p, Command command, String label, String[] args) {
        if (!(p instanceof Player)) {
            p.sendMessage(ChatColor.RED + "This command is only executable by players.");
            return true;
        }
        p.sendMessage("");
        p.sendMessage("§7§m---------------------------------------------------");
        p.sendMessage("§4§lKoths:");
        p.sendMessage(" ");
        p.sendMessage(" §7§l* §cMountain KoTH§f: 500, 500 §7(Overworld)");
        p.sendMessage(" §7§l* §cSnowman KoTH§f: -500 500 §7(Overworld)");
        p.sendMessage(" §7§l* §cSpike KoTH§f: 500, -500 §7(Overworld)");
        p.sendMessage(" §7§l* §cIgloo KoTH§f: -500, -500 §7(Overworld)");
        p.sendMessage(" ");
        p.sendMessage("§6Glowstone Mountain§f: 345, 303 §7(Nether)");
        p.sendMessage("§7Glowstone Mountain resets every 30 minutes)");
        p.sendMessage("§7§m---------------------------------------------------");
        p.sendMessage("");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}