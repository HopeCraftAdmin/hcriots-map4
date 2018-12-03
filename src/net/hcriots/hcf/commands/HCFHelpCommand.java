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

/**
 *
 */
public class HCFHelpCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender p, Command command, String label, String[] args) {
        if (!(p instanceof Player)) {
            p.sendMessage(ChatColor.RED + "This command is only executable by players.");
            return true;
        }
        p.sendMessage("§7§m---------------------------------------------------");
        p.sendMessage("§4§lMap Information:");
        p.sendMessage(" §7§l* §cWarzone Size §7» §f800 x 800");
        p.sendMessage(" §7§l* §cWorld Border §7» §f3000 x 3000");
        p.sendMessage(" §7§l* §cFaction Cap §7» §f7 Man, 0 Ally");
        new Text(" §7§l* §cMap Kit §7»  §f(Click here to view the Kit)").setColor(ChatColor.RED).setClick(ClickAction.RUN_COMMAND, "/mapkit").send((CommandSender)p);
        p.sendMessage(" ");
        p.sendMessage("§4§lUseful Commands:");
        new Text(" §7§l* §c/coords §7» §f(Click here to view all coords)").setColor(ChatColor.RED).setClick(ClickAction.RUN_COMMAND, "/coords").send((CommandSender)p);
        p.sendMessage(" §7§l* §c/ores check (player) §7» §fCheck a players mined ores");
        p.sendMessage(" ");
        p.sendMessage("§4§lWebsite Info:");
        p.sendMessage(" §7§l* §cForums §7» §fwww.hcriots.org");
        p.sendMessage(" §7§l* §cTwitter §7» §fwww.twitter.com/HCRiots");
        p.sendMessage(" §7§l* §cTeamSpeak §7» §fts.hcriots.org");
        p.sendMessage(" §7§l* §cStore §7» §fstore.hcriots.org");
        p.sendMessage("");
        p.sendMessage("§7§m---------------------------------------------------");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}