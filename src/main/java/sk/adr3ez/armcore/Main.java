package sk.adr3ez.armcore;

import org.bukkit.plugin.java.JavaPlugin;
import sk.adr3ez.armcore.api.ArmCore;

public class Main extends JavaPlugin implements ArmCore {

    @Override
    public void onLoad() {
        ArmCore.INSTANCE.set(this);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }
}
