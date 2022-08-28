package ru.rtire.lightchat.chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;

import ru.rtire.lightchat.LightChat;
import ru.rtire.lightchat.chat.modules.Mentions;
import ru.rtire.lightchat.chat.modules.MessageFormatter;
import ru.rtire.lightchat.chat.modules.MessageSender;
import ru.rtire.lightchat.chat.modules.Logger;
import ru.rtire.lightchat.chat.modules.Cooldown;
import ru.rtire.lightchat.api.ChatEvent;
import ru.rtire.lightchat.dependencies.Vault;

public class Chat implements CommandExecutor {
    private LightChat plugin;
    private String Chat;

    public Chat () {
        this.plugin = LightChat.getInstance();
    }
    public Chat (String Chat) {
        this.plugin = LightChat.getInstance();
        this.Chat = Chat;
    }

    public void chatCaller () {
        String Message = ChatEvent.getMessage();
        Player Sender = ChatEvent.getSender();
        String SenderNickname = Sender.getName();
        String Chat = ChatDefinition.definition();
        if (Sender != null) {
            if(Chat != null) {
                MessageFormatter MessageFormatter = new MessageFormatter();
                MessageSender MessageSender = new MessageSender();
                String CooldownDisplay = plugin.getConfig().getString("general.chat.cooldown.display");
                String notCooledDown = MessageFormatter.player(plugin.getConfig().getString(String.format("chats.%s.cooldown.notCooledDown", Chat)).trim(), Sender, "sender");
                if(Cooldown.cooldown() <= 0) {
                    String Prefix = plugin.getConfig().getString(String.format("chats.%s.prefix", Chat)).trim();
                    Message = Message.substring(Prefix.length(), Message.length());
                    if(Message.length() > 0) {
                        sendingMessage(Message.trim(), Sender, SenderNickname, Chat);
                    }
                } else {
                    if (notCooledDown.length() > 0) {
                        notCooledDown = MessageFormatter.placeholderReplacement(notCooledDown, "cdTime", Cooldown.cooldown().toString());
                        if (CooldownDisplay.equalsIgnoreCase("hotbar")) {
                            MessageSender.sendToHotbar(Sender, notCooledDown);
                        } else if (CooldownDisplay.equalsIgnoreCase("chat")) {
                            MessageSender.sendToChat(Sender, notCooledDown);
                        }
                    }
                }
            } else {
                MessageFormatter MessageFormatter = new MessageFormatter();
                MessageSender MessageSender = new MessageSender();
                String prefixErrorDisplay = MessageFormatter.player(plugin.getConfig().getString("general.chat.prefixError.display").trim(), Sender, "sender");
                String prefixError = MessageFormatter.player(plugin.getConfig().getString("general.chat.prefixError.message").trim(), Sender, "sender");

                if (prefixError.length() > 0) {
                    if (prefixErrorDisplay.equalsIgnoreCase("hotbar")) {
                        MessageSender.sendToHotbar(Sender, prefixError);
                    } else if (prefixErrorDisplay.equalsIgnoreCase("chat")) {
                        MessageSender.sendToChat(Sender, prefixError);
                    }
                }
            }
        } else {
            // ...
        }
    }
    public void chatCaller (String Message, String SenderNickname) {
        ChatDefinition.definition();
    }

    public boolean onCommand (CommandSender Sender, Command cmd, String label, String[] args) {
        if (Sender instanceof Player) {
            String noPermsCommand = new MessageFormatter().player(plugin.getConfig().getString(String.format("chats.%s.noPermsCommand")).trim(), (Player) Sender, "sender");
            if (Sender.hasPermission(String.format("lc.chat.%s.command", this.Chat))) {
                if (args.length >= 1) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < args.length; i++) sb.append(args[i]).append(" ");
                    String Message = sb.toString().trim();
                    Player PlayerSender = (Player) Sender;

                    ChatEvent.setAll(null, Message, PlayerSender);

                    String SenderNickname = ChatEvent.getSenderNickname();

                    if(Message.length() > 0) {
                        sendingMessage(Message.trim(), PlayerSender, SenderNickname, this.Chat);
                    }
                } else {
                    return false;
                }
            } else {
                new MessageSender().sendToChat((Player) Sender, noPermsCommand);
            }
        } else {
            plugin.getLogger().warning("Unable to send message from console.");
            // ...
        }
        return true;
    }

    private void sendingAnEmulatedMessage (String Message, String SenderNickname, String Chat) {
        // ...
    }

    private void sendingMessage (String Message, Player Sender, String SenderNickname, String Chat) {
        MessageFormatter MessageFormatter = new MessageFormatter();
        MessageSender MessageSender = new MessageSender();

        String Format = MessageFormatter.player(plugin.getConfig().getString(String.format("chats.%s.format", Chat)).trim(), Sender, "sender");
        String Color = MessageFormatter.unicode(plugin.getConfig().getString(String.format("chats.%s.color", Chat)).trim());
        String Nickname = SenderNickname;
        String DisplayNickname = Sender.getDisplayName();

        Boolean Mentions = plugin.getConfig().getBoolean(String.format("chats.%s.mentions", Chat));
        Boolean fileLog = plugin.getConfig().getBoolean(String.format("chats.%s.fileLog.enable", Chat));
        Boolean consoleLog = plugin.getConfig().getBoolean(String.format("chats.%s.consoleLog.enable", Chat));

        double Price = plugin.getConfig().getDouble(String.format("chats.%s.economy.price", Chat));

        String noPerms = MessageFormatter.player(plugin.getConfig().getString(String.format("chats.%s.noPerms", Chat)).trim(), Sender, "sender");
        String noPermsMention = MessageFormatter.player(plugin.getConfig().getString(String.format("chats.%s.noPermsMention", Chat)).trim(), Sender, "sender");
        String noPermsCommand = MessageFormatter.player(plugin.getConfig().getString(String.format("chats.%s.noPermsCommand", Chat)).trim(), Sender, "sender");
        String notEnoughMoney = MessageFormatter.player(plugin.getConfig().getString(String.format("chats.%s.economy.notEnoughMoney", Chat)).trim(), Sender, "sender");
            notEnoughMoney = MessageFormatter.placeholderReplacement(notEnoughMoney, "price", Double.toString(Price));
        String successfullyPaid = MessageFormatter.player(plugin.getConfig().getString(String.format("chats.%s.economy.successfullyPaid", Chat)).trim(), Sender, "sender");
            successfullyPaid = MessageFormatter.placeholderReplacement(successfullyPaid, "price", Double.toString(Price));

        Boolean transaction = true;
        if(Vault.economy != null) {
            notEnoughMoney = MessageFormatter.placeholderReplacement(notEnoughMoney, "senderBalance", Double.toString(Vault.economy.getBalance(Sender)));
            transaction = Vault.takeMoney();
            successfullyPaid = MessageFormatter.placeholderReplacement(successfullyPaid, "senderBalance", Double.toString(Vault.economy.getBalance(Sender)));
        }
        if(transaction) {
            if(successfullyPaid.length() > 0 && Price > 0) {
                MessageSender.sendToChat(Sender, successfullyPaid);
            }
            if (Sender.hasPermission(String.format("lc.chat.%s.write", Chat))) {
                ChatEvent.setRecipients();
                ArrayList<Player> recipients = ChatEvent.getRecipients();

                String noOneHeardDisplay = MessageFormatter.player(plugin.getConfig().getString("general.chat.noOneHeard.display").trim(), Sender, "sender");
                String noOneHeard = MessageFormatter.player(plugin.getConfig().getString("general.chat.noOneHeard.message").trim(), Sender, "sender");
                if (recipients.size() <= 1) {
                    if (noOneHeard.length() > 0) {
                        if (noOneHeardDisplay.equalsIgnoreCase("hotbar")) {
                            MessageSender.sendToHotbar(Sender, noOneHeard);
                        }
                        else if (noOneHeardDisplay.equalsIgnoreCase("chat")) {
                            MessageSender.sendToChat(Sender, noOneHeard);
                        }
                    }
                }

                if (Mentions) {
                    if (Sender.hasPermission(String.format("lc.chat.%s.mention", Chat))) {
                        for (Player p : recipients) {
                            MessageSender.sendToChat(p, new Mentions().pingEvent(Message, Nickname, p, Format, Color));
                        }
                    } else {
                        if (noPermsMention.length() > 0) {
                            MessageSender.sendToChat(Sender, noPermsMention);
                        }
                        for (Player p : recipients) {
                            MessageSender.sendToChat(p, MessageFormatter.message(Format, Message, Color));
                        }
                    }
                } else {
                    for (Player p : recipients) {
                        MessageSender.sendToChat(p, MessageFormatter.message(Format, Message, Color));
                    }
                }
                if(fileLog) {
                    String LogFormat = MessageFormatter.player(plugin.getConfig().getString(String.format("chats.%s.fileLog.format", Chat)).trim(), Sender, "sender", true);
                    LogFormat = MessageFormatter.message(LogFormat, Message);
                    Logger.fileLogger(LogFormat);
                }
                if(consoleLog) {
                    String LogFormat = MessageFormatter.player(plugin.getConfig().getString(String.format("chats.%s.consoleLog.format", Chat)).trim(), Sender, "sender", true);
                    LogFormat = MessageFormatter.message(LogFormat, Message);
                    Logger.consoleLogger(LogFormat);
                }
            } else {
                if (noPerms.length() > 0) {
                    MessageSender.sendToChat(Sender, noPerms);
                }
            }
        } else {
            if(notEnoughMoney.length() > 0 && Price > 0) {
                MessageSender.sendToChat(Sender, notEnoughMoney);
            }
        }
    }
}