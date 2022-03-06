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
        return str.replace("&", "\u00a7");
    }
    public String message(String str, String Message) {
        return str.replace("{message}", Message);
    }
    public String message(String str, String Message, String Color) {
        return str.replace("{message}", unicode(Color) + Message);
    }
    public String player(String str, Player p, String r) {
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            str = str.replace("{" + r + "Prefix}", this.plugin.chat.getPlayerPrefix(p)).replace("{" + r + "Suffix}", this.plugin.chat.getPlayerSuffix(p));
        }
        return str.replace("{" + r + "}", p.getName());
    }
    public String recipient(String str, String Recipient) {
        return str.replace("{recipient}", Recipient);
    }
    public String mention(String str, String Mentioned) {
        return str.replace("{mentioned}", Mentioned);
    }

    public String prefixes(String str) {
        return str.replace("{globalChatPrefix}", plugin.getConfig().getString("globalChat.prefix")).replace("{localChatPrefix}", plugin.getConfig().getString("localChat.prefix")).replace("{worldChatPrefix}", plugin.getConfig().getString("worldChat.prefix"));
    }
}