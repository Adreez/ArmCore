package sk.adr3ez.armcore;

import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import sk.adr3ez.armcore.api.ArmCore;
import sk.adr3ez.armcore.api.util.PluginLogger;
import sk.adr3ez.armcore.menu.util.MenuListener;
import sk.adr3ez.armcore.utils.BukkitPluginLogger;

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
	
	@Override
	public @NotNull String getDataDirectory() {
		return this.getDataFolder().getPath();
	}
	
	@Override
	public YamlDocument getConfigFile() {
		return null;
	}
	
	@Override
	public PluginLogger getPluginLogger() {
		return new BukkitPluginLogger();
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		new TestMenu().open(event.getPlayer());
	}
}
