package sk.adr3ez.armcore.menu.button;

import lombok.Getter;
import org.bukkit.Bukkit;
import sk.adr3ez.armcore.menu.view.WindowView;

import java.util.HashMap;

/**
 * Each {@link sk.adr3ez.armcore.menu.view.WindowView} has its own button handler.
 */
public class ButtonHandler {
	
	@Getter
	private final HashMap<Integer, MenuButton> buttons = new HashMap<>();
	private final WindowView window;
	
	public ButtonHandler(WindowView windowView) {
		Bukkit.getLogger().info("ButtonHandler initialized"); // TODO DEBUG
		this.window = windowView;
	}
	
	public void addButton(int slot, MenuButton button) {
		this.buttons.put(slot, button);
	}
	
}
