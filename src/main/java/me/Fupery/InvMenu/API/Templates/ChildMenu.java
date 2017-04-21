package me.Fupery.InvMenu.API.Templates;

import me.Fupery.InvMenu.API.Handler.CacheableMenu;
import org.bukkit.entity.Player;

public interface ChildMenu extends MenuTemplate {
    CacheableMenu getParent(Player viewer);
}
