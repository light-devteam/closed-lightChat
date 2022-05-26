package tire.lightchat.chats;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import tire.lightchat.main.LightChat;
import tire.lightchat.modules.ReplaceMethods;
import tire.lightchat.modules.Ping;

public class Local {
    private LightChat plugin;
    public Local(LightChat plugin) {
        this.plugin = plugin;
    }

    public void sendMessage(String Message, AsyncPlayerChatEvent e) {

        ReplaceMethods ReplaceMethods = new ReplaceMethods(plugin);

        String messageColor = plugin.getConfig().getString("chats.localChat.messageColor");
            messageColor = ReplaceMethods.unicode(messageColor);
        String noPerms = plugin.getConfig().getString("chats.localChat.noPerms");
            noPerms = ReplaceMethods.player(noPerms, e.getPlayer(), "sender");
        String format = plugin.getConfig().getString("chats.localChat.format");
            format = ReplaceMethods.player(format, e.getPlayer(), "sender");

        String ping = plugin.getConfig().getString("chats.localChat.ping");
        String noPermsPing = plugin.getConfig().getString("chats.localChat.noPermsPing");
            noPermsPing = ReplaceMethods.player(noPermsPing, e.getPlayer(), "sender");

        float distantion = Float.parseFloat(plugin.getConfig().getString("chats.localChat.chatDistantion").trim());

        if(e.getPlayer().hasPermission("lc.chat.Local.write")) {
            if (ping.equalsIgnoreCase("true")) {
                if(e.getPlayer().hasPermission("lc.chat.Local.mention")) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.hasPermission("lc.chat.Local.see")) {
                            if (e.getPlayer().getWorld().equals(p.getWorld()) && p.getLocation().distance(e.getPlayer().getLocation()) <= distantion) {
                                p.sendMessage(new Ping(plugin).pingEvent(Message, e.getPlayer().getName(), p, format, messageColor));
                            }
                        }
                    }
                } else {
                    if (noPermsPing.length() != 0) {
                        e.getPlayer().sendMessage(noPermsPing);
                    }
                }
            } else {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasPermission("lc.chat.Local.see")) {
                        if (e.getPlayer().getWorld().equals(p.getWorld()) && p.getLocation().distance(e.getPlayer().getLocation()) <= distantion) {
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