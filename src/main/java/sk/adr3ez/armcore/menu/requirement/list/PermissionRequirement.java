package sk.adr3ez.armcore.menu.requirement.list;

import org.bukkit.entity.Player;
import sk.adr3ez.armcore.menu.requirement.MenuRequirement;

public final class PermissionRequirement implements MenuRequirement<String> {
    @Override
    public boolean isMet(Player player, String object) {
        return player.hasPermission(object);
    }
}
