package tire.lightchat.chats;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tire.lightchat.main.LightChat;
import tire.lightchat.modules.ReplaceMethods;

public class Private implements CommandExecutor {
    private LightChat plugin;
    public Private(LightChat plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length >= 2) {
            Player recipient = Bukkit.getPlayer(args[0]);
            String error = plugin.getConfig().getString("private.error").trim().replace("&", "\u00a7").replace("{recipient}", args[0]);
            try {
                ReplaceMethods ReplaceMethods = new ReplaceMethods(plugin);
                String noPerms = plugin.getConfig().getString("private.noPerms").trim().replace("&", "\u00a7");
                String myself = plugin.getConfig().getString("private.myself").trim().replace("&", "\u00a7");

                String senderFormat = plugin.getConfig().getString("private.senderFormat").trim().replace("&", "\u00a7");
                    senderFormat = ReplaceMethods.player(senderFormat, recipient, "recipient");
                String senderSound = plugin.getConfig().getString("private.sound.senderSound").trim().replace(" ", "_").toUpperCase();
                String recipientFormat = plugin.getConfig().getString("private.recipientFormat").trim().replace("&", "\u00a7");
                    recipientFormat = ReplaceMethods.player(recipientFormat, ((Player) sender), "sender");
                String recipientSound = plugin.getConfig().getString("private.sound.recipientSound").trim().replace(" ", "_").toUpperCase();

                Float soundVolume = Float.parseFloat(plugin.getConfig().getString("private.sound.volume").trim());
                Float soundSpeed = Float.parseFloat(plugin.getConfig().getString("private.sound.pitch").trim());
                String soundMessage = plugin.getConfig().getString("private.sound.message").trim().replace("&", "\u00a7");
                    soundMessage = ReplaceMethods.player(soundMessage, ((Player) sender), "sender");

                if (sender instanceof Player) {
                    if (sender.hasPermission("lc.private")) {
                        if (recipient.isOnline()) {
                            if (recipient != sender) {
                                StringBuilder sb = new StringBuilder();
                                for (int i = 1; i < args.length; i++) sb.append(args[i]).append(" ");
                                if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
                                String msg = sb.toString().trim();

                                sender.sendMessage(senderFormat.replace("{message}", msg));
                                if (senderSound.length() != 0) {
                                    ((Player) sender).playSound(((Player) sender).getLocation(), Sound.valueOf(senderSound), soundVolume, soundSpeed);
                                }
                                recipient.sendMessage(recipientFormat.replace("{message}", msg));
                                if (recipientSound.length() != 0) {
                                    recipient.playSound(recipient.getLocation(), Sound.valueOf(recipientSound), soundVolume, soundSpeed);
                                }
                                recipient.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(soundMessage));

                                return true;
                            } else {
                                if (myself.length() != 0) {
                                    sender.sendMessage(myself);
                                }
                                return true;
                            }
                        }
                    } else {
                        if (noPerms.length() != 0) {
                            sender.sendMessage(noPerms);
                        }
                        return true;
                    }
                }
                else {
                    plugin.getLogger().info("Unable to send private message from console.");
                }
            } catch (Exception e) {
                if (error.length() != 0) {
                    sender.sendMessage(error);
                }
                return true;
            }
        }
        return false;
    }
}
