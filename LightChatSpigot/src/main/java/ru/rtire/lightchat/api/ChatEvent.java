package ru.rtire.lightchat.api;

import org.bukkit.event.Event;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.entity.Player;

import ru.rtire.lightchat.LightChat;
import ru.rtire.lightchat.chat.ChatListener;

public class ChatEvent extends Event implements Cancellable {
    private static HandlerList list = new HandlerList();

    private boolean Cancelled;
    private LightChat plugin;

    private static AsyncPlayerChatEvent e;
    private static String Message;
    private static Player Sender;
    private static String SenderNickname;


    public ChatEvent() {
        this.plugin = LightChat.getInstance();
    }

    @Override
    public HandlerList getHandlers() { return list; }
    @Override
    public boolean isCancelled() { return Cancelled; }
    @Override
    public void setCancelled(boolean Cancelled) { this.Cancelled = Cancelled; }

    public static void setEvent(AsyncPlayerChatEvent e) { ChatEvent.e = e; }
    public static AsyncPlayerChatEvent getEvent() { return ChatEvent.e; }

    public static void setMessage(String Message) { ChatEvent.Message = Message; }
    public static String getMessage() { return ChatEvent.Message; }

    public static void setSender(Player Sender) { ChatEvent.Sender = Sender; }
    public static Player getSender() { return ChatEvent.Sender; }

    public static String getSenderNickname() { return ChatEvent.SenderNickname; }

    public static void callChatEvent(AsyncPlayerChatEvent e) { new ChatListener().eventChecker(e); }
    public static void callChatEvent(String Message, Player Sender) { new ChatListener().eventChecker(Message, Sender); }
}
