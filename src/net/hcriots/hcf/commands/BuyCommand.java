package net.hcriots.hcf.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.hcriots.utils.other.chat.ClickAction;
import com.hcriots.utils.other.chat.Text;

import java.util.Collections;
import java.util.List;

public class BuyCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender p, Command command, String label, String[] args) {
        if (!(p instanceof Player)) {
            p.sendMessage(ChatColor.RED + "This command is only executable by players.");
            return true;
        }
        p.sendMessage("§7§m----------------------------------");
        p.sendMessage("§7§lOur Store §f- §cstore.hcriots.org:");
        p.sendMessage("§7§m----------------------------------");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}