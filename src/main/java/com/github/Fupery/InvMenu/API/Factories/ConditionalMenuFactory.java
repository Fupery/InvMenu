package com.github.Fupery.InvMenu.API.Factories;

import com.github.Fupery.InvMenu.API.Event.MenuFactory;
import com.github.Fupery.InvMenu.API.Handler.CacheableMenu;
import org.bukkit.entity.Player;

public class ConditionalMenuFactory implements MenuFactory {
    private StaticMenuFactory conditionTrue;
    private StaticMenuFactory conditionFalse;
    private ConditionalGenerator generator;

    public ConditionalMenuFactory(ConditionalGenerator generator) {
        this.generator = generator;
        conditionTrue = new StaticMenuFactory(generator::getConditionTrue);
        conditionFalse = new StaticMenuFactory(generator::getConditionFalse);
    }

    @Override
    public CacheableMenu get(Player viewer) {
        return generator.evaluateCondition(viewer) ? conditionTrue.get(viewer) : conditionFalse.get(viewer);
    }

    public interface ConditionalGenerator {
        CacheableMenu getConditionTrue();

        CacheableMenu getConditionFalse();

        boolean evaluateCondition(Player viewer);
    }
}
