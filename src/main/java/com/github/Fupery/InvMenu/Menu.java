package com.github.Fupery.InvMenu;

import com.github.Fupery.InvMenu.API.Event.PluginUnloadListener;
import com.github.Fupery.InvMenu.API.Handler.MenuHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Iterator;

public final class Menu {
    private static Menu menu = null;
    private HashMap<String, MenuHandler> handlers;
    private PluginUnloadListener unloadListener;

    private Menu(Plugin plugin) {
        this.handlers = new HashMap<>();
        unloadListener = new PluginUnloadListener(this);
        unloadListener.register(plugin);
    }

    /**
     * @param plugin The plugin using this menu.
     * @return The Menu Handler associated with the plugin provided.
     * @see MenuHandler
     */
    public static MenuHandler getMenuHandler(Plugin plugin) {
        Menu menu = getMenu(plugin);
        if (menu.handlers.containsKey(plugin.getName())) {
            return menu.handlers.get(plugin.getName());
        }
        MenuHandler handler = new AccessibleMenuHandler(plugin);
        menu.handlers.put(plugin.getName(), handler);
        return handler;
    }

    private static Menu getMenu(Plugin plugin) {
        if (Menu.menu != null) return Menu.menu;
        return menu = new Menu(plugin);
    }

    private static void setMenu(Menu menu) {
        Menu.menu = menu;
    }

    public void deregisterPlugin(Plugin plugin) {
        String pluginName = plugin.getName();
        if (!handlers.containsKey(pluginName)) return;
        MenuHandler handler = handlers.get(pluginName);
        handler.closeAll();
        handlers.remove(pluginName);
        if (pluginName.equals(unloadListener.getHandlingPlugin())) {
            unloadListener.deregister();
            Iterator<String> i = handlers.keySet().iterator();
            if (i.hasNext()) {
                unloadListener.register(Bukkit.getPluginManager().getPlugin(i.next()));
            } else {
                setMenu(null);
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        handlers.clear();
        unloadListener.deregister();
        super.finalize();
    }

    private static class AccessibleMenuHandler extends MenuHandler {

        private AccessibleMenuHandler(Plugin plugin) {
            super(plugin);
        }
    }
}
