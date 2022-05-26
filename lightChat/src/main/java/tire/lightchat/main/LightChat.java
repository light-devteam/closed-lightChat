package tire.lightchat.main;

import java.util.*;
import java.lang.*;
import java.io.*;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.plugin.RegisteredServiceProvider;

import tire.lightchat.errorfinder.FatalErrors;
import tire.lightchat.main.Commands;
import tire.lightchat.chats.Private;
import tire.lightchat.main.CommandRegister;

public final class LightChat extends JavaPlugin {

    public static Chat chat = null;

    @Override
    public void onEnable() {

        File config = new File(getDataFolder() + File.separator + "src/main/resources/config.yml");
        if(!config.exists()) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }

        Bukkit.getPluginManager().registerEvents(new Handler(this), this);

        new FatalErrors(this).onEnableCheck();

        getCommand("lightchat").setExecutor(new Commands(this));

        String[] commands = getConfig().getString("private.commands").split(",");
        for(int i = 0; i < commands.length; i++) {
            CommandRegister.reg(this, new Private(this), new String[] { commands[i].trim() }, "Send private message.", "Use /" + commands[i].trim() + " <recipient> <message>");
        }
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            setupChat();
        }
    }

    @Override
    public void onDisable() {}

    public boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);
        if (chatProvider != null) {
            chat = (Chat) chatProvider.getProvider();
        }
        return chat != null;
    }
}