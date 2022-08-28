package ru.rtire.lightchat.dependencies;

import org.bukkit.entity.Player;

public class PlaceholderAPI {

    public static String setPlaceholders(Player p, String str) {
        return me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(p, str);
    }

}
