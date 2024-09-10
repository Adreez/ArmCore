package sk.adr3ez.armcore.menu.view.manager;

import lombok.Getter;
import sk.adr3ez.armcore.menu.button.MenuButton;
import sk.adr3ez.armcore.menu.view.DefaultView;
import sk.adr3ez.armcore.menu.view.WindowView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
public class ViewFactory {
	
	private final Collection<WindowView> windows = new ArrayList<>();
	private final List<MenuButton> addButtons = new ArrayList<>();
	
	public ViewFactory() {
		windows.add(new DefaultView());
	}
	
	public void addWindow(WindowView window) {
		if (window.getClass().equals(DefaultView.class))
			throw new IllegalArgumentException("You cannot set DefaultView!");
		windows.add(window);
	}
	
	public void addWindows(Collection<WindowView> windows) {
		for (WindowView window : windows) {
			this.addWindow(window);
		}
	}
	
	public void addWindows(WindowView... windows) {
		this.addWindows(List.of(windows));
	}
	
	public boolean contains(Class<? extends WindowView> window) {
		return windows.stream().anyMatch(windowItem -> window.equals(windowItem.getClass()));
	}
	
	public DefaultView getDefaultView() {
		return (DefaultView) windows.stream().filter(window -> window.getClass().equals(DefaultView.class)).toList().get(0);
	}
	
}
