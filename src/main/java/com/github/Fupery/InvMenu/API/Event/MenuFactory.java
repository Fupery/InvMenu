package com.github.Fupery.InvMenu.API.Event;

import com.github.Fupery.InvMenu.API.Handler.CacheableMenu;
import org.bukkit.entity.Player;

public interface MenuFactory {
    CacheableMenu get(Player viewer);
}
