package me.Fupery.InvMenu.API.Button;

import me.Fupery.InvMenu.API.Event.MenuCloseReason;
import me.Fupery.InvMenu.API.Handler.CacheableMenu;
import me.Fupery.InvMenu.Utils.SoundCompat;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class CloseButton extends Button {

    private final CacheableMenu menu;

    public CloseButton(CacheableMenu menu) {
        super(Material.BARRIER, "Close"); //todo
        this.menu = menu;
    }

    @Override
    public void onClick(Player player, ClickType clickType) {
        SoundCompat.UI_BUTTON_CLICK.play(player, 1, 3);
        menu.getHandler().closeMenu(player, MenuCloseReason.BACK);
    }
}
