package sk.adr3ez.armcore.menu.view;

import lombok.Getter;
import sk.adr3ez.armcore.menu.button.MenuButton;
import sk.adr3ez.armcore.menu.util.Range;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListedWindowView<T extends MenuButton> extends WindowView {


    private Collection<T> items = new ArrayList<>();
    @Getter
    private Integer currentPage = 1;


    public ListedWindowView(Integer... slots) {
        super(slots);
    }

    public ListedWindowView(Range range) {
        super(range);
    }


    @SafeVarargs
    public final void addConents(T... contents) {
        items.addAll(List.of(contents));
    }

    public Collection<T> contents() {
        return this.items;
    }

    public void next() {
        currentPage++;
        loadPage();
    }

    public void lastPage() {
        currentPage = maxPages();
        loadPage();
    }

    public void firstPage() {
        currentPage = 1;
        loadPage();
    }

    public void previous() {
        currentPage--;
        loadPage();
    }

    private void loadPage() {
        //TODO page logic
    }

    public Integer maxPages() {
        return this.items.size()/getSlots().size();
    }


}
