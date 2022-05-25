package tire.lightchat.chats;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import tire.lightchat.main.LightChat;
import tire.lightchat.modules.Ping;
import tire.lightchat.modules.ReplaceMethods;

public class World {
    private LightChat plugin;
    public World(LightChat plugin) {
        this.plugin = plugin;
    }

    public void sendMessage(String Message, AsyncPlayerChatEvent e) {
        ReplaceMethods ReplaceMethods = new ReplaceMethods(plugin);

        String messageColor = plugin.getConfig().getString("chats.worldChat.messageColor");
            messageColor = ReplaceMethods.unicode(messageColor);
        String noPerms = plugin.getConfig().getString("chats.worldChat.noPerms");
            noPerms = ReplaceMethods.unicode(noPerms);
        String format = plugin.getConfig().getString("chats.worldChat.format");
            format = ReplaceMethods.player(format, e.getPlayer(), "sender");

        String ping = plugin.getConfig().getString("chats.worldChat.ping");

        if(e.getPlayer().hasPermission("lc.chat.World.write")) {
            if (ping.equalsIgnoreCase("true") && e.getPlayer().hasPermission("lc.chat.World.mention")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasPermission("lc.chat.Local.see")) {
                        if (p.getWorld().equals(e.getPlayer().getWorld())) {
                            p.sendMessage(new Ping(plugin).pingEvent(Message, e.getPlayer().getName(), p, format, messageColor));
                        }
                    }
                }
            } else {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasPermission("lc.chat.Local.see")) {
                        if (p.getWorld().equals(e.getPlayer().getWorld())) {
                            p.sendMessage(ReplaceMethods.message(format, Message, messageColor));
                        }
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
