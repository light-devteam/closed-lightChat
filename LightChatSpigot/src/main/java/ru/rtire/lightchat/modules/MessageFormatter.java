package ru.rtire.lightchat.modules;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.rtire.lightchat.LightChat;

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

    public String player(String str, Player p, String r) {
        str = unicode(str);
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            str = placeholderReplacement(str, r + "Prefix", unicode(plugin.chat.getPlayerPrefix(p)));
            str = placeholderReplacement(str, r + "Suffix", unicode(plugin.chat.getPlayerSuffix(p)));
        }
        return placeholderReplacement(str, r, p.getName());
    }

    public String placeholderReplacement(String str, String Placeholder, String replacement) {
        return str.replace(this.Placeholder.replace("placeholder", Placeholder), replacement);
    }

    /*public String prefixes(String str) {
        str = unicode(str);
        return str.replace(Placeholder.replace("placeholder", "globalChatPrefix"), plugin.getConfig().getString("chats.globalChat.prefix"))
                .replace(Placeholder.replace("placeholder", "localChatPrefix"), plugin.getConfig().getString("chats.localChat.prefix"))
                .replace(Placeholder.replace("placeholder", "worldChatPrefix"), plugin.getConfig().getString("chats.worldChat.prefix"));
    }*/
}