package net.hcriots.hcf.commands;

import net.hcriots.hcf.HCF;
import net.hcriots.hcf.faction.event.PlayerFreezeEvent;
import net.minecraft.util.gnu.trove.map.*;
import net.minecraft.util.gnu.trove.map.hash.*;

import java.util.concurrent.*;
import org.bukkit.plugin.*;
import org.bukkit.command.*;
import org.apache.commons.lang3.time.*;
import org.apache.commons.lang.*;
import org.bukkit.potion.*;
import org.bukkit.scheduler.*;

import com.hcriots.utils.BukkitUtils;
import com.hcriots.utils.other.chat.ClickAction;
import com.hcriots.utils.other.chat.Text;

import java.util.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.player.*;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.*;
import net.minecraft.util.org.apache.commons.lang3.*;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;

import org.bukkit.event.inventory.*;
import org.apache.commons.lang3.time.*;

public class FreezeCommand implements CommandExecutor, Listener
{
    private static final String FREEZE_BYPASS = "command.freeze.bypass";
    public static TObjectLongMap<UUID> frozenPlayers;
    public static HashMap<Player, String> frozenReasons;
    private long defaultFreezeDuration;
    private long serverFrozenMillis;
    private HashSet<String> frozen;
    
    static {
        FreezeCommand.frozenPlayers = (TObjectLongMap<UUID>)new TObjectLongHashMap();
        FreezeCommand.frozenReasons = new HashMap<Player, String>();
    }
    
    public FreezeCommand(final HCF plugin) {
        this.frozen = new HashSet<String>();
        this.defaultFreezeDuration = TimeUnit.MINUTES.toMillis(60L);
        Bukkit.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)plugin);
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: " + "/freeze <player>");
            return true;
        }
        final String reason = null;
        Long freezeTicks = this.defaultFreezeDuration;
        final long millis = System.currentTimeMillis();
        if (args[0].equalsIgnoreCase("all") && sender.hasPermission(String.valueOf(command.getPermission()) + ".all")) {
            final long oldTicks = this.getRemainingServerFrozenMillis();
            if (oldTicks > 0L) {
                freezeTicks = 0L;
            }
            this.serverFrozenMillis = millis + this.defaultFreezeDuration;
            Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "The server is " + ((freezeTicks > 0L) ? ("now frozen for " + DurationFormatUtils.formatDurationWords((long)freezeTicks, true, true)) : "no longer frozen") + ((reason == null) ? "" : (" with reason " + reason)) + '.');
            return true;
        }
        final Player target = Bukkit.getServer().getPlayer(args[0]);
        if (target == null || !PingCommand.canSee(sender, target)) {
            sender.sendMessage(ChatColor.GOLD + "Player '" + ChatColor.WHITE + args[0] + ChatColor.GOLD + "' not found.");
            return true;
        }
        final UUID targetUUID = target.getUniqueId();
        final boolean shouldFreeze = this.getRemainingPlayerFrozenMillis(targetUUID) > 0L;
        final PlayerFreezeEvent playerFreezeEvent = new PlayerFreezeEvent(target, shouldFreeze);
        Bukkit.getServer().getPluginManager().callEvent((Event)playerFreezeEvent);
        if (playerFreezeEvent.isCancelled()) {
            sender.sendMessage(ChatColor.RED + "Unable to freeze " + target.getName() + '.');
            return false;
        }
        if (shouldFreeze) {
            this.frozen.remove(target.getName());
            FreezeCommand.frozenPlayers.remove((Object)targetUUID);
            sender.sendMessage("§aYou have succesfully un-frozen §b" + target.getName());
            target.sendMessage(ChatColor.RED + "You have been un-frozen.");
            target.removePotionEffect(PotionEffectType.BLINDNESS);
            FreezeCommand.frozenReasons.remove(target);
        }
        else if (args.length == 1) {
            this.frozen.add(target.getName());
            FreezeCommand.frozenPlayers.put((UUID)targetUUID, millis + freezeTicks);
            final String timeString = DurationFormatUtils.formatDurationWords((long)freezeTicks, true, true);
            this.Message(target.getName());
            sender.sendMessage("§aYou have succesfully frozen §b" + target.getName());
        }
        else {
            this.frozen.add(target.getName());
            FreezeCommand.frozenPlayers.put((UUID)targetUUID, millis + freezeTicks);
            final String timeString = DurationFormatUtils.formatDurationWords((long)freezeTicks, true, true);
            this.Message(target.getName());
            sender.sendMessage("§aYou have succesfully frozen §b" + target.getName());
            final String reason2 = StringUtils.join((Object[])args, ' ', 1, args.length);
            FreezeCommand.frozenReasons.put(target, reason2);
        }
        return true;
    }
    
    private void Message(final String name) {
        final HashMap timer = new HashMap();
        final Player p = Bukkit.getPlayer(name);
        final BukkitTask task = new BukkitRunnable() {
            public void run() {
                if (FreezeCommand.this.frozen.contains(name)) {
                    p.sendMessage(" ");
                    p.sendMessage("§f\u2588\u2588\u2588\u2588§c\u2588§f\u2588\u2588\u2588\u2588");
                    p.sendMessage("§f\u2588\u2588\u2588§c\u2588§6\u2588§c\u2588§f\u2588\u2588\u2588");
                    p.sendMessage("§f\u2588\u2588§c\u2588§6\u2588§0\u2588§6\u2588§c\u2588§f\u2588\u2588 §4§lDo NOT log out!");
                    p.sendMessage("§f\u2588\u2588§c\u2588§6\u2588§0\u2588§6\u2588§c\u2588§f\u2588\u2588 §cIf you do, you will be banned!");
                    p.sendMessage("§f\u2588§c\u2588§6\u2588\u2588§0\u2588§6\u2588\u2588§c\u2588§f\u2588 §ePlease download §eTeamspeak §eand connect to:");
                    p.sendMessage("§f\u2588§c\u2588§6\u2588\u2588\u2588\u2588\u2588§c\u2588§f\u2588 " + "§ets.hcriots.org");
                    p.sendMessage("§c\u2588§6\u2588\u2588\u2588§0\u2588§6\u2588\u2588\u2588§c\u2588");
                    p.sendMessage("§c\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588");
                    p.sendMessage(" ");
                }
                else {
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously((Plugin)HCF.getInstance(), 0L, 200L);
    }
    
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        return (args.length == 1) ? null : Collections.emptyList();
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {
        final Entity entity = event.getEntity();
        if (entity instanceof Player) {
            final Player attacker = BukkitUtils.getFinalAttacker((EntityDamageEvent)event, false);
            if (attacker == null) {
                return;
            }
            final Player player = (Player)entity;
            if ((this.getRemainingServerFrozenMillis() > 0L || this.getRemainingPlayerFrozenMillis(player.getUniqueId()) > 0L) && !player.hasPermission("command.freeze.bypass")) {
                attacker.sendMessage(ChatColor.RED + player.getName() + " is currently frozen, you may not attack.");
                event.setCancelled(true);
                return;
            }
            if ((this.getRemainingServerFrozenMillis() > 0L || this.getRemainingPlayerFrozenMillis(attacker.getUniqueId()) > 0L) && !attacker.hasPermission("command.freeze.bypass")) {
                event.setCancelled(true);
                attacker.sendMessage(ChatColor.RED + "You may not attack players whilst frozen.");
            }
        }
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPreCommandProcess(final PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        if ((this.getRemainingServerFrozenMillis() > 0L || this.getRemainingPlayerFrozenMillis(player.getUniqueId()) > 0L) && !player.hasPermission("command.freeze.bypass")) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You may not use commands whilst frozen.");
        }
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerMove(final PlayerMoveEvent event) {
        final Location from = event.getFrom();
        final Location to = event.getTo();
        if (from.getBlockX() == to.getBlockX() && from.getBlockZ() == to.getBlockZ()) {
            return;
        }
        final Player player = event.getPlayer();
        if ((this.getRemainingServerFrozenMillis() > 0L || this.getRemainingPlayerFrozenMillis(player.getUniqueId()) > 0L) && !player.hasPermission("command.freeze.bypass")) {
            event.setTo(event.getFrom());
        }
    }
    
     @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent e) {
        if (this.frozen.contains(e.getPlayer().getUniqueId())) {
            for (final Player online : Bukkit.getOnlinePlayers()) {
                if (online.hasPermission("command.freeze")) {
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    new Text(ChatColor.WHITE + e.getPlayer().getName() + " has " + ChatColor.DARK_RED + "QUIT" + ChatColor.WHITE + " while frozen. " + ChatColor.GRAY + ChatColor.ITALIC + "(Click here to ban)").setHoverText(ChatColor.WHITE + "Click here to permanently ban" + ChatColor.GRAY + e.getPlayer().getName()).setClick(ClickAction.RUN_COMMAND, "/ban" + e.getPlayer().getName() + "Refusal to SS | Logout").setColor(ChatColor.GRAY).send((CommandSender)online);
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                }
            }
        }
     }
    
    public long getRemainingServerFrozenMillis() {
        return this.serverFrozenMillis - System.currentTimeMillis();
    }
    
    public long getRemainingPlayerFrozenMillis(final UUID uuid) {
        final long remaining = this.frozenPlayers.get((Object)uuid);
        if (remaining == this.frozenPlayers.getNoEntryValue()) {
            return 0L;
        }
        return remaining - System.currentTimeMillis();
    }
}