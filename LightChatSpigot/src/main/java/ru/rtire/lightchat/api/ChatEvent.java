package ru.rtire.lightchat.api;

import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.entity.Player;

import ru.rtire.lightchat.LightChat;
import ru.rtire.lightchat.chat.ChatListener;
import ru.rtire.lightchat.chat.ChatDefinition;

import java.util.ArrayList;

public final class ChatEvent {
    private static LightChat plugin;
    private static AsyncPlayerChatEvent e;

    private static ArrayList<Player> recipients;

    static {
        plugin = LightChat.getInstance();
    }

    public static void setEvent(AsyncPlayerChatEvent e) { ChatEvent.e = e; }

    public static void setRecipients() {
        ArrayList<Player> recipients = new ArrayList<Player>();

        String Chat = getChat();
        Player Sender = getSender();

        Float Distance = Float.parseFloat(plugin.getConfig().getString(String.format("chats.%s.distance", Chat)).trim());

        if (Distance <= -2) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission(String.format("lc.chat.%s.see", Chat))) {
                    recipients.add(p);
                }
            }
        }
        else if (Distance == -1) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getWorld().equals(Sender.getWorld()) && p.hasPermission(String.format("lc.chat.%s.see", Chat))) {
                    recipients.add(p);
                }
            }
        }
        else if(Distance >= 0) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getWorld().equals(Sender.getWorld()) && p.getLocation().distance(Sender.getLocation()) <= Distance && p.hasPermission(String.format("lc.chat.%s.see", Chat))) {
                    recipients.add(p);
                }
            }
        }
        ChatEvent.recipients = recipients;
    }

    public static AsyncPlayerChatEvent getEvent() { return ChatEvent.e; }
    public static String getMessage() { return ChatEvent.e.getMessage(); }
    public static Player getSender() { return ChatEvent.e.getPlayer(); }
    public static String getSenderNickname() { return ChatEvent.e.getPlayer().getName(); }
    public static String getChat() { return ChatDefinition.definition(); }
    public static ArrayList<Player> getRecipients() { return recipients; }

    public static void callChatEvent(AsyncPlayerChatEvent e) { new ChatListener().eventCaller(e); }
    public static void callChatEvent(String Message, String SenderNickname) { new ChatListener().eventCaller(Message, SenderNickname); }
    public static void callChatEvent() { new ChatListener().eventCaller(ChatEvent.e); }
}
