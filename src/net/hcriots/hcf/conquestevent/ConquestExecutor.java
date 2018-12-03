package net.hcriots.hcf.conquestevent;

import com.hcriots.utils.other.command.ArgumentExecutor;

import net.hcriots.hcf.HCF;

public class ConquestExecutor extends ArgumentExecutor {

    public ConquestExecutor(HCF plugin) {
        super("conquest");
        addArgument(new ConquestSetpointsArgument(plugin));
    }
}
