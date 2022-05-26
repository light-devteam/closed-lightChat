package tire.lightchat.modules;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

import tire.lightchat.main.LightChat;
import tire.lightchat.modules.JSONConverter;

public class sendMessage {

    private LightChat plugin;
    public sendMessage(LightChat plugin) {
        this.plugin = plugin;
    }

    public void send(Player p, String m) {
        p.spigot().sendMessage(new TextComponent(ComponentSerializer.parse(new JSONConverter(plugin).converter(m))));
    }
}
