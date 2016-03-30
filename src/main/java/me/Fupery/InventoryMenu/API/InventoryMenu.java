package me.Fupery.InventoryMenu.API;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;

public class InventoryMenu {

    protected final InventoryType type;
    protected final int inventorySize;
    protected final InventoryMenu parent;
    protected String title;
    protected MenuButton[] buttons;

    protected InventoryMenu(InventoryMenu parent, String title, InventoryType type) {
        this.type = type;
        this.title = title;
        this.parent = parent;
        inventorySize = 0;
        this.buttons = new MenuButton[type.getDefaultSize()];
    }

    protected InventoryMenu(InventoryMenu parent, String title, int inventorySize) {
        this.type = InventoryType.CHEST;
        this.title = title;
        this.parent = parent;
        this.inventorySize = inventorySize;
        this.buttons = new MenuButton[inventorySize];
    }

    public void addButton(int slot, MenuButton button) {
        buttons[slot] = button;
    }

    public void addButtons(MenuButton... buttons) {

        if (buttons != null && buttons.length > 0) {
            System.arraycopy(buttons, 0, this.buttons, 0, buttons.length);
        }
    }

    void clearButtons() {
        buttons = new MenuButton[type.getDefaultSize()];
    }

    protected void updateInventory(JavaPlugin plugin, final Player player) {
        Bukkit.getScheduler().runTask(plugin, new Runnable() {
            @Override
            public void run() {
                Inventory inventory = player.getOpenInventory().getTopInventory();

                for (int slot = 0; slot < inventory.getSize(); slot++) {

                    if (getButton(slot) != null) {
                        inventory.setItem(slot, getButton(slot));

                    } else {
                        inventory.setItem(slot, new ItemStack(Material.AIR));
                    }
                }
                player.updateInventory();
            }
        });
    }

    public void clickButton(JavaPlugin plugin, int slot, Player player) {
        MenuButton button = getButton(slot);

        if (button != null) {
            button.onClick(plugin, player);
        }
    }

    public MenuButton getButton(int slot) {
        return buttons[slot];
    }

    public void open(JavaPlugin plugin, final Player player) {
        final InventoryMenu menu = this;
        Bukkit.getScheduler().runTask(plugin, new Runnable() {
            @Override
            public void run() {

                if (getOpenMenus().containsKey(player.getUniqueId())) {
                    getOpenMenus().get(player.getUniqueId()).close(player);
                }

                Inventory inventory = Bukkit.createInventory(player, type, title);

                for (int slot = 0; slot < inventory.getSize(); slot++) {

                    if (getButton(slot) != null) {
                        inventory.setItem(slot, getButton(slot));
                    }
                }
                getOpenMenus().put(player.getUniqueId(), menu);
                player.openInventory(inventory);
            }
        });

    }

    public void close(Player player) {
        getOpenMenus().remove(player.getUniqueId());
        player.closeInventory();
    }

    protected Map<UUID, InventoryMenu> getOpenMenus() {
        return parent.getOpenMenus();
    }

    public String getTitle() {
        return title;
    }

    public InventoryType getType() {
        return type;
    }

    protected InventoryMenu subMenu(String title, InventoryType type) {
        return new InventoryMenu(this, title, type);
    }
}

