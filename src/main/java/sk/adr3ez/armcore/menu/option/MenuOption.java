package sk.adr3ez.armcore.menu.option;

import lombok.AccessLevel;
import lombok.Getter;
import sk.adr3ez.armcore.menu.Menu;

public abstract class MenuOption {

    @Getter(AccessLevel.PROTECTED)
    private final Menu menu;

    public MenuOption(Menu menu) {
        this.menu = menu;
    }

    public abstract void onOpen();

    public abstract void onClose();

}
