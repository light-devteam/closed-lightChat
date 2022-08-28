package ru.rtire.lightchat;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.text.SimpleDateFormat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

import ru.rtire.lightchat.chat.ChatListener;
import ru.rtire.lightchat.chat.modules.MessageFormatter;
import ru.rtire.lightchat.chat.modules.Logger;
import ru.rtire.lightchat.utils.CommandRegister;
import ru.rtire.lightchat.dependencies.Vault;

public final class LightChat extends JavaPlugin {

    private static LightChat instance;
    private static String JarDirectory;

    @Override
    public void onEnable() {
        instance = this;

        MessageFormatter MessageFormatter = new MessageFormatter();

        String placeholder = getConfig().getString("general.placeholder");
        Boolean logToSepFiles = getConfig().getBoolean("general.chat.logToSepFiles");
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat DateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String TZone = getConfig().getString("general.timeZone").trim();
        DateFormatter.setTimeZone(TimeZone.getTimeZone(TZone));

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

            Boolean Log = getConfig().getBoolean(String.format("chats.%s.fileLog.enable", Chat));

            if(Log) {
                try {
                    String location = new File(LightChat.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();
                    JarDirectory = location;
                    File dir = new File(location + File.separator + getDescription().getName() + File.separator + "logs");
                    File file = new File(dir + File.separator + DateFormatter.format(calendar.getTime()) + ".txt");
                    File chatsDir = new File(dir + File.separator + "chats" + File.separator + Chat);
                    File chatFile = new File(chatsDir + File.separator + DateFormatter.format(calendar.getTime()) + ".txt");

                    Logger.setupFile(dir, file);
                    if(logToSepFiles) {
                        Logger.setupFile(chatsDir, chatFile);
                    }
                } catch(Exception e) {
                    System.out.println(e);
                }
            }

            for (int i = 0; i < commands.length; i++) {
                CommandRegister.reg(this, new ru.rtire.lightchat.chat.Chat(Chat), new String[]{ commands[i].trim() }, MessageFormatter.unicode(cmdDescription), MessageFormatter.unicode(cmdUsage.replace(placeholder.replace("placeholder", "command"), commands[i].trim())));
            }
        }

        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            Vault.setupEconomy();
            Vault.setupChat();
        }
    }

    @Override
    public void onDisable() {}

    public static LightChat getInstance() { return instance; }
    public static String getJarDirectory() { return JarDirectory; }
}
