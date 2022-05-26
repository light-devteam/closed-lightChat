package tire.lightchat.modules;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.Sound;

import tire.lightchat.main.LightChat;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;

public class Ping {

    private LightChat plugin;

    public Ping(LightChat plugin) {
        this.plugin = plugin;
    }

    public String pingEvent(String Message, String Sender,
                            Player p, String format, String messageColor) {

        ReplaceMethods ReplaceMethods = new ReplaceMethods(plugin);

        String[] words = Message.split("[\\.,\\s-!;?:\"]+");
        String Name = p.getName();

        String inFormat = plugin.getConfig().getString("ping.inFormat");
            inFormat = ReplaceMethods.unicode(inFormat);
        String outFormat = plugin.getConfig().getString("ping.outFormat").trim();
        String pingMessage = plugin.getConfig().getString("ping.message").trim();
            pingMessage = ReplaceMethods.player(pingMessage, Bukkit.getPlayer(Sender), "sender");
        String myself = plugin.getConfig().getString("ping.myself");
            myself = ReplaceMethods.unicode(myself);

        String marker = plugin.getConfig().getString("ping.marker").trim();

        String pingSound = plugin.getConfig().getString("ping.sound.sound").trim().replace(" ", "_").toUpperCase();
        Float pingVolume = Float.parseFloat(plugin.getConfig().getString("ping.sound.volume"));
        Float pingSpeed = Float.parseFloat(plugin.getConfig().getString("ping.sound.pitch"));

        String placeholder = plugin.getConfig().getString("config.placeholder");

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
            marker = ReplaceMethods.player(marker, Bukkit.getPlayer(v.toString()), "mentioned");
            outFormat = ReplaceMethods.player(outFormat, Bukkit.getPlayer(v.toString()), "mentioned");
            if (v.equals(Name) && !v.equals(Sender)) {
                p.playSound(p.getLocation(), Sound.valueOf(pingSound), pingVolume, pingSpeed);
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(pingMessage));
                if(marker.length() != 0) {
                    Message = Message.replaceAll("(?<=\\s\\p{Punct}{0,9999}|^\\s{0,9999}\\p{Punct}{0,9999})" + inFormat.replace(placeholder.replace("placeholder", "mentioned"), v.toString()) + "(?=\\p{Punct}*\\s|\\p{Punct}*\\s*$)", marker + messageColor);
                } else {
                    Message = Message.replaceAll("(?<=\\s\\p{Punct}{0,9999}|^\\s{0,9999}\\p{Punct}{0,9999})" + inFormat.replace(placeholder.replace("placeholder", "mentioned"), v.toString()) + "(?=\\p{Punct}*\\s|\\p{Punct}*\\s*$)", outFormat + messageColor);
                }
            } else if (v.equals(Sender) && v.equals(Name)) {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(myself));
                Message = Message.replaceAll("(?<=\\s\\p{Punct}{0,9999}|^\\s{0,9999}\\p{Punct}{0,9999})" + inFormat.replace(placeholder.replace("placeholder", "mentioned"), v.toString()) + "(?=\\p{Punct}*\\s|\\p{Punct}*\\s*$)", marker + messageColor);
            } else {
                Message = Message.replaceAll("(?<=\\s\\p{Punct}{0,9999}|^\\s{0,9999}\\p{Punct}{0,9999})" + inFormat.replace(placeholder.replace("placeholder", "mentioned"), v.toString()) + "(?=\\p{Punct}*\\s|\\p{Punct}*\\s*$)", outFormat + messageColor);
            }
        }
        return ReplaceMethods.message(format, Message, messageColor);
    }
}