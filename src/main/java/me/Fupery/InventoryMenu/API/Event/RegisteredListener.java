package me.Fupery.InventoryMenu.API.Event;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public interface RegisteredListener extends Listener {
    void register(Plugin plugin);

    void deregister();
}
