package sk.adr3ez.armcore;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import sk.adr3ez.armcore.api.ArmCore;
import sk.adr3ez.armcore.menu.ChestMenu;
import sk.adr3ez.armcore.menu.util.MenuListener;
import sk.adr3ez.armcore.menu.util.Range;
import sk.adr3ez.armcore.menu.view.WindowView;

public class SpigotMain extends JavaPlugin implements ArmCore, Listener {
	
	@Override
	public void onLoad() {
		ArmCore.INSTANCE.set(this);
	}
	
	@Override
	public void onEnable() {
		
		Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
		Bukkit.getPluginManager().registerEvents(this, this);
		
	}
	
	@Override
	public void onDisable() {
	
	}
	
	@Override
	public JavaPlugin getJavaPlugin() {
		return this;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		/*ChestMenu menu = new ChestMenu("test title")
				.withPattern(new MenuPattern()
						.line("# # # # # # # # #")
						.line("# A # # # # # A #")
						.line("# # # # # # # # #")
						.charOf('#', new WindowView().fill(Material.BLACK_STAINED_GLASS_PANE))
						.charOf('A', new WindowView()
								.addButton(new MenuButton(new ItemStack(Material.DIAMOND_AXE)))
								.addButton(new MenuButton(new ItemStack(Material.IRON_AXE))))
				);*/
		
		ChestMenu menu = new ChestMenu("test title", 6);
		menu.addWindow(new WindowView(new Range(0,8)).fill(Material.BLACK_STAINED_GLASS_PANE));
		menu.addWindow(new WindowView(new Range(9,10)).fill(Material.YELLOW_STAINED_GLASS_PANE));
		
		event.getPlayer().sendMessage("Yaay opening menu with title:" + menu.getTitle());
		menu.open(event.getPlayer());
	}
}
