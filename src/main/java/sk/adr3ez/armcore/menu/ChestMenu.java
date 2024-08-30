package sk.adr3ez.armcore.menu;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import sk.adr3ez.armcore.menu.pattern.MenuPattern;

@Setter
@Getter
public final class ChestMenu extends Menu {

    private int rows = 3;

    public ChestMenu() {
    }

    public ChestMenu(String title) {
        this(title,3);
    }

    public ChestMenu(String title, int rows) {
        this.setRows(rows);
        if (title != null)
            this.setTitle(title);
    }
	
	public ChestMenu(MenuPattern pattern) {
		this.setRows(pattern.getRows());
	}
	
	public ChestMenu withPattern(MenuPattern pattern) {
		pattern.init();
		this.setRows(pattern.getRows());
		pattern.getWindows().forEach(this::addWindow);
		return this;
	}

    @Override
    protected Inventory createInventory() {
	    return Bukkit.createInventory(null, rows*9, this.getTitle());
    }

}
