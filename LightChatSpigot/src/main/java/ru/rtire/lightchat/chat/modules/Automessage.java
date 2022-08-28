package ru.rtire.lightchat.chat.modules;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ru.rtire.lightchat.LightChat;

public class Automessage {

    private static LightChat plugin;

    static {
        plugin = LightChat.getInstance();
    }

    public static void start() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            while(plugin.isEnabled()) {
                for(Player p : Bukkit.getOnlinePlayers()) p.sendMessage("message");
                try {
                    Thread.sleep(10000);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
