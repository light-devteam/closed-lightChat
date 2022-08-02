package ru.rtire.lightchat.chat;

import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.*;

import ru.rtire.lightchat.LightChat;
import ru.rtire.lightchat.api.ChatEvent;

public class Chat {
    private LightChat plugin;

    public Chat() {
        this.plugin = LightChat.getInstance();
    }

    public void chatCaller(AsyncPlayerChatEvent e) {
        String Message = e.getMessage();
        Player Sender = e.getPlayer();
        String SenderNickname = Sender.getName();
        chatDefinition(Message, Sender, SenderNickname);
    }
    public void chatCaller(String Message, String SenderNickname) {
        chatDefinition(Message, null, SenderNickname);
    }

    private void chatDefinition(String Message, Player Sender, String SenderNickname) {
        HashMap<String, String> Prefixes = new HashMap<>();
        for (String s : new ArrayList<>(plugin.getConfig().getConfigurationSection("chats").getKeys(false))) {
            Prefixes.put(s, plugin.getConfig().getString("chats." + s + ".prefix"));
        }
        for (Map.Entry<String, String> entry : Prefixes.entrySet()) {
            String Chat = entry.getKey();
            String Prefix = entry.getValue();
            if (ChatEvent.getMessage().startsWith(Prefix)) {
                Message = Message.substring(Prefix.length(), Message.length());
                if (Sender == null) {
                    sendingAnEmulatedMessage(Message, SenderNickname, Chat);
                } else {
                    sendingMessage(Message, Sender, SenderNickname, Chat);
                }
                break;
            }
        }
    }

    private void sendingAnEmulatedMessage(String Message, String SenderNickname, String Chat) {
        // ...
    }

    private void sendingMessage(String Message, Player Sender, String SenderNickname, String Chat) {
        Sender.sendMessage(Chat + "|" + SenderNickname + " : " + Message);
    }
}
