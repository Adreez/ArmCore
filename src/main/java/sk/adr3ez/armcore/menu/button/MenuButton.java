package sk.adr3ez.armcore.menu.button;

import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import sk.adr3ez.armcore.menu.Menu;

@NoArgsConstructor
public abstract class MenuButton {
	
	public abstract void onClick(@NotNull Player player, @NotNull InventoryClickEvent clickEvent, @NotNull Menu clickedMenu);
	
	public abstract ItemStack getItemStack();
	
}
