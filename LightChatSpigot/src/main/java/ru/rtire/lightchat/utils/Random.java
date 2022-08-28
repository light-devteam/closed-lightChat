package ru.rtire.lightchat.utils;

public class Random {

    private static int min = 0;

    public static int generate(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
    public static int generate(int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
}
