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

        String format = plugin.getConfig().getString("chats.globalChat.format");
            format = ReplaceMethods.player(format, e.getPlayer(), "sender");
        String messageColor = plugin.getConfig().getString("chats.globalChat.messageColor");
            messageColor = ReplaceMethods.unicode(messageColor);
        String noPerms = plugin.getConfig().getString("chats.globalChat.noPerms");
            noPerms = ReplaceMethods.unicode(noPerms);
        String ping = plugin.getConfig().getString("chats.globalChat.ping");

        if(e.getPlayer().hasPermission("lc.chat.Global.write")) {
            if (ping.equalsIgnoreCase("true") && e.getPlayer().hasPermission("lc.chat.Global.mention")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasPermission("lc.chat.Global.see")) {
                        p.sendMessage(new Ping(plugin).pingEvent(Message, e.getPlayer().getName(), p, format, messageColor));
                    }
                }
            } else {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasPermission("lc.chat.Global.see")) {
                        p.sendMessage(ReplaceMethods.message(format, Message, messageColor));
                    }
                }
            }
        } else {
            if (noPerms.length() != 0) {
                e.getPlayer().sendMessage(noPerms);
            }
        }
    }
}