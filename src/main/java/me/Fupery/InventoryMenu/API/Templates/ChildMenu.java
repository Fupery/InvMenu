package me.Fupery.InventoryMenu.API.Templates;

import me.Fupery.InventoryMenu.API.Handler.CacheableMenu;
import org.bukkit.entity.Player;

public interface ChildMenu extends MenuTemplate {
    CacheableMenu getParent(Player viewer);
}
