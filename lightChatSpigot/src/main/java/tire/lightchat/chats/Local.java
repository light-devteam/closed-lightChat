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

        String messageColor = plugin.getConfig().getString("localChat.messageColor").replace("&", "\u00a7");
        String ping = plugin.getConfig().getString("localChat.ping");
        String dist = plugin.getConfig().getString("localChat.chatDistantion");
        float distantion = Float.parseFloat(dist.trim());

        String format = plugin.getConfig().getString("localChat.format").replace("&", "\u00a7");
        format = ReplaceMethods.player(format, e.getPlayer(), "sender");

        if(ping.equalsIgnoreCase("true")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (e.getPlayer().getWorld().equals(p.getWorld()) && p.getLocation().distance(e.getPlayer().getLocation()) <= distantion) {
                    p.sendMessage(new Ping(plugin).pingEvent(Message, e.getPlayer().getName(), p, format, messageColor));
                }
            }
        }
        else {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (e.getPlayer().getWorld().equals(p.getWorld()) && p.getLocation().distance(e.getPlayer().getLocation()) <= distantion) {
                    p.sendMessage(format);
                }
            }
        }

        //String msgPrefix = plugin.getConfig().getString("localChat.msgPrefix");
        //msgPrefix = replaceVarsNUnicode.customReplace(msgPrefix, e.getPlayer().getName(), "", "", "", "");
        //e.setFormat(msgPrefix + Message);
    }
}