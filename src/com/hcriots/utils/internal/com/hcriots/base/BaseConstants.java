package com.hcriots.utils.internal.com.hcriots.base;

import org.bukkit.*;

public final class BaseConstants {
    public static final String PLAYER_WITH_NAME_OR_UUID_NOT_FOUND;

    static {
        PLAYER_WITH_NAME_OR_UUID_NOT_FOUND = ChatColor.GOLD + "Player with name or UUID '" + ChatColor.WHITE + "%1$s" + ChatColor.GOLD + "' not found.";
    }
}
