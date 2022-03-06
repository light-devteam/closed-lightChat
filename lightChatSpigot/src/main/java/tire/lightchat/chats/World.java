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

        String messageColor = plugin.getConfig().getString("worldChat.messageColor").replace("&", "\u00a7");
        String ping = plugin.getConfig().getString("worldChat.ping");

        String format = plugin.getConfig().getString("worldChat.format").replace("&", "\u00a7");
            format = ReplaceMethods.player(format, e.getPlayer(), "sender");

        if(ping.equalsIgnoreCase("true")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getWorld().equals(e.getPlayer().getWorld())) {
                    p.sendMessage(new Ping(plugin).pingEvent(Message, e.getPlayer().getName(), p, format, messageColor));
                }
            }
        }
        else {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getWorld().equals(e.getPlayer().getWorld())) {
                    p.sendMessage(format);
                }
            }
        }
    }
}
