package net.hcriots.hcf.commands;

import org.bukkit.command.*;

import net.hcriots.hcf.*;

import org.bukkit.*;

public class GlowstoneMountain implements CommandExecutor
{
    private HCF hcf;
    
    public GlowstoneMountain(final HCF hcf) {
        this.hcf = hcf;
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!sender.hasPermission("command.glowstonemountain")) {
            sender.sendMessage(ChatColor.RED + "You don not have permission for this command.");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "You have reset Glowstone Mountain!");
            this.hcf.resetGlowstoneMountain();
        }
        return true;
    }
}
