package me.Fupery.InventoryMenu.API;

import me.Fupery.InventoryMenu.Utils.SoundCompat;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ListMenu extends InventoryMenu {

    protected static final int maxButtons = 25;
    protected MenuButton[] listItems;

    protected ListMenu(InventoryMenu parent, String title) {
        super(parent, title, InventoryType.CHEST);
    }

    protected MenuButton[] paginateButtons(int page, MenuButton... listItems) {
        MenuButton[] buttons = new MenuButton[maxButtons + 2];

        if (page < 1) {
            buttons[0] = new MenuButton.CloseButton(this);

        } else {
            buttons[0] = new PageButton(this, false);

            if (page > 0) {
                buttons[0].setAmount(page - 1);
            }
        }

        int start = page * maxButtons;
        int pageLength = listItems.length - start;
        int end = (pageLength >= maxButtons) ? maxButtons : pageLength;

        System.arraycopy(listItems, start, buttons, 1, end);

        if (listItems.length > (maxButtons + start)) {
            buttons[maxButtons + 1] = new PageButton(this, true);

            if (page < 64) {
                buttons[maxButtons + 1].setAmount(page + 1);
            }

        } else {
            buttons[maxButtons + 1] = null;
        }
        return buttons;
    }

    public abstract MenuButton[] generateListButtons();

    @Override
    public void open(JavaPlugin plugin, Player player) {
        listItems = generateListButtons();
        MenuButton[] buttons = paginateButtons(0, listItems);
        clearButtons();
        addButtons(buttons);
        super.open(plugin, player);
    }

    protected void changePage(JavaPlugin plugin, Player player, int page, boolean forward) {
        page += forward ? 1 : -1;
        MenuButton[] buttons = paginateButtons(page, listItems);
        clearButtons();
        addButtons(buttons);
        updateInventory(plugin, player);
    }

    static class PageButton extends MenuButton {

        boolean forward;
        ListMenu menu;

        public PageButton(ListMenu menu, boolean forward) {
            super(forward ? Material.EMERALD : Material.BARRIER,
                    forward ? "§a§l➡" : "§c§l⬅");
            this.forward = forward;
            this.menu = menu;
        }

        @Override
        public void onClick(JavaPlugin plugin, Player player) {
            int page = getAmount();
            page += forward ? -1 : 1;
            SoundCompat.UI_BUTTON_CLICK.play(player);
            menu.changePage(plugin, player, page, forward);
        }
    }

}

