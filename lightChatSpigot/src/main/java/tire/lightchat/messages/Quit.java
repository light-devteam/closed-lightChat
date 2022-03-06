package tire.lightchat.messages;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

import tire.lightchat.main.LightChat;
import tire.lightchat.modules.Random;
import tire.lightchat.modules.ReplaceMethods;

public class Quit {
    private LightChat plugin;
    public Quit(LightChat plugin) {
        this.plugin = plugin;
    }

    public void quit(PlayerQuitEvent e) {
        e.setQuitMessage(null);

        Player Player = e.getPlayer();
        String Name = Player.getName();
        List<String> messages = new ArrayList<String>();

        if(plugin.getConfig().contains("messages.quit." + Name)) {
            messages = plugin.getConfig().getStringList("messages.quit." + Name);
        } else {
            messages = plugin.getConfig().getStringList("messages.quit.main");
        }

        String msg = plugin.getConfig().getString("messages.join.format")
                .replace("{message}", messages.get(Random.rnd(0, messages.size() - 1)))
                .replace("&", "\u00a7");
            msg = new ReplaceMethods(plugin).player(msg, Player, "player");

        Bukkit.broadcastMessage(msg);
    }
}
