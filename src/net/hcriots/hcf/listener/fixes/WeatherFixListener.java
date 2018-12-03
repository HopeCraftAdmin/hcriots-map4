package net.hcriots.hcf.listener.fixes;

import org.bukkit.event.weather.*;
import org.bukkit.*;
import org.bukkit.event.*;

public class WeatherFixListener implements Listener
{
    @EventHandler
    public void onWeatherChange(final WeatherChangeEvent e) {
        if (e.getWorld().getEnvironment() == World.Environment.NORMAL && e.toWeatherState()) {
            e.setCancelled(true);
        }
    }
}