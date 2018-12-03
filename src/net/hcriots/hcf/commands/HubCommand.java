package net.hcriots.hcf.commands;

import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.hcriots.hcf.HCF;
import net.minecraft.util.org.apache.commons.io.output.ByteArrayOutputStream;

public class HubCommand implements CommandExecutor{

    private final HCF plugin;
    public HubCommand(final HCF plugin) {
        this.plugin = plugin;
    }

    public static void teleport(Player pl, String input)
    {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(input);
        }
        catch (IOException localIOException) {}
        pl.sendPluginMessage(HCF.getInstance(), "BungeeCord", b.toByteArray());
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is only executable by players.");
            return true;
        }
        Player p = (Player)sender;
        teleport(p, "lobby");
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(HCF.getInstance(), new Runnable(){
            public void run(){
                if(p.isOnline()){
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cWe couldn't connect you to any of the Hubs, please try again later."));
                }
            }
        }, 20 * 5);
        return true;
    }

}
