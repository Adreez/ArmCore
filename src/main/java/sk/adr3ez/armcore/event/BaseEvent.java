package sk.adr3ez.armcore.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BaseEvent extends Event {
	
	private static final HandlerList HANDLER_LIST = new HandlerList();
	
	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}
	
	public static void callEvent(BaseEvent baseEvent) {
		Bukkit.getPluginManager().callEvent(baseEvent);
	}
	
	@NotNull
	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}
}
