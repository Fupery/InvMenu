package com.github.Fupery.InvMenu.API.Templates;

import com.github.Fupery.InvMenu.API.Event.MenuCloseReason;
import com.github.Fupery.InvMenu.API.Handler.CacheableMenu;
import org.bukkit.entity.Player;

/**
 * Implement this interface to give your menu child menu behaviour:
 * if it is closed with {@link MenuCloseReason#BACK}, its parent will automatically be opened.
 */
public interface ChildMenu extends MenuTemplate {
    CacheableMenu getParent(Player viewer);
}
