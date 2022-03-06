package tire.lightchat.chats;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import tire.lightchat.main.LightChat;
import tire.lightchat.modules.ReplaceMethods;
import tire.lightchat.modules.Ping;

public class Global {

    private LightChat plugin;
    public Global(LightChat plugin) {
        this.plugin = plugin;
    }

    public void sendMessage(String Message, AsyncPlayerChatEvent e) {

        ReplaceMethods ReplaceMethods = new ReplaceMethods(plugin);

        String format = plugin.getConfig().getString("globalChat.format").replace("&", "\u00a7");
        String messageColor = plugin.getConfig().getString("globalChat.messageColor").replace("&", "\u00a7");
        String ping = plugin.getConfig().getString("globalChat.ping");
        format = ReplaceMethods.player(format, e.getPlayer(), "sender");

        if(ping.equalsIgnoreCase("true")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(new Ping(plugin).pingEvent(Message, e.getPlayer().getName(), p, format, messageColor));
            }
        }
        else {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(format);
            }
        }
    }
}