package ru.rtire.lightchat;

import java.util.*;
import java.lang.*;
import java.io.*;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.chat.Chat;

import ru.rtire.lightchat.chat.ChatListener;

public final class LightChat extends JavaPlugin {

    public static Chat chat = null;
    private static LightChat instance;

    @Override
    public void onEnable() {
        instance = this;

        File config = new File(getDataFolder() + File.separator + "src/main/resources/config.yml");
        if(!config.exists()) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }

        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            setupChat();
        }
    }

    // @Override
    // public void onDisable() {}

    public boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);
        if (chatProvider != null) {
            chat = (Chat) chatProvider.getProvider();
        }
        return chat != null;
    }

    public static LightChat getInstance() { return instance; }
}
