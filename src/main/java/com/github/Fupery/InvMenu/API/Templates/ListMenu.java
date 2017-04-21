package com.github.Fupery.InvMenu.API.Templates;

import com.github.Fupery.InvMenu.API.Button.Button;
import com.github.Fupery.InvMenu.API.Button.CloseButton;
import com.github.Fupery.InvMenu.API.Event.MenuCloseReason;
import com.github.Fupery.InvMenu.API.Handler.CacheableMenu;
import com.github.Fupery.InvMenu.API.Handler.MenuHandler;
import com.github.Fupery.InvMenu.Utils.SoundCompat;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;

public abstract class ListMenu extends CacheableMenu {

    private final String heading;
    protected int page;

    public ListMenu(MenuHandler handler, String heading, int page) {
        super(handler, heading, InventoryType.CHEST);
        this.heading = heading;
        this.page = page;
    }

    @Override
    public void onMenuOpenEvent(Player viewer) {
    }

    @Override
    public void onMenuRefreshEvent(Player viewer) {
    }

    @Override
    public void onMenuClickEvent(Player viewer, int slot, ClickType click) {
    }

    @Override
    public void onMenuCloseEvent(Player viewer, MenuCloseReason reason) {
    }

    @Override
    public Button[] getButtons() {
        Button[] listItems = getListItems();
        int maxButtons = 25;
        Button[] buttons = new Button[maxButtons + 2];

        if (page < 1) {
            buttons[0] = new CloseButton(this);

        } else {
            buttons[0] = new PageButton(false);

            if (page > 0) {
                buttons[0].setAmount(page - 1);
            }
        }
        if (listItems == null || listItems.length < 1) return buttons;

        int start = page * maxButtons;
        int pageLength = listItems.length - start;

        if (pageLength > 0) {
            int end = (pageLength >= maxButtons) ? maxButtons : pageLength;

            System.arraycopy(listItems, start, buttons, 1, end);

            if (listItems.length > (maxButtons + start)) {
                buttons[maxButtons + 1] = new PageButton(true);

                if (page < 64) {
                    buttons[maxButtons + 1].setAmount(page + 1);
                }

            } else {
                buttons[maxButtons + 1] = null;
            }
        }
        return buttons;
    }

    protected void changePage(Player player, boolean forward) {
        if (forward) page++;
        else page--;
        refresh(player);
    }

    protected abstract Button[] getListItems();

    private class PageButton extends Button {

        boolean forward;

        private PageButton(boolean forward) {
            super(forward ? Material.EMERALD : Material.BARRIER, forward ? "§a§l➡" : "§c§l⬅");
            this.forward = forward;
        }

        @Override
        public void onClick(Player player, ClickType clickType) {
            if (forward) SoundCompat.UI_BUTTON_CLICK.play(player);
            else SoundCompat.UI_BUTTON_CLICK.play(player);
            changePage(player, forward);
        }
    }
}
