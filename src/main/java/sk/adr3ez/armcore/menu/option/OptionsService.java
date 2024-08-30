package sk.adr3ez.armcore.menu.option;

import lombok.Getter;
import sk.adr3ez.armcore.menu.Menu;
import sk.adr3ez.armcore.menu.option.list.BottomInventoryOption;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class OptionsService {
	
	private final Menu menu;
	@Getter
	private final Collection<MenuOption> options = new ArrayList<>();
	
	
	public OptionsService(Menu menu) {
		this.menu = menu;
	}
	
	/**
	 * Adds {@link BottomInventoryOption} to options
	 * @return instance of {@link OptionsService}
	 */
	public OptionsService useBottomInventory() {
		this.addOption(new BottomInventoryOption(menu));
		return this;
	}
	
	public void addOption(MenuOption option) {
		options.add(option);
	}
	
	public void addOption(MenuOption... options) {
		this.options.addAll(List.of(options));
	}
	
	public void removeOption(Class<? extends MenuOption> option) {
		options.removeIf(optionItem -> option.equals(optionItem.getClass()));
	}
	
	public void onClose(){
		options.forEach(MenuOption::onClose);
	}
	
}
