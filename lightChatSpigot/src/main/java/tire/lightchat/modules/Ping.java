package tire.lightchat.modules;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.Sound;

import tire.lightchat.main.LightChat;
import tire.lightchat.modules.ReplaceMethods;

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

        String inFormat = plugin.getConfig().getString("ping.inFormat").replace("&", "\u00a7");
        String outFormat = plugin.getConfig().getString("ping.outFormat").replace("&", "\u00a7");
        String pingMessage = plugin.getConfig().getString("ping.message").replace("&", "\u00a7");
            pingMessage = ReplaceMethods.player(pingMessage, Bukkit.getPlayer(Sender), "sender");
        String myself = plugin.getConfig().getString("ping.myself").replace("&", "\u00a7");

        String visibility = plugin.getConfig().getString("ping.marker.visibility");
        String markerColor = plugin.getConfig().getString("ping.marker.markerColor").replace("&", "\u00a7");
        String marker = plugin.getConfig().getString("ping.marker.enable");

        String pingSound = plugin.getConfig().getString("ping.sound.sound").trim().replace(" ", "_").toUpperCase();
        Float pingVolume = Float.parseFloat(plugin.getConfig().getString("ping.sound.volume"));
        Float pingSpeed = Float.parseFloat(plugin.getConfig().getString("ping.sound.pitch"));

        ArrayList pings = new ArrayList();

        for (Player pl : Bukkit.getOnlinePlayers()) {
            for (String n : words) {
                if (n.indexOf(pl.getName()) != -1) {
                    n = n.substring(inFormat.indexOf("{mentioned}"), n.length() - (new StringBuilder(inFormat).reverse().toString().indexOf(new StringBuilder("{mentioned}").reverse().toString())));
                    if (inFormat.replace("{mentioned}", pl.getName()).equals(inFormat.replace("{mentioned}", n)) && !pings.contains(n)) {
                        pings.add(n);
                    }
                }
            }
        }
        Object[] pingArray = pings.toArray();
        for (Object v : pingArray) {
            if (v.equals(Name) && !v.equals(Sender)) {
                p.playSound(p.getLocation(), Sound.valueOf(pingSound), pingVolume, pingSpeed);
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(pingMessage));
                if (marker.equalsIgnoreCase("true")) {
                    outFormat = ReplaceMethods.player(outFormat, p, "mentioned");
                    Message = Message.replace(inFormat.replace("{mentioned}", v.toString()), markerColor + outFormat + messageColor);
                }
            }
            if (v.equals(Sender) && v.equals(Name)) {
                if (!visibility.equalsIgnoreCase("mentioned") && marker.equalsIgnoreCase("true")) {
                    outFormat = ReplaceMethods.player(outFormat, p, "mentioned");
                    Message = Message.replace(inFormat.replace("{mentioned}", v.toString()), markerColor + outFormat + messageColor);
                }
                if (myself.length() != 0) {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(myself));
                }
            }
            if (!visibility.equalsIgnoreCase("mentioned") && marker.equalsIgnoreCase("true")) {
                outFormat = ReplaceMethods.player(outFormat, p, "mentioned");
                Message = Message.replace(inFormat.replace("{mentioned}", v.toString()), markerColor + outFormat + messageColor);
            } else {
                outFormat = ReplaceMethods.player(outFormat, p, "mentioned");
                Message = Message.replace(inFormat.replace("{mentioned}", v.toString()), outFormat + messageColor);
            }
        }
        return ReplaceMethods.message(format, Message, messageColor);
    }
}