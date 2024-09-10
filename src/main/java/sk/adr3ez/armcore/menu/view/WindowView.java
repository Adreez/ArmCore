package sk.adr3ez.armcore.menu.view;


import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import sk.adr3ez.armcore.menu.Menu;
import sk.adr3ez.armcore.menu.button.ButtonHandler;
import sk.adr3ez.armcore.menu.button.MenuButton;
import sk.adr3ez.armcore.menu.util.Range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Getter
public class WindowView {

	private final ButtonHandler buttonHandler = new ButtonHandler(this);
	@Setter
    private Collection<Integer> slots = new ArrayList<>();

    public WindowView(Integer... slots) {
		Bukkit.getLogger().info("WindowView slots: " + Arrays.toString(slots)); // TODO DEBUG
        this.slots.addAll(List.of(slots));
    }

    public WindowView(Range... ranges) {
        for (Range range : ranges) {
            this.slots.addAll(List.of(range.getValues()));
        }
    }
	
	public WindowView() {
	}
	
	public WindowView(List<Integer> slots) {
		this.slots.addAll(slots);
	}
	
	public MenuButton getButton(int slot) {
        return buttonHandler.getButtons().get(slot);
    }

    public WindowView addButton(int slot, MenuButton menuButton) {
        this.buttonHandler.addButton(slot, menuButton);
	    return this;
    }
	
	/**
	 * Add button at next free slot
	 * @param menuButton button to add
	 * @return this
	 */
	public WindowView addButton(MenuButton menuButton) {
		int slot = this.getFreeSlot();
		if (slot != -1)
			this.buttonHandler.addButton(slot, menuButton);
		return this;
	}
	
	public WindowView fill(Material material) {
		this.fill(new ItemStack(material));
		return this;
	}
	
	public WindowView fill(ItemStack itemStack) {
		for (Integer slot : slots) {
			buttonHandler.addButton(slot, new MenuButton() {
				@Override
				public void onClick(@NotNull Player player, @NotNull InventoryClickEvent clickEvent, @NotNull Menu clickedMenu) {}
				@Override
				public ItemStack getItemStack() {
					return itemStack;
				}
			});
		}
		return this;
	}
	
	private int getFreeSlot() {
		for (int i = 0; i < slots.size(); i++) {
			if (!buttonHandler.getButtons().containsKey(i))
				return i;
		}
		return -1;
	}
	
	public void update() {
		//TODO Update buttons - update raw name, lore, itemstack
	}

}
