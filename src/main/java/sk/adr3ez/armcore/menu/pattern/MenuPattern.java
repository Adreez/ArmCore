package sk.adr3ez.armcore.menu.pattern;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import sk.adr3ez.armcore.menu.view.WindowView;

import java.util.*;

/**
 * With {@code MenuPattern} you can easily create a whole menu/gui just by using characters.
 */
public class MenuPattern {
	
	private final HashMap<Integer, Character> elements = new HashMap<>();
	private final HashMap<Character, List<Integer>> charSlots = new HashMap<>();
	private final Map<Character, WindowView> charToView = new HashMap<>();
	private List<String> rawLines = new ArrayList<>();
	
	public MenuPattern() {
	}
	
	public MenuPattern(String... lines) {
		for (String line : lines) {
			this.line(line);
		}
	}
	
	/**
	 * Add line to the menu
	 * @param line to be added
	 * @return MenuPattern instance
	 */
	public MenuPattern line(String line) {
		rawLines.add(line);
		return this;
	}
	
	/**
	 * Set line in the menu
	 * @param row      to be set
	 * @param pattern  to be set
	 * @return MenuPattern instance
	 */
	public MenuPattern line(int row, String pattern) {
		rawLines.set(row, pattern);
		return this;
	}
	
	@ApiStatus.Internal
	public void init() throws IllegalStateException {
		if (rawLines.isEmpty())
			throw new IllegalStateException("No lines were added to the pattern");
		// Initialize menu instance logic here
		int ingredientInt = 0;
		for (String line : rawLines) {
			char[] array = line.replaceAll(" ", "").toCharArray(); // Skip spaces, convert to array
			for (char element : array) {
				elements.put(ingredientInt, element);
				ingredientInt++;
			}
		}
		//Fill if empty
		if (elements.size() < 9) {
			for (int i = elements.size(); i < 9; i++) {
				elements.put(i, '#');
			}
			Bukkit.getLogger().warning("GUI does not contain enough elements. Pattern will be automatically filled with default character ('#').");
		}
		//Remove if too many
		if (elements.size() > 63) {
			for (int i = elements.size(); i > 63; i--) {
				elements.remove(i);
			}
			Bukkit.getLogger().warning("Max slots in GUI have been reached, only the first 63 characters will be used");
		}
		
		//Set slots for each character
		for (Map.Entry<Integer, Character> map : elements.entrySet()) {
			char id = map.getValue();
			Integer slot = map.getKey();
			Bukkit.getLogger().info("Slot: " + slot + " has character: " + id); // TODO DEBUG
			charSlots.computeIfAbsent(id, k -> new ArrayList<>()).add(slot);
		}
		Bukkit.getLogger().info("MenuPattern initialized"); // TODO DEBUG
	}
	
	/**
	 * @return Number of rows in the menu
	 */
	public Integer getRows() {
		return elements.size()/9;
	}
	
	/**
	 * Get slots for given character
	 * @param c Character
	 * @return List of slots for given character
	 */
	private List<Integer> getSlotsOfChar(char c) {
		return charSlots.get(c);
	}
	
	/**
	 * @return Collection of all window views
	 */
	public Collection<WindowView> getWindows() {
		return charToView.values();
	}
	
	/**
	 * This method initializes all characters in the menu with the given view.
	 * @param character The character representing menu entries.
	 * @param windowView The window view to set for the character.
	 * @return MenuPattern instance.
	 */
	public MenuPattern charOf(char character, WindowView windowView) {
		List<Integer> slots = getSlotsOfChar(character);
		Bukkit.getLogger().info("Char: " + character + " has these slots: " + slots); // TODO DEBUG
		charToView.put(character, windowView);
		return this;
	}
	
	@Nullable
	public WindowView getWindow(char character) {
		return charToView.get(character);
	}
	
}