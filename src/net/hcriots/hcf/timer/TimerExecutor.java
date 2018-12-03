package net.hcriots.hcf.timer;

import com.hcriots.utils.other.command.ArgumentExecutor;

import net.hcriots.hcf.HCF;
import net.hcriots.hcf.timer.args.TimerCheckArgument;
import net.hcriots.hcf.timer.args.TimerSetArgument;

/**
 * Handles the execution and tab completion of the timer command.
 */
public class TimerExecutor extends ArgumentExecutor {

    public TimerExecutor(HCF plugin) {
        super("timer");

        addArgument(new TimerCheckArgument(plugin));
        addArgument(new TimerSetArgument(plugin));
    }
}