package com.github.Fupery.InvMenu.API.Templates;

import com.github.Fupery.InvMenu.API.Button.Button;
import com.github.Fupery.InvMenu.API.Button.CloseButton;
import com.github.Fupery.InvMenu.API.Event.MenuCloseReason;
import com.github.Fupery.InvMenu.API.Handler.CacheableMenu;
import com.github.Fupery.InvMenu.API.Handler.MenuHandler;
import com.github.Fupery.InvMenu.Utils.MenuType;
import com.github.Fupery.InvMenu.Utils.SoundCompat;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;

public abstract class ListMenu extends CacheableMenu {

    protected int page;
    protected int listStart;

    public ListMenu(MenuHandler handler, String heading, int page) {
        this(handler, heading, 0, page);
    }

    public ListMenu(MenuHandler handler, String heading, int listStartIndex, int page) {
        this(handler, heading, new MenuType(InventoryType.CHEST), listStartIndex, page);
    }

    public ListMenu(MenuHandler handler, String heading, MenuType menuType, int listStartIndex, int page) {
        super(handler, heading, menuType);
        this.heading = heading;
        this.listStart = listStartIndex;
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
        int maxButtons = 25 - listStart;
        Button[] buttons = new Button[type.getDefaultSize()];

        if (page < 1) {
            buttons[listStart] = new CloseButton(this);

        } else {
            buttons[listStart] = new PageButton(false);

            if (page > 1) {
                buttons[listStart].setAmount(page - 1);
            }
        }
        if (listItems == null || listItems.length < 1) return buttons;

        int start = page * (maxButtons);
        int pageLength = listItems.length - start;

        if (pageLength > 0) {
            int end = (pageLength >= maxButtons) ? maxButtons : pageLength;

            System.arraycopy(listItems, start, buttons, listStart + 1, end);

            if (listItems.length > (maxButtons + start)) {
                buttons[maxButtons + listStart + 1] = new PageButton(true);

                if (page < 64) {
                    buttons[maxButtons + listStart + 1].setAmount(page + 1);
                }

            } else {
                buttons[maxButtons + listStart + 1] = null;
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

    protected class PageButton extends Button {

        boolean forward;

        public PageButton(boolean forward) {
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
