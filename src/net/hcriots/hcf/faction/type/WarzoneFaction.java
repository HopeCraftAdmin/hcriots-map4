package net.hcriots.hcf.faction.type;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.hcriots.hcf.ymls.SettingsYML;

import java.util.Map;

/**
 * Represents the {@link WarzoneFaction}.
 */
public class WarzoneFaction extends Faction {

    public WarzoneFaction() {
        super("Warzone");
    }

    public WarzoneFaction(Map<String, Object> map) {
        super(map);
    }

    @Override
    public String getDisplayName(CommandSender sender) {
        return SettingsYML.WARZONE_COLOUR + getName();
    }
}
