package tire.lightchat.messages;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import tire.lightchat.main.LightChat;
import tire.lightchat.modules.Random;
import tire.lightchat.modules.ReplaceMethods;

public class Join {
    private LightChat plugin;
    public Join(LightChat plugin) {
        this.plugin = plugin;
    }

    public void join(PlayerJoinEvent e) {
        if((plugin.getConfig().getString("messages.join.format").trim()).length() != 0) {
            e.setJoinMessage(null);
        }

        Player Player = e.getPlayer();
        String Name = Player.getName();
        List<String> messages = new ArrayList<String>();

        if(!Player.hasPlayedBefore()) {
            messages = plugin.getConfig().getStringList("messages.join.first");
        }
        else {
            if(plugin.getConfig().contains("messages.join." + Name)) {
                messages = plugin.getConfig().getStringList("messages.join." + Name);
            } else {
                messages = plugin.getConfig().getStringList("messages.join.main");
            }
        }

        String msg = plugin.getConfig().getString("messages.join.format");
            msg = new ReplaceMethods(plugin).message(msg, new ReplaceMethods(plugin).unicode(messages.get(Random.rnd(0, messages.size() - 1))));
            msg = new ReplaceMethods(plugin).player(msg, Player, "player");

        Bukkit.broadcastMessage(msg);
    }
}
