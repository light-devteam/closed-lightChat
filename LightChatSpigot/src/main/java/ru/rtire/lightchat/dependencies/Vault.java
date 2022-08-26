package ru.rtire.lightchat.dependencies;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

import ru.rtire.lightchat.LightChat;

public class Vault {

    public static LightChat plugin;
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
}
