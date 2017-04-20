package me.Fupery.InventoryMenu.Utils;

import me.Fupery.InventoryMenu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Version implements Comparable<Version> {
    private final int[] numbers;

    public Version(JavaPlugin plugin) {
        String[] strings = plugin.getDescription().getVersion().split("\\.");
        int[] numbers = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            numbers[i] = Integer.parseInt(strings[i]);
        }
        this.numbers = numbers;
    }

    public Version(int... numbers) {
        this.numbers = numbers;
    }

    public static Version getBukkitVersion() {
        String bukkit = Bukkit.getBukkitVersion();
        String[] ver = bukkit.substring(0, bukkit.indexOf('-')).split("\\.");
        int[] verNumbers = new int[ver.length];
        for (int i = 0; i < ver.length; i++) {
            verNumbers[i] = Integer.parseInt(ver[i]);
        }
        return new Version(verNumbers);
    }

    @Override
    public int compareTo(Version ver) {
        int len = (ver.numbers.length > numbers.length) ? ver.numbers.length : numbers.length;
        for (int i = 0; i < len; i++) {
            int a = i < numbers.length ? numbers[i] : 0;
            int b = i < ver.numbers.length ? ver.numbers[i] : 0;
            if (a != b) {
                return (a > b) ? 1 : -1;
            }
        }
        return 0;
    }

    public boolean isEqualTo(int... numbers) {
        return compareTo(new Version(numbers)) == 1;
    }

    public boolean isGreaterThan(int... numbers) {
        return compareTo(new Version(numbers)) == 1;
    }

    public boolean isLessThan(int... numbers) {
        return compareTo(new Version(numbers)) == -1;
    }

    @Override
    public String toString() {
        if (numbers.length == 0) return "0";
        String ver = "";
        for (int i = 0; i < numbers.length; i++) {
            ver += numbers[i];
            if (i < numbers.length - 1) ver += ".";
        }
        return ver;
    }
}
