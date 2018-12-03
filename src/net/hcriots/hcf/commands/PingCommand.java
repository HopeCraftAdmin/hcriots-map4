package net.hcriots.hcf.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.hcriots.utils.BukkitUtils;
import com.hcriots.utils.internal.com.hcriots.base.BaseConstants;

public class PingCommand implements CommandExecutor {

  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    Player target;
    if ((args.length > 0) && (sender.hasPermission(command.getPermission() + ".others")))
    {
      target = BukkitUtils.playerWithNameOrUUID(args[0]);
    }
    else
    {
      if (!(sender instanceof Player))
      {
        sender.sendMessage(ChatColor.RED + "Usage: " + "/ping <player>");
        return true;
      }
      target = (Player)sender;
    }
    if ((target == null) || (!PingCommand.canSee(sender, target)))
    {
      sender.sendMessage(String.format(BaseConstants.PLAYER_WITH_NAME_OR_UUID_NOT_FOUND, new Object[] { args[0] }));
      return true;
    }
    sender.sendMessage((target.equals(sender) ? (ChatColor.GREEN + "Your current ping is ") : (ChatColor.GRAY + "Ping of �c" + (target.getName() + " �7is �c"))) + target.getPing());
    return true;
  }

  
  public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
  {
    return (args.length == 1) && (sender.hasPermission(command.getPermission() + ".others")) ? null : Collections.emptyList();
  }
  public static boolean canSee(CommandSender sender, Player target)
  {
    return (target != null) && ((!(sender instanceof Player)) || (((Player)sender).canSee(target)));
  }
}
