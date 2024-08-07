package sk.adr3ez.armcore.menu.requirement;

import org.bukkit.entity.Player;

public interface MenuRequirement<T> {

    boolean isMet(Player player, T object);

}
