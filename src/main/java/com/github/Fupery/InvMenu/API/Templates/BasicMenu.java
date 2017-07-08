package com.github.Fupery.InvMenu.API.Templates;

import com.github.Fupery.InvMenu.API.Event.MenuCloseReason;
import com.github.Fupery.InvMenu.API.Handler.CacheableMenu;
import com.github.Fupery.InvMenu.API.Handler.MenuHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;

/**
 * A basic inventory menu implementation.
 * Use {@link MenuHandler#openMenu(Player, CacheableMenu)}
 * to open.
 */
public abstract class BasicMenu extends CacheableMenu {

    protected BasicMenu(MenuHandler handler, String heading, InventoryType type) {
        super(handler, heading, type);
    }

    protected BasicMenu(MenuHandler handler, String heading, int size) {
        super(handler, heading, size);
    }

    @Override
    public void onMenuOpenEvent(Player viewer) {
    }

    @Override
    public void onMenuRefreshEvent(Player viewer) {
    }

    @Override
    public void onMenuClickEvent(Player viewer, int slot, ClickType click) {
    }

    @Override
    public void onMenuCloseEvent(Player viewer, MenuCloseReason reason) {
    }
}
