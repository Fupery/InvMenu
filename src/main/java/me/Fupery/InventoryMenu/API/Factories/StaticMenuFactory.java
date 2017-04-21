package me.Fupery.InventoryMenu.API.Factories;

import me.Fupery.InventoryMenu.API.Event.MenuFactory;
import me.Fupery.InventoryMenu.API.Handler.CacheableMenu;
import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;

public class StaticMenuFactory implements MenuFactory {

    private MenuGenerator generator;
    private WeakReference<CacheableMenu> menuWeakReference = null;

    public StaticMenuFactory(MenuGenerator generator) {
        this.generator = generator;
    }

    @Override
    public CacheableMenu get(Player viewer) {
        if (menuWeakReference == null || menuWeakReference.get() == null) {
            menuWeakReference = new WeakReference<>(generator.get());
        }
        return menuWeakReference.get();
    }

    public interface MenuGenerator {
        CacheableMenu get();
    }
}