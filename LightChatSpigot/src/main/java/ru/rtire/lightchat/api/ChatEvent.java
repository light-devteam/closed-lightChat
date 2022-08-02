package ru.rtire.lightchat.api;

import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.entity.Player;

import ru.rtire.lightchat.LightChat;
import ru.rtire.lightchat.chat.ChatListener;

public final class ChatEvent {
    private static LightChat plugin;
    private static AsyncPlayerChatEvent e;


    public ChatEvent() {
        this.plugin = LightChat.getInstance();
    }

    public static void setEvent(AsyncPlayerChatEvent e) { ChatEvent.e = e; }

    public static AsyncPlayerChatEvent getEvent() { return ChatEvent.e; }
    public static String getMessage() { return ChatEvent.e.getMessage(); }
    public static Player getSender() { return ChatEvent.e.getPlayer(); }
    public static String getSenderNickname() { return ChatEvent.e.getPlayer().getName(); }

    public static void callChatEvent(AsyncPlayerChatEvent e) { new ChatListener().eventCaller(e); }
    public static void callChatEvent(String Message, String SenderNickname) { new ChatListener().eventCaller(Message, SenderNickname); }
    public static void callChatEvent() { new ChatListener().eventCaller(ChatEvent.e); }
}
