   package net.hcriots.hcf.scoreboard.helper;

import com.earth2me.essentials.api.Economy;
import com.google.common.collect.Ordering;
import com.hcriots.utils.BukkitUtils;
import com.hcriots.utils.DurationFormatter;

import net.hcriots.hcf.HCF;
import net.hcriots.hcf.armors.PvpClass;
import net.hcriots.hcf.armors.archer.ArcherClass;
import net.hcriots.hcf.armors.archer.ArcherMark;
import net.hcriots.hcf.armors.bard.BardClass;
import net.hcriots.hcf.armors.miner.MinerClass;
import net.hcriots.hcf.commands.CobbleCommand;
import net.hcriots.hcf.endoftheworld.EotwHandler;
import net.hcriots.hcf.eventutils.EventTimer;
import net.hcriots.hcf.eventutils.tracker.ConquestTracker;
import net.hcriots.hcf.faction.FactionManager;
import net.hcriots.hcf.faction.FactionUser;
import net.hcriots.hcf.faction.type.ConquestFaction;
import net.hcriots.hcf.faction.type.EventFaction;
import net.hcriots.hcf.faction.type.KothFaction;
import net.hcriots.hcf.faction.type.PlayerFaction;
import net.hcriots.hcf.listener.DateTimeFormats;
import net.hcriots.hcf.scoreboard.SidebarEntry;
import net.hcriots.hcf.scoreboard.SidebarProvider;
import net.hcriots.hcf.startoftheworld.SotwTimer;
import net.hcriots.hcf.timer.PlayerTimer;
import net.hcriots.hcf.timer.Timer;
import net.hcriots.hcf.timer.type.CombatTimer;
import net.hcriots.hcf.ymls.SettingsYML;
import net.md_5.bungee.api.ChatColor;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.potion.PotionEffectType;


import java.text.DecimalFormat;
import java.time.Duration;
import java.util.*;

public class ScoreboardHelper implements SidebarProvider {

    public static final ThreadLocal<DecimalFormat> CONQUEST_FORMATTER = new ThreadLocal<DecimalFormat>() {
        @Override
        protected DecimalFormat initialValue() {
            return new DecimalFormat("00.0");
        }
    };
    protected static final String STRAIGHT_LINE = BukkitUtils.STRAIGHT_LINE_DEFAULT.substring(0, 14);
    private static final SidebarEntry EMPTY_ENTRY_FILLER = new SidebarEntry(" "," "," ");
   private static final Comparator<Map.Entry<UUID, ArcherMark>> ARCHER_MARK_COMPARATOR = (o1, o2) -> o1.getValue().compareTo(o2.getValue());
   public static Economy econ = null;

    private final HCF plugin;

    public ScoreboardHelper(HCF plugin) {
        this.plugin = plugin;
    }

    public SidebarEntry add(String s){

        if(s.length() < 10){
            return new SidebarEntry(s);
        }

        if(s.length() > 10 && s.length() < 20){
            return new SidebarEntry(s.substring(0, 10), s.substring(10, s.length()), "");
        }

        if(s.length() > 20){
            return new SidebarEntry(s.substring(0, 10), s.substring(10, 20), s.substring(20, s.length()));
        }

        return null;
    }

    @Override
    public String getTitle() {
        return SettingsYML.SCOREBOARD_TITLE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<SidebarEntry> getLines(Player player) {
        PlayerFaction playerFaction = HCF.getInstance().getFactionManager().getPlayerFaction(player);
        List<SidebarEntry> lines = new ArrayList<>();
        
        EventTimer eventTimer = plugin.getTimerManager().getEventTimer();
        List<SidebarEntry> conquestLines = null;
        
        SotwTimer.SotwRunnable sotwRunnable = plugin.getSotwTimer().getSotwRunnable();

        if (sotwRunnable != null) {
            lines.add(new SidebarEntry(ChatColor.YELLOW.toString() + ChatColor.BOLD, "SOTW ends in ", ChatColor.WHITE + DurationFormatter.getRemaining(sotwRunnable.getRemaining(), true)));
        }

        EotwHandler.EotwRunnable eotwRunnable = plugin.getEotwHandler().getRunnable();

        if (eotwRunnable != null) {
            long remaining = eotwRunnable.getMillisUntilStarting();
            if (remaining > 0L) {
                lines.add(new SidebarEntry(ChatColor.RED.toString() + ChatColor.BOLD, "EOTW" + ChatColor.RED + " begins", " in " + ChatColor.BOLD + DurationFormatter.getRemaining(remaining, true)));
            } else if ((remaining = eotwRunnable.getMillisUntilCappable()) > 0L) {
                lines.add(new SidebarEntry(ChatColor.RED.toString() + ChatColor.BOLD, "EOTW" + ChatColor.RED + " capable", " in " + ChatColor.BOLD + DurationFormatter.getRemaining(remaining, true)));
            }
        }

        EventFaction eventFaction = eventTimer.getEventFaction();
        if (eventFaction instanceof KothFaction) {
           // lines.add(new SidebarEntry(ChatColor.AQUA.toString(), ChatColor.BOLD + "Active Events", null));
            lines.add(new SidebarEntry(eventTimer.getScoreboardPrefix(), eventFaction.getScoreboardName() + ChatColor.GRAY, ": " +
                    ChatColor.WHITE + DurationFormatter.getRemaining(eventTimer.getRemaining(), true)));
        } else if (eventFaction instanceof ConquestFaction) {
            ConquestFaction conquestFaction = (ConquestFaction) eventFaction;
            DecimalFormat format = CONQUEST_FORMATTER.get();

            conquestLines = new ArrayList<>();

            conquestLines.add(new SidebarEntry(" " +
                    ChatColor.RED.toString() + conquestFaction.getRed().getScoreboardRemaining(),
                    ChatColor.GOLD + " | ",
                    ChatColor.YELLOW.toString() + conquestFaction.getYellow().getScoreboardRemaining()));

            conquestLines.add(new SidebarEntry(" " +
                    ChatColor.GREEN.toString() + conquestFaction.getGreen().getScoreboardRemaining(),
                    ChatColor.GOLD + " | " + ChatColor.RESET,
                    ChatColor.AQUA.toString() + conquestFaction.getBlue().getScoreboardRemaining()));

            // Show the top 3 factions next.
            ConquestTracker conquestTracker = (ConquestTracker) conquestFaction.getEventType().getEventTracker();
            int count = 0;
            for (Map.Entry<PlayerFaction, Integer> entry : conquestTracker.getFactionPointsMap().entrySet()) {
                String factionName = entry.getKey().getName();
                if (factionName.length() > 14) factionName = factionName.substring(0, 14);
                for (int i = 0; i < 3; i++) {
                    conquestLines.add(new SidebarEntry(ChatColor.GOLD.toString() + ChatColor.BOLD + " " + count++ + ". ", ChatColor.YELLOW + factionName, ChatColor.GRAY + ": " + ChatColor.WHITE + entry.getValue()));
                }
                if (++count == 3) break;
            }
        }

        if (conquestLines != null && !conquestLines.isEmpty()) {
            if (!lines.isEmpty()) {
                conquestLines.add(new SidebarEntry("", "", ""));
            }

            conquestLines.addAll(lines);
            lines = conquestLines;
        }
        Collection<Timer> timers = (Collection<Timer>)this.plugin.getTimerManager().getTimers();
        // Show the current PVP Class statistics of the player.
        PvpClass pvpClass = plugin.getPvpClassManager().getEquippedClass(player);
        if (pvpClass != null) {
            if (pvpClass instanceof BardClass) {
                BardClass bardClass = (BardClass) pvpClass;
                lines.add(new SidebarEntry(ChatColor.AQUA + "", ChatColor.BOLD + "Bard Energy", ChatColor.AQUA + ": " + ChatColor.WHITE + handleBardFormat(bardClass.getEnergyMillis(player), true)));
                long remaining2 = bardClass.getRemainingBuffDelay(player);
                if (remaining2 > 0L) {
                    lines.add(new SidebarEntry(ChatColor.GREEN + "", ChatColor.BOLD + "Bard Effect", ChatColor.GREEN + ": " + ChatColor.WHITE +  DurationFormatter.getRemaining(remaining2, true)));
                }
                long remaining = bardClass.getRemainingBuffDelay(player);
                if (remaining > 0) {
                }
            }
            if (pvpClass instanceof ArcherClass) {
                UUID uuid = player.getUniqueId();
                ArcherClass archerClass = (ArcherClass)pvpClass;
                long timestamp = ArcherClass.archerSpeedCooldowns.get(uuid);
                long millis = System.currentTimeMillis();
                long remaining3 = (timestamp == ArcherClass.archerSpeedCooldowns.getNoEntryValue()) ? -1L : (timestamp - millis);
                if (remaining3 > 0L) {
                    lines.add(new SidebarEntry(" " + ChatColor.GOLD + ChatColor.BOLD, "Delay", ChatColor.GRAY + ": " + DurationFormatter.getRemaining(remaining3, true)));
                }
            }
        }
    

        for (Timer timer : timers) {
            if (timer instanceof PlayerTimer) {
                PlayerTimer playerTimer = (PlayerTimer)timer;
                long remaining4 = playerTimer.getRemaining(player);
                if (remaining4 <= 0L) {
                    continue;
                }
                String timerName = playerTimer.getName();
                if (timerName.length() > 14) {
                    timerName = timerName.substring(0, timerName.length());
                }
                lines.add(new SidebarEntry(playerTimer.getScoreboardPrefix(), timerName + ChatColor.GRAY, ": " + ChatColor.WHITE + ((timer instanceof CombatTimer) ? DurationFormatter.getRemaining(remaining4, false) : DurationFormatter.getRemaining(remaining4, true))));
            }
        }
    if (!lines.isEmpty())
   {
      lines.add(0, new SidebarEntry(ChatColor.GRAY, STRAIGHT_LINE, STRAIGHT_LINE));
        lines.add(lines.size(), new SidebarEntry(ChatColor.GRAY, ChatColor.STRIKETHROUGH + STRAIGHT_LINE, STRAIGHT_LINE));
   }

    return lines;
}

    private static String handleBardFormat(long millis, boolean trailingZero) {
        return (trailingZero ? DateTimeFormats.REMAINING_SECONDS_TRAILING : DateTimeFormats.REMAINING_SECONDS).get().format(millis * 0.001);
    }

    public static String translate(String input)
    {
        return ChatColor.translateAlternateColorCodes('&', input);
        
    }
}
