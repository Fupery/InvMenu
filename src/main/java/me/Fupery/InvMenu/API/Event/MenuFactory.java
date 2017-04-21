package me.Fupery.InvMenu.API.Event;

import me.Fupery.InvMenu.API.Handler.CacheableMenu;
import org.bukkit.entity.Player;

public interface MenuFactory {
    CacheableMenu get(Player viewer);
}
