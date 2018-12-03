package net.hcriots.hcf.listener.combatloggers;

import java.beans.ConstructorProperties;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.hcriots.hcf.listener.combatloggers.LoggerEntity;

public class LoggerRemovedEvent
extends Event
{
@ConstructorProperties({"loggerEntity"})
public LoggerRemovedEvent(LoggerEntity loggerEntity)
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

