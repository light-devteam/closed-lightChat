package ru.rtire.lightchat.chat;

import java.io.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.text.SimpleDateFormat;
import java.util.*;

import net.milkbowl.vault.economy.Economy;

import ru.rtire.lightchat.LightChat;
import ru.rtire.lightchat.api.ChatEvent;
import ru.rtire.lightchat.modules.MessageFormatter;

public class Chat implements CommandExecutor {
    private LightChat plugin;
    private String Chat;
    private static Economy economy;

    public Chat () {
        this.plugin = LightChat.getInstance();
        economy = plugin.economy;
    }
    public Chat (String Chat) {
        this.plugin = LightChat.getInstance();
        this.Chat = Chat;
        economy = plugin.economy;
    }

    public void chatCaller (AsyncPlayerChatEvent e) {
        String Message = e.getMessage();
        Player Sender = e.getPlayer();
        String SenderNickname = Sender.getName();
        chatDefinition(Message, Sender, SenderNickname);
    }
    public void chatCaller (String Message, String SenderNickname) {
        chatDefinition(Message, null, SenderNickname);
    }

    private void chatDefinition (String Message, Player Sender, String SenderNickname) {
        MessageFormatter MessageFormatter = new MessageFormatter();
        MessageSender MessageSender = new MessageSender();

        String prefixErrorDisplay = MessageFormatter.player(plugin.getConfig().getString("general.chat.prefixError.display").trim(), Sender, "sender");
        String prefixError = MessageFormatter.player(plugin.getConfig().getString("general.chat.prefixError.message").trim(), Sender, "sender");

        HashMap<String, String> Prefixes = new HashMap<>();
        for (String s : new ArrayList<>(plugin.getConfig().getConfigurationSection("chats").getKeys(false))) {
            Prefixes.put(s, plugin.getConfig().getString(String.format("chats.%s.prefix", s)));
        }
        for (Map.Entry<String, String> entry : Prefixes.entrySet()) {
            String Chat = entry.getKey();
            String Prefix = entry.getValue();
            if (ChatEvent.getMessage().startsWith(Prefix)) {
                Message = Message.substring(Prefix.length(), Message.length());
                if (Sender != null) {
                    sendingMessage(Message.trim(), Sender, SenderNickname, Chat);
                }
                return;
            }
        }
        if (prefixError.length() > 0) {
            if (prefixErrorDisplay.equalsIgnoreCase("hotbar")) {
                MessageSender.sendToHotbar(Sender, prefixError);
            }
            else if (prefixErrorDisplay.equalsIgnoreCase("chat")) {
                MessageSender.sendToChat(Sender, prefixError);
            }
        }
    }

    public boolean onCommand (CommandSender Sender, Command cmd, String label, String[] args) {
        if (Sender instanceof Player) {
            String noPermsCommand = new MessageFormatter().player(plugin.getConfig().getString(String.format("chats.%s.noPermsCommand")).trim(), (Player) Sender, "sender");
            if (Sender.hasPermission(String.format("lc.chat.%s.command", this.Chat))) {
                if (args.length >= 1) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < args.length; i++) sb.append(args[i]).append(" ");
                    if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
                    String Message = sb.toString().trim();
                    Player PlayerSender = (Player) Sender;
                    String SenderNickname = PlayerSender.getName();

                    sendingMessage(Message.trim(), PlayerSender, SenderNickname, this.Chat);
                } else {
                    return false;
                }
            } else {
                new MessageSender().sendToChat((Player) Sender, noPermsCommand);
            }
        } else {
            plugin.getLogger().warning("Unable to send message from console.");
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
        Float Distance = Float.parseFloat(plugin.getConfig().getString(String.format("chats.%s.distance", Chat)).trim());

        Boolean Mentions = plugin.getConfig().getBoolean(String.format("chats.%s.mentions", Chat));
        Boolean Log = plugin.getConfig().getBoolean(String.format("chats.%s.fileLog.enable", Chat));
        Boolean Console = plugin.getConfig().getBoolean(String.format("chats.%s.console", Chat));

        int Cooldown = plugin.getConfig().getInt(String.format("chats.%s.cooldown", Chat));
        double Price = plugin.getConfig().getDouble(String.format("chats.%s.price", Chat));

        String noPerms = MessageFormatter.player(plugin.getConfig().getString(String.format("chats.%s.noPerms", Chat)).trim(), Sender, "sender");
        String notCooledDown = MessageFormatter.player(plugin.getConfig().getString(String.format("chats.%s.notCooledDown", Chat)).trim(), Sender, "sender");
        String noPermsMention = MessageFormatter.player(plugin.getConfig().getString(String.format("chats.%s.noPermsMention", Chat)).trim(), Sender, "sender");
        String noPermsCommand = MessageFormatter.player(plugin.getConfig().getString(String.format("chats.%s.noPermsCommand", Chat)).trim(), Sender, "sender");
        String notEnoughMoney = MessageFormatter.player(plugin.getConfig().getString(String.format("chats.%s.notEnoughMoney", Chat)).trim(), Sender, "sender");
            notEnoughMoney = MessageFormatter.placeholderReplacement(notEnoughMoney, "price", Double.toString(Price));
        String successfullyPaid = MessageFormatter.player(plugin.getConfig().getString(String.format("chats.%s.successfullyPaid", Chat)).trim(), Sender, "sender");
            successfullyPaid = MessageFormatter.placeholderReplacement(successfullyPaid, "price", Double.toString(Price));

        Boolean transaction = true;
        if(this.economy != null) {
            notEnoughMoney = MessageFormatter.placeholderReplacement(notEnoughMoney, "senderBalance", Double.toString(economy.getBalance(Sender)));
            transaction = takeMoney(Sender, Price);
            successfullyPaid = MessageFormatter.placeholderReplacement(successfullyPaid, "senderBalance", Double.toString(economy.getBalance(Sender)));
        }
        if(transaction) {
            if(successfullyPaid.length() > 0 && Price > 0) {
                MessageSender.sendToChat(Sender, successfullyPaid);
            }
            if (Sender.hasPermission(String.format("lc.chat.%s.write", Chat))) {
                ArrayList<Player> recipients = recipientsList(Distance, Sender, Chat);
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
                if(Log) {
                    String LogFormat = MessageFormatter.player(plugin.getConfig().getString(String.format("chats.%s.fileLog.format", Chat)).trim(), Sender, "sender", true);
                    LogFormat = MessageFormatter.message(LogFormat, Message);
                    new Chat().Logger(Chat, LogFormat, Log);
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

    public void Logger(String Chat, String Message, Boolean Log) {
        try {
            if (Log) {
                Calendar calendar = new GregorianCalendar();
                SimpleDateFormat DateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat TimeFormatter = new SimpleDateFormat("HH:mm:ss:SSSS");
                String TZone = plugin.getConfig().getString(String.format("general.timeZone")).trim();
                calendar.setTimeZone(TimeZone.getTimeZone(TZone));

                String location = LightChat.getJarDirectory();
                File dir = new File(location + File.separator + plugin.getDescription().getName() + File.separator + "logs");
                File file = new File(dir + File.separator + DateFormatter.format(calendar.getTime()) + ".log");
                File chatsDir = new File(dir + File.separator + "chats" + File.separator + Chat);
                File chatFile = new File(chatsDir + File.separator + DateFormatter.format(calendar.getTime()) + ".log");

                Boolean logToSepFiles = plugin.getConfig().getBoolean("general.chat.logToSepFiles");

                Message = new MessageFormatter().placeholderReplacement(Message, "time", TimeFormatter.format(calendar.getTime())) + "\n";

                if (dir.exists() && file.exists()) {
                    BufferedWriter allChatsLogFile = new BufferedWriter(new FileWriter(file, true));

                    allChatsLogFile.write(Message);

                    if(logToSepFiles) {
                        if(chatsDir.exists() && chatFile.exists()) {
                            BufferedWriter chatLogFile = new BufferedWriter(new FileWriter(chatFile, true));

                            chatLogFile.write(Message);

                            chatLogFile.flush();
                            chatLogFile.close();
                        } else {
                            new LightChat().setupLogFile(dir, file);
                            Logger(Chat, Message, Log);
                        }
                    }
                    allChatsLogFile.flush();
                    allChatsLogFile.close();
                } else {
                    new LightChat().setupLogFile(dir, file);
                    Logger(Chat, Message, Log);
                }
            }
            return;
        } catch(Exception e) {}
    }


    public ArrayList recipientsList(Float Distance, Player Sender, String Chat) {
        MessageFormatter MessageFormatter = new MessageFormatter();
        MessageSender MessageSender = new MessageSender();

        ArrayList<Player> recipients = new ArrayList<Player>();

        String noOneHeardDisplay = MessageFormatter.player(plugin.getConfig().getString("general.chat.noOneHeard.display").trim(), Sender, "sender");
        String noOneHeard = MessageFormatter.player(plugin.getConfig().getString("general.chat.noOneHeard.message").trim(), Sender, "sender");

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
        return recipients;
    }

    public static boolean takeMoney(Player p, double amount) {
        if (economy.getBalance(p) < amount) return false;
        return economy.withdrawPlayer(p, amount).transactionSuccess();
    }
}