package net.hcriots.hcf.listener.combatloggers;

import java.beans.ConstructorProperties;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.hcriots.hcf.listener.combatloggers.LoggerEntity;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Event;

public class LoggerDeathEvent
extends Event
{
@ConstructorProperties({"loggerEntity"})
public LoggerDeathEvent(LoggerEntity loggerEntity)
{
this.loggerEntity = loggerEntity;
}

private static final HandlerList handlers = new HandlerList();
private final LoggerEntity loggerEntity;

public LoggerEntity getLoggerEntity()
{
return this.loggerEntity;
}

public HandlerList getHandlers()
{
return handlers;
}

public static HandlerList getHandlerList()
{
return handlers;
}
}