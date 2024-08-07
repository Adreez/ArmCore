package sk.adr3ez.armcore.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.adr3ez.armcore.api.ArmCore;
import sk.adr3ez.armcore.menu.requirement.MenuRequirement;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Menu {

    private static final String METADATA_VALUE = "ArmCoreMenu";

    private final String title;
    private final int slots;
    private final List<MenuButton> buttons;
    private final Collection<MenuRequirement<?>> menuRequirements;
    private Player player;
    private Set<BukkitTask> schedulerTasks;

    public Menu(@NotNull String title,
                @NotNull Integer rows,
                @Nullable List<MenuButton> buttons,
                @Nullable Set<BukkitTask> schedulerTasks, Collection<MenuRequirement<?>> menuRequirements) {
        this.title = title;
        this.slots = rows * 9;
        this.buttons = buttons;
        this.schedulerTasks = schedulerTasks;
        this.menuRequirements = menuRequirements;
    }

    @Nullable
    public static Menu get(@NotNull Player player) {
        if (player.hasMetadata(METADATA_VALUE))
            return (Menu) player.getMetadata(METADATA_VALUE).get(0).value();
        else
            return null;
    }

    public Inventory getInventory() {
        return this.player.getOpenInventory().getTopInventory();
    }

    @ApiStatus.Internal
    private void handleClose() {
        //todo actions on close

        //TODO Check if inventory can be closed else cancel
        //TODO Call custom event

        player.removeMetadata(METADATA_VALUE, ArmCore.INSTANCE.get().getJavaPlugin());
        player.closeInventory();
        for (BukkitTask task : schedulerTasks) {
            task.cancel();
        }
    }

    public final void open(Player player) {
        this.player = player;

        //Save metadata
        player.setMetadata(METADATA_VALUE, new FixedMetadataValue(ArmCore.INSTANCE.get().getJavaPlugin(), Menu.this));

        //todo Register buttons
        Inventory inv = Bukkit.createInventory(this.player, slots, title);
        //todo setcontents for inventory
        // inv.setContents(List.of(new ItemStack(Material.ACACIA_FENCE)).toArray(new ItemStack[0]));

        player.openInventory(inv);

        //todo PostDisplayConsumer???
    }

}
