package ru.rtire.lightchat.chat.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

import org.bukkit.entity.Player;

import ru.rtire.lightchat.LightChat;
import ru.rtire.lightchat.api.ChatEvent;
import ru.rtire.lightchat.utils.StringToMillis;

public class Cooldown {
    private static Map<String, Map<Player, Long>> cooldown = new HashMap<>();
    private static Map<Player, Long> gCooldown = new HashMap<>();
    private static LightChat plugin;
    private static boolean init = false;

    static {
        plugin = LightChat.getInstance();
    }

    public static Long cooldown() {
        String Chat = ChatEvent.getChat();
        Player Sender = ChatEvent.getSender();

        String cd = plugin.getConfig().getString(String.format("chats.%s.cooldown.cooldown", Chat)).trim();
        String gcd = plugin.getConfig().getString("general.chat.cooldown.general").trim();

        Long currentTimeMillis = new Date().getTime();
        Long cooldownTimeMillis = currentTimeMillis + StringToMillis.Parse(cd);
        Long gCooldownTimeMillis = currentTimeMillis + StringToMillis.Parse(gcd);

        if(!init) init();
        Long difference = 0L;
        Long gDifference = 0L;
        if(cooldown.get(Chat).get(Sender) != null) {
            difference = cooldown.get(Chat).get(Sender) - currentTimeMillis;
        }
        if(gCooldown.get(Sender) != null) {
            gDifference = gCooldown.get(Sender) - currentTimeMillis;
        }
        if(cooldown.get(Chat).get(Sender) == null || cooldown.get(Chat).get(Sender) - currentTimeMillis <= 0) {
            cooldown.get(Chat).put(Sender, cooldownTimeMillis);
        }
        if(gCooldown.get(Sender) == null || gCooldown.get(Sender) - currentTimeMillis <= 0) {
            gCooldown.put(Sender, gCooldownTimeMillis);
        }

        if(gCooldownTimeMillis > cooldownTimeMillis) return gDifference;
        return difference;
    }

    private static void init() {
        for (String Chat : new ArrayList<>(plugin.getConfig().getConfigurationSection("chats").getKeys(false))) {
            cooldown.put(Chat, new HashMap<Player, Long>());
        }
        init = true;
    }


}
