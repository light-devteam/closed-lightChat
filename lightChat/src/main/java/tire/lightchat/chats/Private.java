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
import tire.lightchat.modules.sendMessage;

public class Private implements CommandExecutor {
    private LightChat plugin;
    public Private(LightChat plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        sendMessage sendMessage = new sendMessage(plugin);
        ReplaceMethods ReplaceMethods = new ReplaceMethods(plugin);

        if(args.length >= 2) {
            Player recipient = Bukkit.getPlayer(args[0]);
            String error = plugin.getConfig().getString("private.error").trim();
                error = ReplaceMethods.player(error, recipient, "recipient");
            try {
                String noPerms = plugin.getConfig().getString("private.noPerms").trim();
                    noPerms = ReplaceMethods.player(noPerms, (Player)sender, "sender");
                String myself = plugin.getConfig().getString("private.myself").trim();
                    myself = ReplaceMethods.unicode(myself);

                String senderFormat = plugin.getConfig().getString("private.senderFormat").trim();
                    senderFormat = ReplaceMethods.player(senderFormat, recipient, "recipient");
                String senderSound = plugin.getConfig().getString("private.sound.senderSound").trim().replace(" ", "_").toUpperCase();
                String recipientFormat = plugin.getConfig().getString("private.recipientFormat").trim();
                    recipientFormat = ReplaceMethods.player(recipientFormat, ((Player) sender), "sender");
                String message = plugin.getConfig().getString("private.message").trim();
                    message = ReplaceMethods.player(message, ((Player) sender), "sender");
                String recipientSound = plugin.getConfig().getString("private.sound.recipientSound").trim().replace(" ", "_").toUpperCase();

                Float soundVolume = Float.parseFloat(plugin.getConfig().getString("private.sound.volume").trim());
                Float soundSpeed = Float.parseFloat(plugin.getConfig().getString("private.sound.pitch").trim());

                if (sender instanceof Player) {
                    if (sender.hasPermission("lc.chat.Private.write")) {
                        if (recipient.isOnline()) {

                            StringBuilder sb = new StringBuilder();
                            for (int i = 1; i < args.length; i++) sb.append(args[i]).append(" ");
                            if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
                            String msg = sb.toString().trim();

                            if (recipient != sender) {
                                if(recipient.hasPermission("lc.chat.Private.see")) {
                                    if(senderFormat.length() != 0) {
                                        sendMessage.send((Player)sender, ReplaceMethods.message(senderFormat, msg));
                                    }
                                    if (senderSound.length() != 0) {
                                        ((Player)sender).playSound(((Player)sender).getLocation(), Sound.valueOf(senderSound), soundVolume, soundSpeed);
                                    }
                                    sendMessage.send(recipient, ReplaceMethods.message(recipientFormat, msg));
                                    if (recipientSound.length() != 0) {
                                        recipient.playSound(recipient.getLocation(), Sound.valueOf(recipientSound), soundVolume, soundSpeed);
                                    }
                                    recipient.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                                } else {

                                }
                                return true;
                            } else {
                                if (myself.length() != 0) {
                                    sendMessage.send((Player)sender, ReplaceMethods.message(myself, msg));
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
                    sendMessage.send((Player)sender, error);
                }
                return true;
            }
        }
        return false;
    }
}
