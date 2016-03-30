package me.Fupery.InventoryMenu.API;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public interface MenuListener extends Listener{

    @EventHandler
    void onMenuInteract(final InventoryClickEvent event);

    @EventHandler
    void onItemDrag(InventoryDragEvent event);

    @EventHandler
    void onMenuClose(InventoryCloseEvent event);

    ConcurrentHashMap<UUID, InventoryMenu> getOpenMenus();
}
