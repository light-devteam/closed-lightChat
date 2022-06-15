package ru.rtire.lightchat.chat;

import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import ru.rtire.lightchat.LightChat;

public class Chat {
    private LightChat plugin;

    public Chat() {
        this.plugin = LightChat.getInstance();
    }

    public void chatCaller(AsyncPlayerChatEvent e) {
        String Message = e.getMessage();
        Player Sender = e.getPlayer();
        String SenderNickname = Sender.getName();
        chat(Message, Sender, SenderNickname);
    }
    public void chatCaller(String Message, Player Sender) {
        String SenderNickname = Sender.getName();
        chat(Message, Sender, SenderNickname);
    }

    public void chat(String Message, Player Sender, String SenderNickname) {
        HashMap<String, String> prefixes = new HashMap<>();
        for (String s: plugin.getConfig().getConfigurationSection("chats").getKeys(false)) {
            prefixes.put(s, plugin.getConfig().getString("chats." + s + ".prefix"));
        }
    }
}
