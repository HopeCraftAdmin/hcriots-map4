package net.hcriots.hcf.faction.args;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.hcriots.utils.other.command.CommandArgument;

import net.hcriots.hcf.HCF;
import net.hcriots.hcf.faction.FactionMember;
import net.hcriots.hcf.faction.struct.ChatChannel;
import net.hcriots.hcf.faction.struct.Relation;
import net.hcriots.hcf.faction.struct.Role;
import net.hcriots.hcf.faction.type.Faction;
import net.hcriots.hcf.faction.type.PlayerFaction;
import net.hcriots.hcf.ymls.SettingsYML;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Faction argument used to accept invitations from {@link Faction}s.
 */
public class FactionManagerArgument extends CommandArgument implements Listener {

    public Inventory factionManager;
    private final HCF plugin;

    public FactionManagerArgument(HCF plugin) {
        super("manage", "Manage your faction using a GUI");
        this.plugin = plugin;
    }

    @Override
    public String getUsage(String label) {
        return '/' + label;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = (Player) sender;
        PlayerFaction playerFaction = HCF.getInstance().getFactionManager().getPlayerFaction(player);
        this.factionManager = Bukkit.createInventory(null, 36, "Faction Management");

        if (playerFaction == null) {
            player.sendMessage(ChatColor.RED + "You don't have a faction");
            return true;
        }


        if (playerFaction.getMember(player).getRole() == Role.LEADER) {
            player.openInventory(factionManager);

            for (Player p : playerFaction.getOnlinePlayers()) {
                ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                ItemMeta meta = skull.getItemMeta();
                meta.setLore((Arrays.asList(new String[]{ChatColor.translateAlternateColorCodes('&',"&aRight click to &2&lDEMOTE &athis player."),
                        (ChatColor.translateAlternateColorCodes('&',"&aLeft click to &2&LPROMOTE &athis player.")),ChatColor.translateAlternateColorCodes('&',"&aMiddle click to make this person &2&lLEADER&a.")})));
                meta.setDisplayName(p.getName());
                skull.setItemMeta(meta);
                factionManager.addItem(skull);
            }
        } else {
            player.sendMessage(ChatColor.RED + "You are not a leader.");
        }
        return false;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        Inventory inventory = event.getInventory();

        if (inventory.getName().equals("Faction Management")) {

            if (clicked.getType() == Material.SKULL_ITEM) {
                if (event.getClick() == ClickType.LEFT) {
                    Bukkit.dispatchCommand(player, "f promote " + clicked.getItemMeta().getDisplayName());
                    event.setCancelled(true);
                }
                if (event.getClick() == ClickType.MIDDLE){
                    Bukkit.dispatchCommand(player, "f leader " + clicked.getItemMeta().getDisplayName());
                    event.setCancelled(true);
                }
                if (event.getClick() == ClickType.RIGHT) {
                    Bukkit.dispatchCommand(player, "f demote " + clicked.getItemMeta().getDisplayName());
                    event.setCancelled(true);
                }
            }
        }
    }
}