package com.github.Fupery.InvMenu.API.Templates;

import com.github.Fupery.InvMenu.API.Button.Button;
import com.github.Fupery.InvMenu.API.Event.MenuCloseReason;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public interface MenuTemplate {

    /**
     * Called after the menu has been sent to the player
     *
     * @param viewer The player viewing the menu
     */
    void onMenuOpenEvent(Player viewer);

    /**
     * Called when the menu is updated
     *
     * @param viewer The player viewing the menu
     */
    void onMenuRefreshEvent(Player viewer);

    /**
     * Called when the player clicks the menu
     *
     * @param viewer The player viewing the menu
     */
    void onMenuClickEvent(Player viewer, int slot, ClickType click);

    /**
     * Called after the player closes the menu
     *
     * @param viewer The player viewing the menu
     */
    void onMenuCloseEvent(Player viewer, MenuCloseReason reason);

    /**
     * @return A list of itemstack buttons that fill the menu
     */
    Button[] getButtons();
}
