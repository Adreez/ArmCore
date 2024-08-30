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
import sk.adr3ez.armcore.menu.view.WindowView;

import java.util.*;

public abstract class Menu {

    private static final String METADATA_VALUE = "ArmCoreMenu";
	@Getter
	private final OptionsService options = new OptionsService(this);
	
    private final Set<BukkitTask> scheduledTasks = new HashSet<>();
	private final Collection<WindowView> windows = new ArrayList<>();
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
        if (this.player == null)
            throw new IllegalStateException("Trying to get an inventory before menu was opened!");
        return this.player.getOpenInventory().getTopInventory();
    }

    public Inventory getBottomInventory() {
        if (this.player == null)
            throw new IllegalStateException("Trying to get an inventory before menu was opened!");
        //TODO Check if menuOption to use bottom inv is there
        return this.player.getOpenInventory().getBottomInventory();
    }
	
	public void performClick(Player player, InventoryClickEvent event) {
		
		int slot = event.getSlot();
		for (WindowView window : windows) {
			if (window.getSlots().contains(slot)) {
				MenuButton.ClickAction action = window.getButton(slot).getClickAction();
				if (action != null)
					action.click(player, event, this);
				break;
			}
		}
	}
	
	public void addWindow(WindowView window) {
		Bukkit.getLogger().info("Window added: " + window.getSlots()); // TODO DEBUG
		windows.add(window);
	}
	
	public void update() {
		for (WindowView window : windows) {
			window.update();
		}
	}

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

        //todo Register buttons
        Inventory inv = createInventory();
		
		this.update();
		
	    ItemStack[] content = new ItemStack[inv.getSize()];
	    
		Bukkit.getLogger().info("Windows: " + windows);
	    for (WindowView window : windows) {
			Bukkit.getLogger().info("Window buttons: " + window.getButtonHandler().getButtons()); //TODO DEBUG
			Bukkit.getLogger().info("Window slots: " + window.getSlots());
		    for (Map.Entry<Integer, MenuButton> map : window.getButtonHandler().getButtons().entrySet()) {
				int slot = map.getKey();
				ItemStack item = map.getValue().getItemStack();
			    if (slot <= content.length)
				    content[slot] = item;
		    }
	    }
		Bukkit.getLogger().info("Content: " + Arrays.toString(content));
		inv.setContents(content);
        player.openInventory(inv);

        //todo PostDisplayConsumer???
    }

}
