package me.Fupery.InventoryMenu;

import me.Fupery.InventoryMenu.API.GenericMenuListener;
import me.Fupery.InventoryMenu.API.InventoryMenu;
import me.Fupery.InventoryMenu.API.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Menu extends InventoryMenu {

    MenuListener listener;

    public Menu(JavaPlugin plugin, String title, InventoryType type) {
        super(null, title, type);
        listener = new GenericMenuListener(plugin);
        registerListener(plugin);
    }

    @Override
    public InventoryMenu subMenu(String title, InventoryType type) {
        return super.subMenu(title, type);
    }

    @Override
    public ConcurrentHashMap<UUID, InventoryMenu> getOpenMenus() {
        return listener.getOpenMenus();
    }

    protected void registerListener(JavaPlugin plugin) {
        if (!Bukkit.getServicesManager().isProvidedFor(MenuListener.class)) {
            Bukkit.getServicesManager().register(MenuListener.class, listener, plugin, ServicePriority.Normal);
        }
    }
}
