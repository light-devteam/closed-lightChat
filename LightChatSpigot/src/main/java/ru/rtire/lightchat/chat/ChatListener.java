package ru.rtire.lightchat.chat;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.entity.Player;

import ru.rtire.lightchat.LightChat;
import ru.rtire.lightchat.api.ChatEvent;

public class ChatListener implements Listener {
    private LightChat plugin;

    public ChatListener() {
        this.plugin = LightChat.getInstance();
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        ChatEvent.setEvent(e);
        e.setCancelled(true);
        eventCaller(e);
    }

    public void eventCaller(AsyncPlayerChatEvent e) {
        new Chat().chatCaller(e);
    }
    public void eventCaller(String Message, String SenderNickname) {
        new Chat().chatCaller(Message, SenderNickname);
    }
}
