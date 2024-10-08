package sk.adr3ez.armcore.utils;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;
import sk.adr3ez.armcore.api.util.PluginLogger;

public class BukkitPluginLogger implements PluginLogger {
	@Override
	public void info(@Nullable String s) {
		Bukkit.getLogger().info(s);
	}
	
	@Override
	public void warn(@Nullable String s) {
		Bukkit.getLogger().warning(s);
	}
	
	@Override
	public void severe(@Nullable String s) {
		Bukkit.getLogger().severe(s);
	}
}
