package ru.rtire.lightchat.dependencies;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.entity.Player;

import ru.rtire.lightchat.LightChat;
import ru.rtire.lightchat.api.ChatEvent;

public class Vault {

    private static LightChat plugin;
    public static Chat chat = null;
    public static Economy economy = null;

    static {
        plugin = LightChat.getInstance();
    }

    public static boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if(economyProvider != null) {
            economy = (Economy) economyProvider.getProvider();
        }
        return economy != null;
    }
    public static boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = plugin.getServer().getServicesManager().getRegistration(Chat.class);
        if (chatProvider != null) {
            chat = (Chat) chatProvider.getProvider();
        }
        return chat != null;
    }

    public static boolean takeMoney() {
        Player p = ChatEvent.getSender();
        double amount = plugin.getConfig().getDouble(String.format("chats.%s.economy.price", ChatEvent.getChat()));
        if (economy.getBalance(p) < amount) return false;
        return economy.withdrawPlayer(p, amount).transactionSuccess();
    }

    public String getPlayerPrefix(Player p) {
        return chat.getPlayerPrefix(p);
    }
    public String getPlayerSuffix(Player p) {
        return chat.getPlayerSuffix(p);
    }
    public String getSenderPrefix() {
        return chat.getPlayerPrefix(ChatEvent.getSender());
    }
    public String getSenderSuffix() {
        return chat.getPlayerSuffix(ChatEvent.getSender());
    }
}
