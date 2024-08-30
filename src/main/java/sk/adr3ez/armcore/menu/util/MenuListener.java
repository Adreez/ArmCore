package sk.adr3ez.armcore.menu.util;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import sk.adr3ez.armcore.menu.Menu;
import sk.adr3ez.armcore.menu.event.MenuCloseEvent;

public class MenuListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void clickEvent(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player player))
			return;
		
		final Menu menu = Menu.get(player);
		
		if (menu == null || event.getCurrentItem() == null)
			return;
		
		event.setCancelled(true); //TODO Enable clicking on buttons which are not protected
		menu.performClick(player, event);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onSwapEvent(PlayerSwapHandItemsEvent event) {
		//TODO Enable bottom inventory if neither of the hands were from button which is protected
		if (Menu.get(event.getPlayer()) != null)
			event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onItemDrop(PlayerDropItemEvent event) {
		//TODO Enable bottom inventory dropping if menu does not use it
		if (Menu.get(event.getPlayer()) != null)
			event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onInventoryDragEvent(InventoryDragEvent event) {
		//TODO Enable bottom inventory dragging if menu does not use it
		if (event.getWhoClicked() instanceof Player player)
			if (Menu.get(player) != null)
				event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void inventoryClose(InventoryCloseEvent event) {
		
		if (!(event.getPlayer() instanceof Player player))
			return;
		
		Menu menu = Menu.get(player);
		if (menu != null) {
			MenuCloseEvent menuCloseEvent = MenuCloseEvent.callEvent(player, menu);
			menu.handleClose();
		}
	}
	
	public enum MenuClickLocation {
		MENU, PLAYER_INVENTORY, ELSE
	}
	
}
