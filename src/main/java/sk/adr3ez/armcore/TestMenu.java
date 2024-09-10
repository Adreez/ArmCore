package sk.adr3ez.armcore;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import sk.adr3ez.armcore.menu.ChestMenu;
import sk.adr3ez.armcore.menu.Menu;
import sk.adr3ez.armcore.menu.button.MenuButton;
import sk.adr3ez.armcore.menu.button.annotation.Button;
import sk.adr3ez.armcore.menu.util.Range;
import sk.adr3ez.armcore.menu.view.WindowView;

public class TestMenu extends ChestMenu {
	
	
	@Button(row = 2, snapPosition = Button.SnapPosition.CENTER)
	MenuButton menuButton = new MenuButton() {
		@Override
		public void onClick(@NotNull Player player, @NotNull InventoryClickEvent clickEvent, @NotNull Menu clickedMenu) {
			player.sendMessage("You fool!");
		}
		
		@Override
		public ItemStack getItemStack() {
			return new ItemStack(Material.DIAMOND);
		}
	};
	
	public TestMenu() {
		super();
		
		setRows(3);
		setTitle("Rotzz je šulín");
		
		WindowView windowView = new WindowView(new Range(0,8));
		windowView.fill(Material.BLACK_STAINED_GLASS_PANE);
		addWindow(windowView);
		
	}
}
