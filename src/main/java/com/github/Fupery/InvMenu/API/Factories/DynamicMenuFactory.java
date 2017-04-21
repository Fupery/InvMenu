package com.github.Fupery.InvMenu.API.Factories;

import com.github.Fupery.InvMenu.API.Event.MenuFactory;
import com.github.Fupery.InvMenu.API.Handler.CacheableMenu;
import org.bukkit.entity.Player;

public class DynamicMenuFactory implements MenuFactory {
    private MenuGenerator generator;

    public DynamicMenuFactory(MenuGenerator generator) {
        this.generator = generator;
    }

    @Override
    public CacheableMenu get(Player viewer) {
        return generator.get(viewer);
    }

    public interface MenuGenerator {
        CacheableMenu get(Player viewer);
    }
}
