package com.github.Fupery.InvMenu.API.Handler;

import com.github.Fupery.InvMenu.API.Button.Button;
import com.github.Fupery.InvMenu.API.Event.MenuCloseReason;
import com.github.Fupery.InvMenu.API.Event.MenuListener;
import com.github.Fupery.InvMenu.API.Templates.BasicMenu;
import com.github.Fupery.InvMenu.API.Templates.ChildMenu;
import com.github.Fupery.InvMenu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.plugin.Plugin;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This handler manages all the menus for your plugin.
 * Each plugin only has one MenuHandler, you can get it using
 * {@link Menu#getMenuHandler(Plugin)};
 *
 * @see BasicMenu
 * @see Button
 */
public class MenuHandler {
    private final ConcurrentHashMap<UUID, CacheableMenu> openMenus = new ConcurrentHashMap<>();
    private MenuListener listener;

    /**
     * Use {@link Menu#getMenuHandler} to get a MenuHandler instance
     *
     * @param plugin
     */
    protected MenuHandler(Plugin plugin) {
        listener = new MenuListener(this);
        listener.register(plugin);
    }

    private CacheableMenu getMenu(Player viewer) {
        return openMenus.get(viewer.getUniqueId());
    }

    /**
     * @param player
     * @return True if the player has an open menu, false if they do not.
     */
    public boolean isTrackingPlayer(Player player) {
        return openMenus.containsKey(player.getUniqueId());
    }

    /**
     * Opens the menu provided for a player.
     *
     * @param viewer The player that will see this menu.
     * @param menu   The menu the player will see.
     */
    public void openMenu(Player viewer, CacheableMenu menu) {
        if (openMenus.containsKey(viewer.getUniqueId())) closeMenu(viewer, MenuCloseReason.SWITCH);
        else viewer.closeInventory();
        openMenus.put(viewer.getUniqueId(), menu);
        menu.open(viewer);
    }

    /**
     * Click the menu in the slot provided.
     *
     * @param viewer    The player who clicked the button.
     * @param slot      The slot of the button that was clicked.
     * @param clickType The type of click.
     * @see ClickType
     */
    public void fireClickEvent(Player viewer, int slot, ClickType clickType) {
        if (!openMenus.containsKey(viewer.getUniqueId()) || viewer.getOpenInventory() == null) return;
        getMenu(viewer).click(viewer, slot, clickType);
    }

    /**
     * Refreshes a player's menu, updating all the buttons and title.
     *
     * @param viewer
     */
    public void refreshMenu(Player viewer) {
        if (!openMenus.containsKey(viewer.getUniqueId()) || viewer.getOpenInventory() == null) return;
        getMenu(viewer).refresh(viewer);
    }

    /**
     * Closes the menu the player provided is currently viewing.
     *
     * @param viewer
     * @param reason The reason why this menu is being closed.
     *               This may affect the menu's behaviour when it closes.
     * @see com.github.Fupery.InvMenu.API.Event.MenuCloseReason
     */
    public void closeMenu(Player viewer, MenuCloseReason reason) {
        if (!openMenus.containsKey(viewer.getUniqueId())) return;
        CacheableMenu menu = getMenu(viewer);
        openMenus.remove(viewer.getUniqueId());
        menu.close(viewer, reason);
        if (menu instanceof ChildMenu && reason == MenuCloseReason.BACK) {
            openMenu(viewer, ((ChildMenu) menu).getParent(viewer));
        }
    }

    @Override
    protected void finalize() throws Throwable {
        closeAll();
        super.finalize();
    }

    /**
     * Closes all active menus, clears the active menu list, and unregisters events.
     */
    public void closeAll() {
        listener.deregister();
        for (UUID uuid : openMenus.keySet()) closeMenu(Bukkit.getPlayer(uuid), MenuCloseReason.SYSTEM);
        openMenus.clear();
    }
}
