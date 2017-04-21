package com.github.Fupery.InvMenu.Utils;

import org.bukkit.Bukkit;

public enum VersionHandler {
    v1_8, v1_9, v1_10;

    public static VersionHandler getVersion() {
        String bukkit = Bukkit.getBukkitVersion();
        String[] ver = bukkit.substring(0, bukkit.indexOf('-')).split("\\.");
        int[] verNumbers = new int[ver.length];
        for (int i = 0; i < ver.length; i++) {
            verNumbers[i] = Integer.parseInt(ver[i]);
        }
        Version version = new Version(verNumbers);
        if (version.isLessThan(1, 9)) return v1_8;
        else if (version.isLessThan(1, 10)) return v1_9;
        else return v1_10;
    }

    public static VersionHandler getLatest() {
        VersionHandler[] handlers = values();
        return handlers[values().length - 1];
    }

    static class Version implements Comparable<Version> {
        final int[] numbers;

        Version(int... numbers) {
            this.numbers = numbers;
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

        boolean isGreaterThan(int... numbers) {
            return compareTo(new Version(numbers)) == 1;
        }

        boolean isGreaterThanOrEqualTo(int... numbers) {
            return compareTo(new Version(numbers)) >= 0;
        }

        boolean isLessThan(int... numbers) {
            return compareTo(new Version(numbers)) == -1;
        }

        boolean isLessThanOrEqualTo(int numbers) {
            return compareTo(new Version(numbers)) <= 0;
        }
    }
}
