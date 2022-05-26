package tire.lightchat.modules;

import tire.lightchat.main.LightChat;
import org.bukkit.Bukkit;

import org.bukkit.entity.Player;

public class ReplaceMethods {

    private LightChat plugin;
    public ReplaceMethods(LightChat plugin) {
        this.plugin = plugin;
    }

    public String unicode(String str) {
        String placeholder = plugin.getConfig().getString("config.placeholder");
        String formatSymbol = plugin.getConfig().getString("config.formatSymbol");
        return str.replace(formatSymbol, "\u00a7");
    }
    public String message(String str, String Message) {
        String placeholder = plugin.getConfig().getString("config.placeholder");
        String formatSymbol = plugin.getConfig().getString("config.formatSymbol");
        str = unicode(str);
        return str.replace(placeholder.replace("placeholder", "message"), Message);
    }
    public String message(String str, String Message, String Color) {
        String placeholder = plugin.getConfig().getString("config.placeholder");
        String formatSymbol = plugin.getConfig().getString("config.formatSymbol");
        return str.replace(placeholder.replace("placeholder", "message"), unicode(Color) + Message);
    }
    public String player(String str, Player p, String r) {
        String placeholder = plugin.getConfig().getString("config.placeholder");
        String formatSymbol = plugin.getConfig().getString("config.formatSymbol");
        str = unicode(str);
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            str = str.replace(placeholder.replace("placeholder", r + "Prefix"), this.plugin.chat.getPlayerPrefix(p)).replace(placeholder.replace("placeholder", r + "Suffix"), this.plugin.chat.getPlayerSuffix(p));
        }
        return str.replace(placeholder.replace("placeholder", r), p.getName());
    }

    public String prefixes(String str) {
        String placeholder = plugin.getConfig().getString("config.placeholder");
        String formatSymbol = plugin.getConfig().getString("config.formatSymbol");
        str = unicode(str);
        return str.replace(placeholder.replace("placeholder", "globalChatPrefix"), plugin.getConfig().getString("chats.globalChat.prefix"))
                .replace(placeholder.replace("placeholder", "localChatPrefix"), plugin.getConfig().getString("chats.localChat.prefix"))
                .replace(placeholder.replace("placeholder", "worldChatPrefix"), plugin.getConfig().getString("chats.worldChat.prefix"));
    }
}