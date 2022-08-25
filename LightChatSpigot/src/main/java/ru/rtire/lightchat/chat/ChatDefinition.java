package ru.rtire.lightchat.chat;

import org.bukkit.entity.Player;
import ru.rtire.lightchat.LightChat;
import ru.rtire.lightchat.api.ChatEvent;
import ru.rtire.lightchat.chat.modules.MessageFormatter;
import ru.rtire.lightchat.chat.modules.MessageSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatDefinition {
    private static LightChat plugin;

    static {
        plugin = LightChat.getInstance();
    }

    public static String definition () {
        MessageFormatter MessageFormatter = new MessageFormatter();
        MessageSender MessageSender = new MessageSender();

        HashMap<String, String> Prefixes = new HashMap<>();
        for (String s : new ArrayList<>(plugin.getConfig().getConfigurationSection("chats").getKeys(false))) {
            Prefixes.put(s, plugin.getConfig().getString(String.format("chats.%s.prefix", s)));
        }
        for (Map.Entry<String, String> entry : Prefixes.entrySet()) {
            String Chat = entry.getKey();
            String Prefix = entry.getValue();
            if (ChatEvent.getMessage().startsWith(Prefix)) {
                return Chat;
            }
        }
        return null;
    }
}
