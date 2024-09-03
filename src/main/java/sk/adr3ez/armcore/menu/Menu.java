package sk.adr3ez.armcore.menu;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.adr3ez.armcore.api.ArmCore;
import sk.adr3ez.armcore.menu.button.MenuButton;
import sk.adr3ez.armcore.menu.option.MenuOption;
import sk.adr3ez.armcore.menu.option.OptionsService;
import sk.adr3ez.armcore.menu.option.list.BottomInventoryOption;
import sk.adr3ez.armcore.menu.view.WindowView;
import sk.adr3ez.armcore.menu.view.manager.ViewFactory;

import java.util.*;

public abstract class Menu {
	
	private static final String METADATA_VALUE = "ArmCoreMenu";
	
	@Getter
	private final OptionsService options = new OptionsService(this);
	
	@Getter
	private final ViewFactory viewFactory = new ViewFactory();
	private final Set<BukkitTask> scheduledTasks = new HashSet<>();
	private Inventory inventory;
	@Setter
	@Getter
	private String title = "Default Title";
	@Nullable
	private Player player;
	
	
	public Menu() {
	}
	
	@Nullable
	public static Menu get(@NotNull Player player) {
		if (player.hasMetadata(METADATA_VALUE))
			return (Menu) player.getMetadata(METADATA_VALUE).get(0).value();
		else
			return null;
	}
	
	public void addTask(BukkitTask... tasks) {
		scheduledTasks.addAll(List.of(tasks));
	}
	
	public void addOption(MenuOption... options) {
		this.options.addOption(options);
	}
	
	protected abstract Inventory createInventory();
	
	public Inventory getInventory() {
		if (this.inventory == null)
			throw new IllegalStateException("Trying to get an inventory before menu was opened!");
		return this.inventory;
	}
	
	public Inventory getBottomInventory() throws IllegalStateException {
		if (this.player == null)
			throw new IllegalStateException("Trying to get an inventory before menu was opened!");
		//TODO Check if menuOption to use bottom inv is there
		if (this.options.contains(BottomInventoryOption.class))
			return this.player.getOpenInventory().getBottomInventory();
		else
			throw new IllegalStateException("Trying to get an bottom inventory before BottomInventoryOption was added!");
	}
	
	public void performClick(Player player, InventoryClickEvent event) {
		int slot = event.getSlot();
		for (WindowView window : viewFactory.getWindows()) {
			if (window.getSlots().contains(slot)) {
				MenuButton.ClickAction action = window.getButton(slot).getClickAction();
				if (action != null)
					action.click(player, event, this);
				break;
			}
		}
	}
	
	public void setButton(int slot, MenuButton button) {
		//TODO Check if slot is used in window
		// if yes, set button to window else add button to default window
		for (WindowView window : viewFactory.getWindows()) {
			if (window.getSlots().contains(slot)) {
				window.getButtonHandler().addButton(slot, button);
				Bukkit.getLogger().info("Button added to window: " + window.getSlots()); // TODO DEBUG
				return;
			}
		}
		Bukkit.getLogger().info("Button added to default window because it was not added to any window: " + slot); // TODO DEBUG
		viewFactory.getDefaultView().getButtonHandler().addButton(slot, button);
	}
	
	public void addWindow(WindowView window) {
		Bukkit.getLogger().info("Window added: " + window.getSlots()); // TODO DEBUG
		this.viewFactory.addWindow(window);
	}
	
	/**
	 * Updates all windows and itemstacks in inventory.<br>
	 * <br>
	 * This method may throw {@link IllegalStateException} if you call it before you call {@link #open(Player)}.
	 */
	public void update() {
		if (this.inventory == null)
			throw new IllegalStateException("Trying to update an inventory before menu was opened!");
		
		for (WindowView window : viewFactory.getWindows()) {
			//Update all windows
			window.update();
			
			//Update itemstacks in inventory
			for (Map.Entry<Integer, MenuButton> map : window.getButtonHandler().getButtons().entrySet()) {
				int slot = map.getKey();
				ItemStack item = map.getValue().getItemStack();
				inventory.setItem(slot, item);
			}
		}
	}
	
	private ItemStack[] getContent() {
		ItemStack[] content = new ItemStack[inventory.getSize()];
		
		Bukkit.getLogger().info("Windows: " + viewFactory.getWindows());
		
		List<WindowView> views = new ArrayList<>(viewFactory.getWindows());
		views.add(viewFactory.getDefaultView());
		
		//For each window get buttons and set them to content which will be returned
		for (WindowView window : views) {
			Bukkit.getLogger().info("Window buttons: " + window.getButtonHandler().getButtons()); //TODO DEBUG
			Bukkit.getLogger().info("Window slots: " + window.getSlots());
			for (Map.Entry<Integer, MenuButton> map : window.getButtonHandler().getButtons().entrySet()) {
				int slot = map.getKey();
				ItemStack item = map.getValue().getItemStack();
				if (slot <= content.length)
					content[slot] = item;
			}
		}
		Bukkit.getLogger().info("Content: " + Arrays.toString(content)); // TODO DEBUG
		return content;
	}
	
	/**
	 * This method will remove metadata for a {@link org.bukkit.entity.Player} and cancel all scheduled tasks.
	 */
	@ApiStatus.Internal
	public void handleClose() {
		//todo actions on close
		
		Bukkit.getLogger().info("handleClose"); // TODO DEBUG
		Objects.requireNonNull(player).removeMetadata(METADATA_VALUE, ArmCore.INSTANCE.get().getJavaPlugin());
		
		scheduledTasks.forEach(BukkitTask::cancel);
		this.options.onClose();
	}
	
	/**
	 * This method will set metadata for a {@link org.bukkit.entity.Player} to stop duplication bugs,
	 * creates {@link org.bukkit.inventory.Inventory}, sets {@link sk.adr3ez.armcore.menu.button.MenuButton}s
	 * and finally opens a menu to a {@link org.bukkit.entity.Player}.
	 *
	 * @param player {@link org.bukkit.entity.Player} for which will be menu opened
	 */
	public final void open(Player player) {
		this.player = player;
		
		//Save metadata
		player.setMetadata(METADATA_VALUE, new FixedMetadataValue(ArmCore.INSTANCE.get().getJavaPlugin(), Menu.this));
		
		this.inventory = createInventory();
		
		//
		int[] slots = new int[inventory.getSize()];
		for (int i = 0; i < slots.length; i++)
			slots[i] = i;
		for (WindowView window : viewFactory.getWindows()) {
			window.getSlots().forEach(slot -> slots[slot] = -1);
		}
		ArrayList<Integer> freeSlots = new ArrayList<>();
		for (int i = 0; i < slots.length; i++) {
			if (slots[i] != -1)
				freeSlots.add(i); //Add all non-occupied slots to free slots which will be used to fill default window
		}
		viewFactory.getDefaultView().setSlots(freeSlots);
		
		inventory.setContents(getContent());
		player.openInventory(inventory);
		
		//todo PostDisplayConsumer???
	}
	
}
