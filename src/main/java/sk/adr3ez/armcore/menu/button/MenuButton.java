package sk.adr3ez.armcore.menu.button;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import sk.adr3ez.armcore.menu.Menu;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class MenuButton {

    private final ItemStack itemStack;
	private final Collection<String> rawLore = new ArrayList<>();
	private boolean update = false;
    private ClickAction clickAction = null;
	private String rawName;

    public MenuButton(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public MenuButton withClickAction(ClickAction action) {
        this.clickAction = action;
		return this;
    }
	
	public MenuButton setUpdate(boolean bool) {
		this.update = bool;
		return this;
	}
	
	public void update() {
		ItemMeta itemMeta = itemStack.getItemMeta();
		assert itemMeta != null;
		itemMeta.setDisplayName(rawName);
		itemMeta.setLore(rawLore.stream().toList());
	}
	
	/*public MenuButton withClickRequirement() {
		//TODO
		return this;
	}
	
	public MenuButton withViewRequirement() {
		//TODO
		return this;
	}*/

    public interface ClickAction {

        void click(@NotNull Player player, @NotNull InventoryClickEvent event, @NotNull Menu menu);

    }

}
