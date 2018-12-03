package net.hcriots.hcf.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.*;
import com.google.common.io.*;

import net.hcriots.hcf.HCF;
import net.md_5.bungee.api.ChatColor;

public class StaffServerCommand implements CommandExecutor
{
    private final HCF hcf;
    
    public StaffServerCommand(final HCF hcf) {
        this.hcf = hcf;
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender instanceof Player) {
            if (args.length != 1) {
                sender.sendMessage(ChatColor.YELLOW + "Invalid Usage: " + ChatColor.GRAY + "/" + label + " <server>");
            }
            else {
                final ByteArrayDataOutput dataOutput = ByteStreams.newDataOutput();
                dataOutput.writeUTF("Connect");
                dataOutput.writeUTF(args[0]);
                ((Player)sender).sendPluginMessage((Plugin)this.hcf, "BungeeCord", dataOutput.toByteArray());
                sender.sendMessage(ChatColor.YELLOW + "Sending you to " + ChatColor.GOLD + args[0]);
            }
        }
        else {
            sender.sendMessage(ChatColor.RED + "You need to be a staff member to do this");
        }
        return true;
    }
}
