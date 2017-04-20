package me.Fupery.InventoryMenu.API.Templates;

import me.Fupery.InventoryMenu.API.Event.MenuCloseReason;
import me.Fupery.InventoryMenu.API.Handler.CacheableMenu;
import me.Fupery.InventoryMenu.API.Handler.MenuHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;

public abstract class BasicMenu extends CacheableMenu {

    protected BasicMenu(MenuHandler handler, String heading, InventoryType type) {
        super(handler, heading, type);
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
