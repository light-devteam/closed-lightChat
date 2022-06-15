package ru.rtire.lightchat.chat;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.entity.Player;

import ru.rtire.lightchat.api.ChatEvent;
import ru.rtire.lightchat.LightChat;

public class ChatListener implements Listener {
    private LightChat plugin;
    private AsyncPlayerChatEvent e;
    private ru.rtire.lightchat.api.ChatEvent ChatEvent;

    public ChatListener() { this.plugin = LightChat.getInstance(); }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) throws Exception {
        e.setCancelled(true);
        this.e = e;
        eventChecker(e);
    }
    public void eventChecker(AsyncPlayerChatEvent e) {
        eventCaller();
        if(!ChatEvent.isCancelled()) {
            new Chat().chatCaller(e);
        }
    }
    public void eventChecker(String Message, Player Sender) {
        eventCaller();
        if(!ChatEvent.isCancelled()) {
            new Chat().chatCaller(Message, Sender);
        }
    }

    private void eventCaller() {
        Bukkit.getPluginManager().callEvent(ChatEvent);
        ChatEvent.setEvent(e);
    }
}
