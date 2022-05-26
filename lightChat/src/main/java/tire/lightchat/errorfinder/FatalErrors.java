package tire.lightchat.errorfinder;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import java.util.Arrays;
import tire.lightchat.main.LightChat;

public class FatalErrors {
    private LightChat plugin;
    public FatalErrors(LightChat plugin) { this.plugin = plugin; }

    public void onEnableCheck() {

        String[] prefixes = new String[3];
        prefixes[0] = plugin.getConfig().getString("chats.globalChat.prefix"); // Глобальный чат
        prefixes[1] = plugin.getConfig().getString("chats.localChat.prefix"); // Локальный чат
        prefixes[2] = plugin.getConfig().getString("chats.worldChat.prefix"); // Мировой чат

        String[] enables = new String[3];
        enables[0] = plugin.getConfig().getString("chats.globalChat.enable");
        enables[1] = plugin.getConfig().getString("chats.localChat.enable");
        enables[2] = plugin.getConfig().getString("chats.worldChat.enable");

        String[] pings = new String[3];
        pings[0] = plugin.getConfig().getString("chats.globalChat.ping");
        pings[1] = plugin.getConfig().getString("chats.localChat.ping");
        pings[2] = plugin.getConfig().getString("chats.worldChat.ping");

        String inFormat = plugin.getConfig().getString("ping.inFormat");

        String placeholder = plugin.getConfig().getString("config.placeholder");
        String formatSymbol = plugin.getConfig().getString("config.formatSymbol");

        if ((prefixes[0].equals(prefixes[1]) && enables[0].equalsIgnoreCase("true") && enables[1].equalsIgnoreCase("true"))
                || (prefixes[1].equals(prefixes[2]) && enables[1].equalsIgnoreCase("true") && enables[2].equalsIgnoreCase("true"))
                || (prefixes[2].equals(prefixes[0]) && enables[2].equalsIgnoreCase("true") && enables[0].equalsIgnoreCase("true"))) {
            try {
                throw new IllegalArgumentException("Chat prefixes cannot be the same.");
            } finally {
                errorCheck();
            }
        }

        if(((enables[0].equalsIgnoreCase("true") && pings[0].equalsIgnoreCase("true"))
                || (enables[1].equalsIgnoreCase("true") && pings[1].equalsIgnoreCase("true"))
                || (enables[2].equalsIgnoreCase("true") && pings[2].equalsIgnoreCase("true")))
                && inFormat.indexOf(placeholder.replace("placeholder", "mentioned")) == -1) {
            try {
                throw new IllegalArgumentException("\"ping.inFormat\" must contain \"" + placeholder.replace("placeholder", "mentioned") + "\".");
            } finally {
                errorCheck();
            }
        }

        SoundError("ping.sound.sound");
        SoundError("private.sound.recipientSound");
        SoundError("private.sound.senderSound");

        if(placeholder.split("placeholder").length != 2) {
            try {
                throw new IllegalArgumentException("There must be only one \"placeholder\" substring in the field, and it must be wrapped on both sides in \"config.placeholder\". For example: \"{placeholder}\".");
            } finally {
                errorCheck();
            }
        }

        if(formatSymbol.length() == 0) {
            try {
                throw new IllegalArgumentException("Format symbol cannot be missing in \"config.formatSymbol\"");
            } finally {
                errorCheck();
            }
        }

        plugin.getLogger().info("The plugin has been launched successfully.");
    }

    public void SoundError(String cfg) {
        String s = plugin.getConfig().getString(cfg).trim().replace(" ", "_").toUpperCase();
        try {
            if (!Arrays.asList(Sound.values()).contains(Sound.valueOf(s)) && s.trim().length() != 0) {
                try {
                    throw new IllegalArgumentException("A non-existent sound is specified in \"" + cfg + "\"!");
                } finally {
                    errorCheck();
                }
            }
        } catch(Exception e) {
            try {
                throw new IllegalArgumentException("A non-existent sound is specified in \"" + cfg + "\"!");
            } finally {
                errorCheck();
            }
        }
    }

    public void errorCheck() {
        plugin.getLogger().info("The plugin was disabled due to errors.");
        Bukkit.getPluginManager().disablePlugin(plugin);
    }
}