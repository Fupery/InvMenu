package me.Fupery.InventoryMenu.API;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GenericMenuListener implements MenuListener {

    public final ConcurrentHashMap<UUID, InventoryMenu> openMenus = new ConcurrentHashMap<>();
    private JavaPlugin plugin;

    public GenericMenuListener(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    protected void handleClick(InventoryClickEvent event) {
        Inventory top = event.getWhoClicked().getOpenInventory().getTopInventory();
        Inventory bottom = event.getWhoClicked().getOpenInventory().getBottomInventory();

        if (event.getClickedInventory() == top) {
            event.setResult(Event.Result.DENY);
            event.setCancelled(true);

        } else if (event.getClickedInventory() == bottom) {

            switch (event.getAction()) {
                case MOVE_TO_OTHER_INVENTORY:
                case HOTBAR_MOVE_AND_READD:
                case COLLECT_TO_CURSOR:
                case UNKNOWN:
                    event.setResult(Event.Result.DENY);
                    event.setCancelled(true);
                    return;
                default:
                    break;
            }
        }
    }

    @EventHandler
    public void onMenuInteract(final InventoryClickEvent event) {

        if (!openMenus.containsKey(event.getWhoClicked().getUniqueId())) {
            return;
        }

        handleClick(event);

        final Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory() != player.getOpenInventory().getTopInventory()) {
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                InventoryMenu menu = openMenus.get(player.getUniqueId());
                menu.clickButton(plugin, event.getSlot(), player);
            }
        });
    }

    @EventHandler
    public void onItemDrag(InventoryDragEvent event) {

        if (!openMenus.containsKey(event.getWhoClicked().getUniqueId())) {
            return;
        }
        event.setResult(Event.Result.DENY);
        event.setCancelled(true);
    }

    @EventHandler
    public void onMenuClose(InventoryCloseEvent event) {
        Player player = ((Player) event.getPlayer());

        if (player != null && openMenus.containsKey(player.getUniqueId())) {
            openMenus.get(player.getUniqueId()).close(player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (openMenus.containsKey(event.getPlayer().getUniqueId())) {
            openMenus.remove(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (openMenus.containsKey(event.getEntity().getUniqueId())) {
            openMenus.remove(event.getEntity().getUniqueId());
        }
    }

    @Override
    public ConcurrentHashMap<UUID, InventoryMenu> getOpenMenus() {
        return openMenus;
    }

}
