package sk.adr3ez.armcore.menu.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import sk.adr3ez.armcore.event.BaseEvent;
import sk.adr3ez.armcore.menu.Menu;

public class MenuCloseEvent extends BaseEvent {
	
	@Getter
	private final Player player;
	@Getter
	private final Menu menu;
	private boolean cancelled;
	
	private MenuCloseEvent(Player player, Menu menu) {
		this.player = player;
		this.menu = menu;
	}
	
	public static MenuCloseEvent callEvent(Player player, Menu menu) {
		MenuCloseEvent event = new MenuCloseEvent(player, menu);
		BaseEvent.callEvent(event);
		return event;
	}
	
}
