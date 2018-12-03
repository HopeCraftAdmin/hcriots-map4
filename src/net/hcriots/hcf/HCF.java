package net.hcriots.hcf;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.hcriots.utils.internal.com.hcriots.base.BasePlugin;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import lombok.Getter;
import net.hcriots.hcf.armors.PvpClassManager;
import net.hcriots.hcf.armors.bard.EffectRestorer;
import net.hcriots.hcf.commands.BuyCommand;
import net.hcriots.hcf.commands.ClearChatCommand;
import net.hcriots.hcf.commands.CobbleCommand;
import net.hcriots.hcf.commands.CoordsCommand;
import net.hcriots.hcf.commands.FreezeCommand;
import net.hcriots.hcf.commands.GiveCrowbarCommand;
import net.hcriots.hcf.commands.GlowstoneMountain;
import net.hcriots.hcf.commands.GoppleCommand;
import net.hcriots.hcf.commands.HCFHelpCommand;
import net.hcriots.hcf.commands.HubCommand;
import net.hcriots.hcf.commands.LogoutCommand;
import net.hcriots.hcf.commands.MapKitCommand;
import net.hcriots.hcf.commands.MedicCommand;
import net.hcriots.hcf.commands.MediaCommand;
import net.hcriots.hcf.commands.OresCommand;
import net.hcriots.hcf.commands.PingCommand;
import net.hcriots.hcf.commands.PlayTimeCommand;
import net.hcriots.hcf.commands.RefundCommand;
import net.hcriots.hcf.commands.RegenCommand;
import net.hcriots.hcf.commands.RenameCommand;
import net.hcriots.hcf.commands.SpawnerCommand;
import net.hcriots.hcf.commands.StaffServerCommand;
import net.hcriots.hcf.commands.ToggleCapzoneEntryCommand;
import net.hcriots.hcf.commands.ToggleLightningCommand;
import net.hcriots.hcf.conquestevent.ConquestExecutor;
import net.hcriots.hcf.deathban.Deathban;
import net.hcriots.hcf.deathban.DeathbanListener;
import net.hcriots.hcf.deathban.DeathbanManager;
import net.hcriots.hcf.deathban.FlatFileDeathbanManager;
import net.hcriots.hcf.deathban.StaffReviveCommand;
import net.hcriots.hcf.deathban.lives.LivesExecutor;
import net.hcriots.hcf.deathban.lives.PvpTimerCommand;
import net.hcriots.hcf.economy.EconomyCommand;
import net.hcriots.hcf.economy.EconomyManager;
import net.hcriots.hcf.economy.FlatFileEconomyManager;
import net.hcriots.hcf.economy.PayCommand;
import net.hcriots.hcf.economy.ShopSignListener;
import net.hcriots.hcf.endoftheworld.EotwCommand;
import net.hcriots.hcf.endoftheworld.EotwHandler;
import net.hcriots.hcf.endoftheworld.EotwListener;
import net.hcriots.hcf.eventutils.CaptureZone;
import net.hcriots.hcf.eventutils.EventExecutor;
import net.hcriots.hcf.eventutils.EventScheduler;
import net.hcriots.hcf.faction.FactionExecutor;
import net.hcriots.hcf.faction.FactionManager;
import net.hcriots.hcf.faction.FactionMember;
import net.hcriots.hcf.faction.FactionUser;
import net.hcriots.hcf.faction.FlatFileFactionManager;
import net.hcriots.hcf.faction.args.FactionClaimChunkArgument;
import net.hcriots.hcf.faction.args.FactionManagerArgument;
import net.hcriots.hcf.faction.claim.Claim;
import net.hcriots.hcf.faction.claim.ClaimHandler;
import net.hcriots.hcf.faction.claim.ClaimWandListener;
import net.hcriots.hcf.faction.claim.Subclaim;
import net.hcriots.hcf.faction.claim.SubclaimWandListener;
import net.hcriots.hcf.faction.type.CapturableFaction;
import net.hcriots.hcf.faction.type.ClaimableFaction;
import net.hcriots.hcf.faction.type.ConquestFaction;
import net.hcriots.hcf.faction.type.EndPortalFaction;
import net.hcriots.hcf.faction.type.Faction;
import net.hcriots.hcf.faction.type.GlowstoneMountainFaction;
import net.hcriots.hcf.faction.type.KothFaction;
import net.hcriots.hcf.faction.type.PlayerFaction;
import net.hcriots.hcf.faction.type.RoadFaction;
import net.hcriots.hcf.faction.type.SpawnFaction;
import net.hcriots.hcf.kothevent.KothExecutor;
import net.hcriots.hcf.listener.AutoSmeltOreListener;
import net.hcriots.hcf.listener.BookDeenchantListener;
import net.hcriots.hcf.listener.BorderListener;
import net.hcriots.hcf.listener.BottledExpListener;
import net.hcriots.hcf.listener.ChatListener;
import net.hcriots.hcf.listener.Cooldowns;
import net.hcriots.hcf.listener.CoreListener;
import net.hcriots.hcf.listener.CreativeClickListener;
import net.hcriots.hcf.listener.CreeperFriendlyListener;
import net.hcriots.hcf.listener.CrowbarListener;
import net.hcriots.hcf.listener.DeathListener;
import net.hcriots.hcf.listener.DeathMessageListener;
import net.hcriots.hcf.listener.ElevatorListener;
import net.hcriots.hcf.listener.EndListener;
import net.hcriots.hcf.listener.EntityLimitListener;
import net.hcriots.hcf.listener.ExpMultiplierListener;
import net.hcriots.hcf.listener.FactionListener;
import net.hcriots.hcf.listener.FastEverythingListener;
import net.hcriots.hcf.listener.FoundDiamondsListener;
import net.hcriots.hcf.listener.GlowstoneListener;
import net.hcriots.hcf.listener.ItemRemoverListener;
import net.hcriots.hcf.listener.MineListener;
import net.hcriots.hcf.listener.PlayTimeManager;
import net.hcriots.hcf.listener.PortalListener;
import net.hcriots.hcf.listener.PotListener;
import net.hcriots.hcf.listener.ProtectionListener;
import net.hcriots.hcf.listener.SignSubclaimListener;
import net.hcriots.hcf.listener.StrengthListener;
import net.hcriots.hcf.listener.UnRepairableListener;
import net.hcriots.hcf.listener.UserManager;
import net.hcriots.hcf.listener.WorldListener;
import net.hcriots.hcf.listener.combatloggers.CombatLogListener;
import net.hcriots.hcf.listener.combatloggers.CustomEntityRegistration;
import net.hcriots.hcf.listener.fixes.ArmorFixListener;
import net.hcriots.hcf.listener.fixes.BlockHitFixListener;
import net.hcriots.hcf.listener.fixes.BlockJumpGlitchFixListener;
import net.hcriots.hcf.listener.fixes.BoatGlitchFixListener;
import net.hcriots.hcf.listener.fixes.ColonCommandFixListener;
import net.hcriots.hcf.listener.fixes.CreatureSpawn;
import net.hcriots.hcf.listener.fixes.EnchantLimitListener;
import net.hcriots.hcf.listener.fixes.EnderChestRemovalListener;
import net.hcriots.hcf.listener.fixes.EnderpearlRefundListener;
import net.hcriots.hcf.listener.fixes.HungerFixListener;
import net.hcriots.hcf.listener.fixes.InfinityArrowFixListener;
import net.hcriots.hcf.listener.MobFixesListener;
import net.hcriots.hcf.listener.fixes.PearlGlitchListener;
import net.hcriots.hcf.listener.fixes.PearlLandListener;
import net.hcriots.hcf.listener.fixes.PhaseFixListener;
import net.hcriots.hcf.listener.fixes.PluginViewListener;
import net.hcriots.hcf.listener.fixes.PortalTrapFixListener;
import net.hcriots.hcf.listener.fixes.PotionLimitListener;
import net.hcriots.hcf.listener.fixes.VoidGlitchFixListener;
import net.hcriots.hcf.listener.fixes.WeatherFixListener;
import net.hcriots.hcf.listener.render.ProtocolLibHook;
import net.hcriots.hcf.listener.render.VisualiseHandler;
import net.hcriots.hcf.listener.render.WallBorderListener;
import net.hcriots.hcf.scoreboard.ScoreboardHandler;
import net.hcriots.hcf.startoftheworld.SotwCommand;
import net.hcriots.hcf.startoftheworld.SotwListener;
import net.hcriots.hcf.startoftheworld.SotwTimer;
import net.hcriots.hcf.systems.crates.KeyListener;
import net.hcriots.hcf.systems.crates.KeyManager;
import net.hcriots.hcf.systems.signs.DeathSignListener;
import net.hcriots.hcf.systems.signs.EventSignListener;
import net.hcriots.hcf.timer.TimerExecutor;
import net.hcriots.hcf.timer.TimerManager;
import net.hcriots.hcf.ymls.SettingsYML;

public class HCF extends JavaPlugin {

    @Getter private static HCF instance;
    @Getter private PlayTimeManager playTimeManager;
    @Getter private Random random = new Random();
    @Getter private ClaimHandler claimHandler;
    @Getter private DeathbanManager deathbanManager;
    @Getter private EconomyManager economyManager;
    @Getter private EffectRestorer effectRestorer;
    @Getter private EotwHandler eotwHandler;
    @Getter private EventScheduler eventScheduler;
    @Getter private FactionManager factionManager;
    @Getter private FoundDiamondsListener foundDiamondsListener;
    @Getter private PvpClassManager pvpClassManager;
    @Getter private ScoreboardHandler scoreboardHandler;
    @Getter private SotwTimer sotwTimer;
    @Getter private TimerManager timerManager;
    @Getter private KeyManager keyManager;
    @Getter private FactionUser factionUser;    
    @Getter private UserManager userManager;
    @Getter private VisualiseHandler visualiseHandler;
    @Getter private WorldEditPlugin worldEdit;
    
	public static String PREFIX = "";

    
    private CombatLogListener combatLogListener;

    public CombatLogListener getCombatLogListener()
    {
        return this.combatLogListener;
    }
    
    public void resetGlowstoneMountain() {
    	for(int x = 502 ; x <= 582 ; x++) {
    		for(int y = 76 ; y <= 112 ; y++) {
    			for(int z = 594 ; z > 514 ; z--) {
    	    			Block block = Bukkit.getWorld("world_nether").getBlockAt(new Location(Bukkit.getWorld("world_nether"), -1 * x, y ,z));
    	    			if(block.getType().equals(Material.AIR)) {
    	    				block.setType(Material.GLOWSTONE);
    	    			}
    	    	}
        	}
    	}
    }

    @Override
    public void onEnable() {
    	File file = new File(getDataFolder(), "config.yml");
    	if(!file.exists()) {
    		this.getConfig().options().copyDefaults(true);
    		this.saveConfig();
    	}
        Cooldowns.createCooldown("medic_cooldown");

        instance = this;
        BasePlugin.getPlugin().init(this);

        ProtocolLibHook.hook(this);

        Plugin wep = getServer().getPluginManager().getPlugin("WorldEdit");
        this.worldEdit = wep instanceof WorldEditPlugin && wep.isEnabled() ? (WorldEditPlugin) wep : null;
        CustomEntityRegistration.registerCustomEntities();

        SettingsYML.init(this);
        this.effectRestorer = new EffectRestorer(this);
        this.registerConfiguration();
        this.registerCommands();
        this.registerManagers();
        this.registerListeners();
        
        this.resetGlowstoneMountain();

        new BukkitRunnable() {
            @Override
            public void run() {
                getServer().broadcast(ChatColor.GREEN.toString() + ChatColor.BOLD + "Saving!" + "\n" + ChatColor.GREEN + "Saved all faction and player data to the database." + "\n" + ChatColor.GRAY + "Current TPS: " + ChatColor.GRAY, "hcf.seesaves");
                saveData();
            }
        }.runTaskTimerAsynchronously(this, TimeUnit.MINUTES.toMillis(10L), TimeUnit.MINUTES.toMillis(10L));
    }

    private void saveData() {
        this.combatLogListener.removeCombatLoggers();
        this.deathbanManager.saveDeathbanData();
        this.economyManager.saveEconomyData();
        this.factionManager.saveFactionData();
        this.keyManager.saveKeyData();
        this.timerManager.saveTimerData();
        this.userManager.saveUserData();
    }

    @Override
    public void onDisable() {
        this.pvpClassManager.onDisable();
        this.scoreboardHandler.clearBoards();
        this.factionManager.saveFactionData();
        this.deathbanManager.saveDeathbanData();
        this.economyManager.saveEconomyData();
        this.factionManager.saveFactionData();
        this.keyManager.saveKeyData();
        this.timerManager.saveTimerData();
        this.userManager.saveUserData();
        this.playTimeManager.savePlaytimeData();
        this.saveData();

        instance = null; // always initialise last
    }

    private void registerConfiguration() {
        ConfigurationSerialization.registerClass(CaptureZone.class);
        ConfigurationSerialization.registerClass(Deathban.class);
        ConfigurationSerialization.registerClass(Claim.class);
        ConfigurationSerialization.registerClass(Subclaim.class);
        ConfigurationSerialization.registerClass(Deathban.class);
        ConfigurationSerialization.registerClass(FactionUser.class);
        ConfigurationSerialization.registerClass(ClaimableFaction.class);
        ConfigurationSerialization.registerClass(ConquestFaction.class);
        ConfigurationSerialization.registerClass(CapturableFaction.class);
        ConfigurationSerialization.registerClass(KothFaction.class);
        ConfigurationSerialization.registerClass(EndPortalFaction.class);
        ConfigurationSerialization.registerClass(Faction.class);
        ConfigurationSerialization.registerClass(FactionMember.class);
        ConfigurationSerialization.registerClass(PlayerFaction.class);
        ConfigurationSerialization.registerClass(RoadFaction.class);
        ConfigurationSerialization.registerClass(SpawnFaction.class);
        ConfigurationSerialization.registerClass(GlowstoneMountainFaction.class);
        ConfigurationSerialization.registerClass(RoadFaction.NorthRoadFaction.class);
        ConfigurationSerialization.registerClass(RoadFaction.EastRoadFaction.class);
        ConfigurationSerialization.registerClass(RoadFaction.SouthRoadFaction.class);
        ConfigurationSerialization.registerClass(RoadFaction.WestRoadFaction.class);
    }

    private void registerListeners() {
        PluginManager manager = this.getServer().getPluginManager();
        this.playTimeManager = new PlayTimeManager(this);
        manager.registerEvents(this.playTimeManager, this);
        manager.registerEvents(this.combatLogListener = new CombatLogListener(this), this);
        manager.registerEvents(new FactionManagerArgument(this), this);
        manager.registerEvents(new ArmorFixListener(), this);
        manager.registerEvents(new BlockHitFixListener(), this);
        manager.registerEvents(new BlockJumpGlitchFixListener(), this);
        manager.registerEvents(new BoatGlitchFixListener(), this);
        manager.registerEvents(new BookDeenchantListener(), this);
        manager.registerEvents(new FactionClaimChunkArgument(this), this);
        manager.registerEvents(new BorderListener(), this);
        manager.registerEvents(new CreatureSpawn(), this);
        manager.registerEvents(new CobbleCommand(), this);
        manager.registerEvents(new BottledExpListener(), this);
        manager.registerEvents(new PortalTrapFixListener(this), this);
        manager.registerEvents(new ChatListener(this), this);
        manager.registerEvents(new ClaimWandListener(this), this);
        manager.registerEvents(new CoreListener(this), this);
        manager.registerEvents(new ElevatorListener(this), this);
        manager.registerEvents(new CrowbarListener(this), this);
        manager.registerEvents(new DeathListener(this), this);
        manager.registerEvents(new DeathMessageListener(this), this);
        manager.registerEvents(new DeathSignListener(this), this);
        manager.registerEvents(new WeatherFixListener(), this);
        if (SettingsYML.KIT_MAP == false) {
        manager.registerEvents(new DeathbanListener(this), this);
        }
        manager.registerEvents(new EnchantLimitListener(), this);
        manager.registerEvents(new EnderChestRemovalListener(), this);
        manager.registerEvents(new EntityLimitListener(), this);
        manager.registerEvents(new EndListener(), this);
        manager.registerEvents(new EotwListener(this), this);
        manager.registerEvents(new EventSignListener(), this);
        manager.registerEvents(new ExpMultiplierListener(), this);
        manager.registerEvents(new FactionListener(this), this);
        manager.registerEvents(this.foundDiamondsListener = new FoundDiamondsListener(this), this);
        manager.registerEvents(new FastEverythingListener(this), this);
        manager.registerEvents(new InfinityArrowFixListener(), this);
        manager.registerEvents(new KeyListener(this), this);
        manager.registerEvents(new PearlGlitchListener(this), this);
        manager.registerEvents(new PhaseFixListener(), this);
        manager.registerEvents(new PortalListener(this), this);
        manager.registerEvents(new PotionLimitListener(), this);
        manager.registerEvents(new ProtectionListener(this), this);
        manager.registerEvents(new SubclaimWandListener(this), this);
        manager.registerEvents(new SignSubclaimListener(this), this);
        manager.registerEvents(new ShopSignListener(this), this);
        manager.registerEvents(new SotwListener(this), this);
        manager.registerEvents(new CreeperFriendlyListener(), this);
        manager.registerEvents(new VoidGlitchFixListener(), this);
        manager.registerEvents(new UnRepairableListener(), this);
        manager.registerEvents(new WallBorderListener(this), this);
        manager.registerEvents(new AutoSmeltOreListener(), this);
        manager.registerEvents(new WorldListener(this), this);
        manager.registerEvents(new PluginViewListener(), this);
        manager.registerEvents(new MineListener(this), this);
        manager.registerEvents(new ItemRemoverListener(this), this);
        manager.registerEvents(new PotListener(this), this);
        manager.registerEvents(new GlowstoneListener(this), this);
        manager.registerEvents(new StrengthListener(), this);
        manager.registerEvents(new CreativeClickListener(), this);
        manager.registerEvents(new ArmorFixListener(), this);
        manager.registerEvents(new HungerFixListener(this), this);
        manager.registerEvents(new MobFixesListener(), this);
        manager.registerEvents(new ColonCommandFixListener(), this);
        manager.registerEvents(new PearlLandListener(), this);
        manager.registerEvents(new EnderpearlRefundListener(this), this);
    }

    private void registerCommands()
    {
        getCommand("buy").setExecutor(new BuyCommand());
        getCommand("clearchat").setExecutor(new ClearChatCommand());
        getCommand("cobble").setExecutor(new CobbleCommand());
        getCommand("hub").setExecutor(new HubCommand(this));
        getCommand("amethyst").setExecutor(new MedicCommand(this));
        getCommand("crowgive").setExecutor(new GiveCrowbarCommand());
        getCommand("hcfhelp").setExecutor(new HCFHelpCommand());
        getCommand("coords").setExecutor(new CoordsCommand());
        getCommand("conquest").setExecutor((CommandExecutor) new ConquestExecutor(this));
        getCommand("economy").setExecutor(new EconomyCommand(this));
        getCommand("refund").setExecutor(new RefundCommand());
        getCommand("eotw").setExecutor(new EotwCommand(this));
        getCommand("event").setExecutor((CommandExecutor) new EventExecutor(this));
        getCommand("hcfhelp").setExecutor(new HCFHelpCommand());
        getCommand("faction").setExecutor((CommandExecutor) new FactionExecutor(this));
        getCommand("gopple").setExecutor(new GoppleCommand(this));
        getCommand("koth").setExecutor((CommandExecutor) new KothExecutor(this));
        getCommand("lives").setExecutor((CommandExecutor) new LivesExecutor(this));
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("freeze").setExecutor(new FreezeCommand(this));
        getCommand("logout").setExecutor(new LogoutCommand(this));
        getCommand("mapkit").setExecutor(new MapKitCommand(this));
        getCommand("media").setExecutor(new MediaCommand());
        getCommand("pay").setExecutor(new PayCommand(this));
        getCommand("pvptimer").setExecutor(new PvpTimerCommand(this));
        getCommand("regen").setExecutor(new RegenCommand(this));
        getCommand("sotw").setExecutor(new SotwCommand(this));
        getCommand("staffrevive").setExecutor(new StaffReviveCommand(this));
        getCommand("staffserver").setExecutor((CommandExecutor)new StaffServerCommand(this));
        getCommand("timer").setExecutor((CommandExecutor) new TimerExecutor(this));
        getCommand("spawner").setExecutor((CommandExecutor)new SpawnerCommand(this));
        getCommand("togglecapzoneentry").setExecutor(new ToggleCapzoneEntryCommand(this));
        getCommand("togglelightning").setExecutor(new ToggleLightningCommand(this));
        getCommand("rename").setExecutor(new RenameCommand(this));
        getCommand("ores").setExecutor(new OresCommand(this));
        getCommand("playtime").setExecutor(new PlayTimeCommand());
        getCommand("glowstonemountain").setExecutor(new GlowstoneMountain(this));


        Map<String, Map<String, Object>> map = getDescription().getCommands();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            PluginCommand command = getCommand(entry.getKey());
            command.setPermission("command." + entry.getKey());
            command.setPermissionMessage(ChatColor.RED + "You do not have permissions to execute this command.");
        }
    }
		
    private void registerCooldowns()
    {
        Cooldowns.createCooldown("medic_cooldown");
    }


    private void registerManagers() {
        this.claimHandler = new ClaimHandler(this);
        this.deathbanManager = new FlatFileDeathbanManager(this);
        this.economyManager = new FlatFileEconomyManager(this);
        this.eotwHandler = new EotwHandler(this);
        this.eventScheduler = new EventScheduler(this);
        this.factionManager = new FlatFileFactionManager(this);
        this.keyManager = new KeyManager(this);
        this.pvpClassManager = new PvpClassManager(this);
        this.sotwTimer = new SotwTimer();
        this.timerManager = new TimerManager(this); // needs to be registered before ScoreboardHandler
        this.scoreboardHandler = new ScoreboardHandler(this);
        this.userManager = new UserManager(this);
        this.visualiseHandler = new VisualiseHandler();
	}
    
    public PlayTimeManager getPlayTimeManager()
    {
        return this.playTimeManager;
    }
    
    public static HCF getInstance() {
    	return HCF.instance;
    }
    
    public Random getRandom() {
        return this.random;
    }
    
    public ClaimHandler getClaimHandler() {
        return this.claimHandler;
    }
    
    public DeathbanManager getDeathbanManager() {
        return this.deathbanManager;
    }
    
    public EconomyManager getEconomyManager() {
        return this.economyManager;
    }
    
    public EffectRestorer getEffectRestorer() {
        return this.effectRestorer;
    }
    
    public EotwHandler getEotwHandler() {
        return this.eotwHandler;
    }
    
    public static SotwTimer getSotwRunnable() {
    	return HCF.getSotwRunnable();
    }
    
    public FactionUser getFactionUser() {
    	return this.factionUser;
    }
    
    public EventScheduler getEventScheduler() {
        return this.eventScheduler;
    }
    
    public FactionManager getFactionManager() {
        return this.factionManager;
    }
    
    public FoundDiamondsListener getFoundDiamondsListener() {
        return this.foundDiamondsListener;
    }
    
    public KeyManager getKeyManager() {
        return this.keyManager;
    }
    
    public PvpClassManager getPvpClassManager() {
        return this.pvpClassManager;
    }
    
    public ScoreboardHandler getScoreboardHandler() {
        return this.scoreboardHandler;
    }
    
    public SotwTimer getSotwTimer() {
        return this.sotwTimer;
    }
    
    public TimerManager getTimerManager() {
        return this.timerManager;
    }
    
    public UserManager getUserManager() {
        return this.userManager;
    }
    
    public VisualiseHandler getVisualiseHandler() {
        return this.visualiseHandler;
    }
    
    public WorldEditPlugin getWorldEdit() {
        return this.worldEdit;
    }

	public static ChatColor getRemaining(long remaining, boolean b, boolean c) {
		// TODO Auto-generated method stub
		return null;
	}

}
