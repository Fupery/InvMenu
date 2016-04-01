package me.Fupery.InventoryMenu.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public enum SoundCompat {
    CLICK(Sound_1_9.UI_BUTTON_CLICK, Sound_1_8.CLICK),
    FIZZ(Sound_1_9.BLOCK_LAVA_EXTINGUISH, Sound_1_8.FIZZ),
    DING(Sound_1_9.ENTITY_EXPERIENCE_ORB_PICKUP, Sound_1_8.ORB_PICKUP),
    LEVEL_UP(Sound_1_9.ENTITY_PLAYER_LEVELUP, Sound_1_8.LEVEL_UP),
    BREAK(Sound_1_9.ENTITY_ITEM_BREAK, Sound_1_8.ITEM_BREAK),
    SNARE(Sound_1_9.BLOCK_NOTE_SNARE, Sound_1_8.NOTE_SNARE_DRUM);

    private final Sound sound;
    private final boolean isBelow1_9 = getVersion();

    SoundCompat(Sound_1_9 sound1_9, Sound_1_8 sound1_8) {
        sound = (isBelow1_9) ? Sound.values()[sound1_8.ordinal()] : Sound.values()[sound1_9.ordinal()];
    }
    public void play(Player player) {
        play(player, 1, 1);
    }

    public void play(Player player, int volume, int pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
     }

    public void play(Location location, int volume, int pitch) {
        location.getWorld().playSound(location, sound, volume, pitch);
    }

    private static boolean getVersion() {
        String bukkit = Bukkit.getBukkitVersion();
        String version = bukkit.substring(0, bukkit.indexOf('-'));
        String superversion = version.substring(0, bukkit.indexOf('.'));
        String subversion = version.substring(bukkit.indexOf('.'), version.length());
        String result = superversion + "." + subversion.replace(".", "");
        Double verID = Double.parseDouble(result);
        return (verID < 1.9);
    }
}
