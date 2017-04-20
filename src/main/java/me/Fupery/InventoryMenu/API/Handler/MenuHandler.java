package me.Fupery.InventoryMenu.API.Handler;

import me.Fupery.InventoryMenu.API.Event.MenuCloseReason;
import me.Fupery.InventoryMenu.API.Event.MenuListener;
import me.Fupery.InventoryMenu.API.Templates.ChildMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.plugin.Plugin;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MenuHandler {
    //    public final MenuList MENU = new MenuList();
    private final ConcurrentHashMap<UUID, CacheableMenu> openMenus = new ConcurrentHashMap<>();
    private MenuListener listener;

    protected MenuHandler(Plugin plugin) {
        listener = new MenuListener(this);
        listener.register(plugin);
    }

    private CacheableMenu getMenu(Player viewer) {
        return openMenus.get(viewer.getUniqueId());
    }

    public boolean isTrackingPlayer(Player player) {
        return openMenus.containsKey(player.getUniqueId());
    }

    public void openMenu(Player viewer, CacheableMenu menu) {
        if (openMenus.containsKey(viewer.getUniqueId())) closeMenu(viewer, MenuCloseReason.SWITCH);
        openMenus.put(viewer.getUniqueId(), menu);
        menu.open(viewer);
    }

    public void fireClickEvent(Player viewer, int slot, ClickType clickType) {
        if (!openMenus.containsKey(viewer.getUniqueId()) || viewer.getOpenInventory() == null) return;
        getMenu(viewer).click(viewer, slot, clickType);
    }

    public void refreshMenu(Player viewer) {
        if (!openMenus.containsKey(viewer.getUniqueId()) || viewer.getOpenInventory() == null) return;
        getMenu(viewer).refresh(viewer);
    }

    public void closeMenu(Player viewer, MenuCloseReason reason) {
        if (!openMenus.containsKey(viewer.getUniqueId())) return;
        CacheableMenu menu = getMenu(viewer);
        openMenus.remove(viewer.getUniqueId());
        menu.close(viewer, reason);
        if (menu instanceof ChildMenu && reason == MenuCloseReason.BACK) {
            openMenu(viewer, ((ChildMenu) menu).getParent(viewer));
        }
    }

    @Override
    protected void finalize() throws Throwable {
        closeAll();
        super.finalize();
    }

    public void closeAll() {
        listener.deregister();
        for (UUID uuid : openMenus.keySet()) closeMenu(Bukkit.getPlayer(uuid), MenuCloseReason.SYSTEM);
        openMenus.clear();
    }

//    public static class MenuList {
//        public MenuFactory HELP = new StaticMenuFactory(HelpMenu::new);
//        public MenuFactory DYES = new StaticMenuFactory(DyeMenu::new);
//        public MenuFactory TOOLS = new StaticMenuFactory(ToolMenu::new);
//        public MenuFactory ARTIST = new DynamicMenuFactory(ArtistMenu::new);
//        public MenuFactory RECIPE = new ConditionalMenuFactory(new ConditionalMenuFactory.ConditionalGenerator() {
//            @Override
//            public CacheableMenu getConditionTrue() {
//                return new RecipeMenu(true);
//            }
//
//            @Override
//            public CacheableMenu getConditionFalse() {
//                return new RecipeMenu(false);
//            }
//
//            @Override
//            public boolean evaluateCondition(Player viewer) {
//                return viewer.hasPermission("artmap.admin");
//            }
//        });
//    }
}
