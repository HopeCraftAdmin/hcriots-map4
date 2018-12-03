package net.hcriots.hcf.listener.fixes;

import org.bukkit.event.*;
import org.bukkit.*;
import java.util.*;

public class ServerSecurityListener implements Listener
{
    public static List<String> allowedOps;
    public static List<Material> blacklistedBlocks;
    
    static {
        ServerSecurityListener.allowedOps = new ArrayList<String>();
        ServerSecurityListener.blacklistedBlocks = new ArrayList<Material>();
    }
    
    public ServerSecurityListener() {
        ServerSecurityListener.blacklistedBlocks.add(Material.BEDROCK);
        ServerSecurityListener.allowedOps.add("InspectMC");
    }
}
