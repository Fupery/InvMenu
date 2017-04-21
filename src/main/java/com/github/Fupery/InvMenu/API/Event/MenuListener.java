package com.github.Fupery.InvMenu.API.Event;

import com.github.Fupery.InvMenu.API.Handler.MenuHandler;
import com.github.Fupery.InvMenu.Utils.VersionHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import static com.github.Fupery.InvMenu.API.Event.MenuEvent.MenuCloseEvent;
import static com.github.Fupery.InvMenu.API.Event.MenuEvent.MenuInteractEvent;

public class MenuListener implements RegisteredListener {
    private final MenuHandler handler;
    private final RegisteredListener listener;

    public MenuListener(MenuHandler handler) {
        this.handler = handler;
        listener = (VersionHandler.getVersion() == VersionHandler.v1_8) ? new v1_8() : new v1_9();
    }

    private void fireMenuEvent(MenuEvent event) {
        Player player = event.getPlayer();
        if (event instanceof MenuInteractEvent) {
            MenuInteractEvent interactEvent = ((MenuInteractEvent) event);
            handler.fireClickEvent(player, interactEvent.getSlot(), interactEvent.getClick());
        } else if (event instanceof MenuCloseEvent) {
            MenuCloseReason reason = ((MenuCloseEvent) event).getReason();
            handler.closeMenu(player, reason);
        }
    }

    private void handleClick(InventoryClickEvent event) {
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

    @Override
    public void register(Plugin plugin) {
        listener.register(plugin);
    }

    @Override
    public void deregister() {
        listener.deregister();
    }

    private class v1_8 implements RegisteredListener {

        @EventHandler
        public void onMenuInteract(final InventoryClickEvent event) {
            if (!handler.isTrackingPlayer((Player) event.getWhoClicked())) {
                return;
            }

            handleClick(event);

            final Player player = (Player) event.getWhoClicked();

            if (event.getClickedInventory() != player.getOpenInventory().getTopInventory()) {
                return;
            }

            fireMenuEvent(new MenuInteractEvent(player, event.getInventory(), event.getSlot(), event.getClick()));
        }

        @EventHandler
        public void onItemDrag(InventoryDragEvent event) {

            if (!handler.isTrackingPlayer((Player) event.getWhoClicked())) {
                return;
            }
            event.setResult(Event.Result.DENY);
            event.setCancelled(true);
        }

        @EventHandler
        public void onMenuClose(InventoryCloseEvent event) {
            Player player = ((Player) event.getPlayer());

            if (player != null && handler.isTrackingPlayer(player)) {
                fireMenuEvent(new MenuCloseEvent(player, event.getInventory(), MenuCloseReason.CLIENT));
            }
        }

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent event) {
            if (handler.isTrackingPlayer(event.getPlayer())) {
                fireMenuEvent(new MenuCloseEvent(event.getPlayer(), MenuCloseReason.QUIT));
            }
        }

        @EventHandler
        public void onPlayerDeath(PlayerDeathEvent event) {
            if (handler.isTrackingPlayer(event.getEntity())) {
                fireMenuEvent(new MenuCloseEvent(event.getEntity(), MenuCloseReason.DEATH));
            }
        }

        @Override
        public void register(Plugin plugin) {
            Bukkit.getPluginManager().registerEvents(this, plugin);
        }

        @Override
        public void deregister() {
            InventoryCloseEvent.getHandlerList().unregister(this);
            InventoryDragEvent.getHandlerList().unregister(this);
            InventoryCloseEvent.getHandlerList().unregister(this);
            PlayerQuitEvent.getHandlerList().unregister(this);
            PlayerDeathEvent.getHandlerList().unregister(this);
        }
    }

    private class v1_9 extends v1_8 {
        @EventHandler
        public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
            if (handler.isTrackingPlayer(event.getPlayer())) {
                event.setCancelled(true);
            }
        }

        @Override
        public void deregister() {
            super.deregister();
            PlayerSwapHandItemsEvent.getHandlerList().unregister(this);
        }
    }
}
