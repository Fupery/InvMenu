package com.github.Fupery.InvMenu.API.Event;

import com.github.Fupery.InvMenu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

public class PluginUnloadListener implements RegisteredListener {

    private final Menu menu;
    private String handlingPlugin;

    public PluginUnloadListener(Menu menu) {
        this.menu = menu;
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        menu.deregisterPlugin(event.getPlugin());
    }

    @Override
    public void register(Plugin plugin) {
        handlingPlugin = plugin.getName();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void deregister() {
        handlingPlugin = null;
        PluginDisableEvent.getHandlerList().unregister(this);
    }

    public String getHandlingPlugin() {
        return handlingPlugin;
    }
}
