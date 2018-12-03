package net.hcriots.hcf.faction.type;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import net.hcriots.hcf.faction.claim.Claim;
import net.hcriots.hcf.ymls.SettingsYML;

import java.util.Map;

/**
 * Represents the {@link SpawnFaction}.
 */
public class SpawnFaction extends ClaimableFaction implements ConfigurationSerializable {

    public SpawnFaction() {
        super("Spawn");

        this.safezone = true;
                    }
            
        
    

    public SpawnFaction(Map<String, Object> map) {
        super(map);
    }
    @Override
    public boolean isDeathban() {
        return false;
    }
}