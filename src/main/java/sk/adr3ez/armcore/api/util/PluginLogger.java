package sk.adr3ez.armcore.api.util;

import org.jetbrains.annotations.Nullable;

public interface PluginLogger {
	
	void info(@Nullable String s);
	
	void warn(@Nullable String s);
	
	void severe(@Nullable String s);
	
}

