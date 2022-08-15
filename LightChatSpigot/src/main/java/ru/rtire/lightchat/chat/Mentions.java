package ru.rtire.lightchat.chat;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import ru.rtire.lightchat.LightChat;
import ru.rtire.lightchat.modules.MessageFormatter;

import java.util.ArrayList;

public class Mentions {

    private LightChat plugin;

    public Mentions() {
        this.plugin = LightChat.getInstance();
    }

    public String pingEvent(String Message, String Sender,
                            Player p, String format, String messageColor) {

        MessageSender MessageSender = new MessageSender();
        MessageFormatter MessageFormatter = new MessageFormatter();

        String[] words = Message.split("[\\.,\\s-!;?:\"]+");
        String Name = p.getName();

        String inFormat = MessageFormatter.unicode(plugin.getConfig().getString("general.mentions.inFormat").trim());
        String outFormat = plugin.getConfig().getString("general.mentions.outFormat").trim();
        String outFormatMarker = plugin.getConfig().getString("general.mentions.outFormatMarker").trim();

        String display = plugin.getConfig().getString("general.mentions.message.display");
        String message = plugin.getConfig().getString("general.mentions.message.message").trim();
            message = MessageFormatter.player(message, Bukkit.getPlayer(Sender), "sender");
        String selfReference = MessageFormatter.unicode(plugin.getConfig().getString("general.mentions.message.selfReference").trim());

        String pingSound = plugin.getConfig().getString("general.mentions.sound.sound").trim().replace(" ", "_").toUpperCase();
        Float pingVolume = Float.parseFloat(plugin.getConfig().getString("general.mentions.sound.volume").trim());
        Float pingSpeed = Float.parseFloat(plugin.getConfig().getString("general.mentions.sound.pitch").trim());

        String placeholder = plugin.getConfig().getString("general.placeholder");

        ArrayList pings = new ArrayList();

        for (Player pl : Bukkit.getOnlinePlayers()) {
            for (String n : words) {
                if (n.indexOf(pl.getName()) != -1) {
                    n = n.substring(inFormat.indexOf(placeholder.replace("placeholder", "mentioned")), n.length() - (new StringBuilder(inFormat).reverse().toString().indexOf(new StringBuilder(placeholder.replace("placeholder", "mentioned")).reverse().toString())));
                    if (inFormat.replace(placeholder.replace("placeholder", "mentioned"), pl.getName()).equals(inFormat.replace(placeholder.replace("placeholder", "mentioned"), n)) && !pings.contains(n)) {
                        pings.add(n);
                    }
                }
            }
        }
        Object[] pingArray = pings.toArray();

        for (Object v : pingArray) {
            outFormatMarker = MessageFormatter.player(outFormatMarker, Bukkit.getPlayer(v.toString()), "mentioned");
            outFormat = MessageFormatter.player(outFormat, Bukkit.getPlayer(v.toString()), "mentioned");
            if (v.equals(Name) && !v.equals(Sender)) {
                p.playSound(p.getLocation(), Sound.valueOf(pingSound), pingVolume, pingSpeed);
                if(message.length() > 0) {
                    if (display.equalsIgnoreCase("hotbar")) {
                        MessageSender.sendToHotbar(p, message);
                    } else if (display.equalsIgnoreCase("chat")) {
                        MessageSender.sendToChat(p, message);
                    }
                }
                if(outFormatMarker.length() > 0) {
                    Message = Message.replaceAll("(?<=\\s\\p{Punct}{0,9999}|^\\s{0,9999}\\p{Punct}{0,9999})" + inFormat.replace(placeholder.replace("placeholder", "mentioned"), v.toString()) + "(?=\\p{Punct}*\\s|\\p{Punct}*\\s*$)", outFormatMarker + messageColor);
                    // Костыли                                        ^^^^         ^^^^              ^^^^
                } else {
                    Message = Message.replaceAll("(?<=\\s\\p{Punct}{0,9999}|^\\s{0,9999}\\p{Punct}{0,9999})" + inFormat.replace(placeholder.replace("placeholder", "mentioned"), v.toString()) + "(?=\\p{Punct}*\\s|\\p{Punct}*\\s*$)", outFormat + messageColor);
                    // Костыли                                        ^^^^         ^^^^              ^^^^
                }
            } else if (v.equals(Sender) && v.equals(Name)) {
                if(display.equalsIgnoreCase("hotbar") && selfReference.length() > 0) {
                    MessageSender.sendToHotbar(p, selfReference);
                }
                else if(display.equalsIgnoreCase("chat") && selfReference.length() > 0) {
                    MessageSender.sendToChat(p, selfReference);
                }
                Message = Message.replaceAll("(?<=\\s\\p{Punct}{0,9999}|^\\s{0,9999}\\p{Punct}{0,9999})" + inFormat.replace(placeholder.replace("placeholder", "mentioned"), v.toString()) + "(?=\\p{Punct}*\\s|\\p{Punct}*\\s*$)", outFormatMarker + messageColor);
                // Костыли                                        ^^^^         ^^^^              ^^^^
            } else {
                Message = Message.replaceAll("(?<=\\s\\p{Punct}{0,9999}|^\\s{0,9999}\\p{Punct}{0,9999})" + inFormat.replace(placeholder.replace("placeholder", "mentioned"), v.toString()) + "(?=\\p{Punct}*\\s|\\p{Punct}*\\s*$)", outFormat + messageColor);
                // Костыли                                        ^^^^         ^^^^              ^^^^
            }
        }
        return MessageFormatter.message(format, Message, messageColor);
    }
}