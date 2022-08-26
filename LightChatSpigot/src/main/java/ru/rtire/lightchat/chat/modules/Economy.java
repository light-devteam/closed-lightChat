package ru.rtire.lightchat.chat.modules;

import org.bukkit.entity.Player;

import ru.rtire.lightchat.LightChat;
import ru.rtire.lightchat.api.ChatEvent;
import ru.rtire.lightchat.dependencies.Vault;

public class Economy {
    private static LightChat plugin;
    private static net.milkbowl.vault.economy.Economy economy;

    static {
        plugin = LightChat.getInstance();
        economy = Vault.economy;
    }

    public static boolean takeMoney() {
        Player p = ChatEvent.getSender();
        double amount = plugin.getConfig().getDouble(String.format("chats.%s.price", ChatEvent.getChat()));
        if (economy.getBalance(p) < amount) return false;
        return economy.withdrawPlayer(p, amount).transactionSuccess();
    }
}
