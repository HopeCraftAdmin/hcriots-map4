package net.hcriots.hcf.timer;

import lombok.Data;
import lombok.Getter;
import net.hcriots.hcf.HCF;
import net.hcriots.hcf.eventutils.EventTimer;
import net.hcriots.hcf.timer.type.*;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.hcriots.utils.Config;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class TimerManager implements Listener {

    @Getter
	public
    final CombatTimer combatTimer;

    @Getter
    private final LogoutTimer logoutTimer;

    @Getter
	public
    final EnderPearlTimer enderPearlTimer;

    @Getter
    private final EventTimer eventTimer;


    @Getter
	public
    final GappleTimer gappleTimer;

    @Getter
    private final StartingTimer invincibilityTimer;

    @Getter
    public final ArcherTimer archerTimer;

    @Getter
    private final ClassLoad pvpClassWarmupTimer;

    @Getter
    private final StuckTimer stuckTimer;

    @Getter
	public
    final TeleportTimer teleportTimer;

    @Getter
    private final Set<Timer> timers = new LinkedHashSet<>();

    private final JavaPlugin plugin;
    private Config config;

	public Object pvpProtectionTimer;

    public TimerManager(HCF plugin) {
        (this.plugin = plugin).getServer().getPluginManager().registerEvents(this, plugin);
        this.registerTimer(this.enderPearlTimer = new EnderPearlTimer(plugin));
        this.registerTimer(this.logoutTimer = new LogoutTimer());
        this.registerTimer(this.gappleTimer = new GappleTimer(plugin));
        this.registerTimer(this.stuckTimer = new StuckTimer());
        this.registerTimer(this.invincibilityTimer = new StartingTimer(plugin));
        this.registerTimer(this.combatTimer = new CombatTimer(plugin));
        this.registerTimer(this.teleportTimer = new TeleportTimer(plugin));
        this.registerTimer(this.eventTimer = new EventTimer(plugin));
        this.registerTimer(this.pvpClassWarmupTimer = new ClassLoad(plugin));
        this.registerTimer(this.archerTimer = new ArcherTimer(plugin));
        this.reloadTimerData();
    }

    public void registerTimer(Timer timer) {
        this.timers.add(timer);
        if (timer instanceof Listener) {
            this.plugin.getServer().getPluginManager().registerEvents((Listener) timer, this.plugin);
        }
    }

    public void unregisterTimer(Timer timer) {
        this.timers.remove(timer);
    }

    /**
     * Reloads the {@link Timer} data from storage.
     */
    public void reloadTimerData() {
        this.config = new Config((HCF) plugin, "timers");
        for (Timer timer : this.timers) {
            timer.load(this.config);
        }
    }

    /**
     * Saves the {@link Timer} data to storage.
     */
    public void saveTimerData() {
        for (Timer timer : this.timers) {
            timer.onDisable(this.config);
        }

        this.config.save();
    }
    
    public CombatTimer getCombatTimer() {
        return this.combatTimer;
    }
    
    public GappleTimer getGappleTimer() {
    	return this.gappleTimer;
    }
    
    public LogoutTimer getLogoutTimer() {
        return this.logoutTimer;
    }
    
    public EnderPearlTimer getEnderPearlTimer() {
        return this.enderPearlTimer;
    }
    
    public EventTimer getEventTimer() {
        return this.eventTimer;
    }
        
    public StuckTimer getStuckTimer() {
        return this.stuckTimer;
    }
    
    public StartingTimer getInvincibilityTimer() {
        return this.invincibilityTimer;
    }
    
    public TeleportTimer getTeleportTimer() {
        return this.teleportTimer;
    }

	public Collection<Timer> getTimers() {
		return this.timers;
	}
	
}
