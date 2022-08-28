package ru.rtire.lightchat.chat.modules;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ru.rtire.lightchat.LightChat;
import ru.rtire.lightchat.dependencies.Vault;
import ru.rtire.lightchat.dependencies.PlaceholderAPI;

public final class MessageFormatter {

    private static LightChat plugin;
    private static String Placeholder;
    private static String Format;

    public MessageFormatter() {
        plugin = LightChat.getInstance();
        Placeholder = plugin.getConfig().getString("general.placeholder");
        Format = plugin.getConfig().getString("general.format");
    }

    public String unicode(String str) {
        return str.replace(Format, "\u00a7");
    }

    public String message(String str, String Message, String Color) {
        return placeholderReplacement(unicode(str), "message", unicode(Color) + Message);
    }
    public String message(String str, String Message) {
        return placeholderReplacement(str, "message", Message);
    }

    public String player(String str, Player p, String r) {
        str = unicode(str);
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            Vault Vault = new Vault();
            str = placeholderReplacement(str, r + "Prefix", unicode(Vault.getPlayerPrefix(p)));
            str = placeholderReplacement(str, r + "Suffix", unicode(Vault.getPlayerSuffix(p)));
        }
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            str = PlaceholderAPI(str, p);
        }
        return placeholderReplacement(str, r, p.getName());
    }
    public String player(String str, Player p, String r, boolean unicode) {
        if(!unicode) { return new MessageFormatter().player(str, p, r); }
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            Vault Vault = new Vault();
            str = placeholderReplacement(str, r + "Prefix", Vault.getPlayerPrefix(p));
            str = placeholderReplacement(str, r + "Suffix", Vault.getPlayerSuffix(p));
        }
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            str = PlaceholderAPI(str, p);
        }
        return placeholderReplacement(str, r, p.getName());
    }

    public String placeholderReplacement(String str, String Placeholder, String replacement) {
        return str.replace(this.Placeholder.replace("placeholder", Placeholder), replacement);
    }

    public String PlaceholderAPI(String str, Player p) {
        return PlaceholderAPI.setPlaceholders(p, str);
    }
}