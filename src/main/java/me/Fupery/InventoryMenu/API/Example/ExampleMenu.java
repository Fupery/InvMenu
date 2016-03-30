package me.Fupery.InventoryMenu.API.Example;

import me.Fupery.InventoryMenu.API.InventoryMenu;
import me.Fupery.InventoryMenu.API.MenuButton;
import me.Fupery.InventoryMenu.Menu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;

public class ExampleMenu {

    //Run InventoryMenu#open(plugin, player) to open a menu
    public static void openMenu(JavaPlugin plugin, Player player) {
        Menu menu = new Menu(plugin, "My rad title!", InventoryType.HOPPER);
        InventoryMenu subMenu = menu.subMenu("A linked Sub-Menu!", InventoryType.CHEST);

        menu.addButtons(
                //Static button displays help text, but doesn't do anything when clicked
                new MenuButton.StaticButton(Material.DIAMOND, "Fancy Button", "Lots of helpful text", "On each line"),

                //LinkedButton opens a new menu when clicked
                new MenuButton.LinkedButton(subMenu, Material.BOOK_AND_QUILL, "This button links to an empty menu!"),

                //You can also implement MenuButton to make custom types of menu buttons if you like
                new MenuButton(Material.SKULL_ITEM, "Custom Button Implementation - Don't click me!") {
                    @Override
                    public void onClick(JavaPlugin plugin, Player player) {
                        player.sendMessage(ChatColor.RED + "Hey, I told you not to click me!");
                        player.damage(100000);
                    }
                },
                //CloseButton will return to the previous menu, will close the menu if there is none
                new MenuButton.CloseButton(menu)
        );
        //Call open to open the menu for a player
        menu.open(plugin, player);
    }
}
