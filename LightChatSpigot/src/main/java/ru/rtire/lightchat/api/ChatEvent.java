package ru.rtire.lightchat.api;

import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.Location;

import ru.rtire.lightchat.LightChat;
import ru.rtire.lightchat.chat.ChatListener;
import ru.rtire.lightchat.chat.ChatDefinition;

import java.util.ArrayList;

public final class ChatEvent {
    private static LightChat plugin;

    private static AsyncPlayerChatEvent Event;
    private static String Message;
    private static Player Sender;
    private static Location SenderLocation;
    private static String Chat;
    private static ArrayList<Player> Recipients;

    static {
        plugin = LightChat.getInstance();
    }

    public static void setAll(String m, Player p) {
        setMessage(m);
        setSender(p);
        setSenderLocation();
        setChat();
        setRecipients();
    }
    public static void setAll(AsyncPlayerChatEvent e, String m, Player p) {
        setEvent(e);
        if(e == null) {
            setMessage(m);
            setSender(p);
            setSenderLocation();
            setChat();
            setRecipients();
        }
    }

    public static void setEvent(AsyncPlayerChatEvent e) {
        Event = e;
        if(e != null) setAll(e.getMessage(), e.getPlayer());
    }
    public static void setMessage(String m) { Message = m; }
    public static void setSender(Player p) { Sender = p; }
    public static void setSenderLocation() { Sender.getLocation(); }
    public static void setChat() { if(Message != null) Chat = ChatDefinition.definition(); }
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
                if (p.getWorld().equals(Sender.getWorld()) && p.getLocation().distance(SenderLocation) <= Distance && p.hasPermission(String.format("lc.chat.%s.see", Chat))) {
                    recipients.add(p);
                }
            }
        }
        Recipients = recipients;
    }

    public static AsyncPlayerChatEvent getEvent() { return Event; }
    public static String getMessage() { return Message; }
    public static Player getSender() { return Sender; }
    public static String getSenderNickname() { return Sender.getName(); }
    public static Location getSenderLocation() { return SenderLocation; }
    public static String getChat() { return Chat; }
    public static ArrayList<Player> getRecipients() { return Recipients; }

    public static void callChatEvent(AsyncPlayerChatEvent e) { new ChatListener().eventCaller(); }
    public static void callChatEvent(String Message, String SenderNickname) { new ChatListener().eventCaller(Message, SenderNickname); }
    public static void callChatEvent() { new ChatListener().eventCaller(); }
}
