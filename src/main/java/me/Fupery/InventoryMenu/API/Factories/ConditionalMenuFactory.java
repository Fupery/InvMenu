package me.Fupery.InventoryMenu.API.Factories;

import me.Fupery.InventoryMenu.API.Event.MenuFactory;
import me.Fupery.InventoryMenu.API.Handler.CacheableMenu;
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
