package net.hcriots.hcf.kothevent;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.hcriots.utils.other.command.ArgumentExecutor;

import net.hcriots.hcf.HCF;
import net.hcriots.hcf.kothevent.args.KothHelpArgument;
import net.hcriots.hcf.kothevent.args.KothNextArgument;
import net.hcriots.hcf.kothevent.args.KothScheduleArgument;
import net.hcriots.hcf.kothevent.args.KothSetCapDelayArgument;

/**
 * Command used to handle KingOfTheHills.
 */
public class KothExecutor extends ArgumentExecutor {

    private final KothScheduleArgument kothScheduleArgument;

    public KothExecutor(HCF plugin) {
        super("koth");

        addArgument(new KothHelpArgument(this));
        addArgument(new KothNextArgument(plugin));
        addArgument(this.kothScheduleArgument = new KothScheduleArgument(plugin));
        addArgument(new KothSetCapDelayArgument(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            this.kothScheduleArgument.onCommand(sender, command, label, args);
            return true;
        }

        return super.onCommand(sender, command, label, args);
    }
}
