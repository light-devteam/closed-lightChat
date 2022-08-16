package ru.rtire.lightchat;

import java.util.*;
import java.lang.*;
import java.io.*;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

import ru.rtire.lightchat.chat.ChatListener;
import ru.rtire.lightchat.modules.MessageFormatter;
import ru.rtire.lightchat.modules.CommandRegister;

public final class LightChat extends JavaPlugin {

    public static Chat chat = null;
    public static Economy economy = null;
    private static LightChat instance;

    @Override
    public void onEnable() {
        instance = this;

        String placeholder = getConfig().getString("general.placeholder");
        MessageFormatter MessageFormatter = new MessageFormatter();

        File config = new File(getDataFolder() + File.separator + "src/main/resources/config.yml");
        if(!config.exists()) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }

        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);

        //getCommand("lightchat").setExecutor(new Commands(this));

        for (String Chat : new ArrayList<>(getConfig().getConfigurationSection("chats").getKeys(false))) {
            String prefix = getConfig().getString(String.format("chats.%s.prefix", Chat)).trim();
            String[] commands = getConfig().getString(String.format("chats.%s.commands", Chat)).trim().split(" ");
            String cmdUsage = getConfig().getString(String.format("chats.%s.cmdUsage", Chat)).trim();
                cmdUsage = cmdUsage.replace(placeholder.replace("placeholder", "chatPrefix"), prefix);
            String cmdDescription = getConfig().getString(String.format("chats.%s.cmdDescription", Chat)).trim();

            for (int i = 0; i < commands.length; i++) {
                CommandRegister.reg(this, new ru.rtire.lightchat.chat.Chat(Chat), new String[]{ commands[i].trim() }, MessageFormatter.unicode(cmdDescription), MessageFormatter.unicode(cmdUsage.replace(placeholder.replace("placeholder", "command"), commands[i].trim())));
            }
        }

        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            setupChat();
        }
    }

    // @Override
    // public void onDisable() {}

    public void setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if(economyProvider != null) {
            economy = economyProvider.getProvider();
        }
    }
    public boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);
        if (chatProvider != null) {
            chat = (Chat) chatProvider.getProvider();
        }
        return chat != null;
    }

    public static LightChat getInstance() { return instance; }
}
