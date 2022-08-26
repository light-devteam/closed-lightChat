package ru.rtire.lightchat.chat.modules;

import org.bukkit.Bukkit;
import ru.rtire.lightchat.LightChat;
import ru.rtire.lightchat.api.ChatEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Logger {
    private static LightChat plugin;

    static {
        plugin = LightChat.getInstance();
    }

    public static void fileLogger(String LogFormat) {
        try {
            Calendar calendar = new GregorianCalendar();
            SimpleDateFormat DateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat TimeFormatter = new SimpleDateFormat("HH:mm:ss:SSSS");
            String TZone = plugin.getConfig().getString("general.timeZone").trim();
            TimeFormatter.setTimeZone(TimeZone.getTimeZone(TZone));
            DateFormatter.setTimeZone(TimeZone.getTimeZone(TZone));

            String location = LightChat.getJarDirectory();
            File dir = new File(location + File.separator + plugin.getDescription().getName() + File.separator + "logs");
            File file = new File(dir + File.separator + DateFormatter.format(calendar.getTime()) + ".txt");
            File chatsDir = new File(dir + File.separator + "chats" + File.separator + ChatEvent.getChat());
            File chatFile = new File(chatsDir + File.separator + DateFormatter.format(calendar.getTime()) + ".txt");

            Boolean logToSepFiles = plugin.getConfig().getBoolean("general.chat.logToSepFiles");

            LogFormat = new MessageFormatter().placeholderReplacement(LogFormat, "time", TimeFormatter.format(calendar.getTime())) + "\n";

            if (dir.exists() && file.exists()) {
                BufferedWriter allChatsLogFile = new BufferedWriter(new FileWriter(file, true));

                allChatsLogFile.write(LogFormat);

                if(logToSepFiles) {
                    if(chatsDir.exists() && chatFile.exists()) {
                        BufferedWriter chatLogFile = new BufferedWriter(new FileWriter(chatFile, true));

                        chatLogFile.write(LogFormat);

                        chatLogFile.flush();
                        chatLogFile.close();
                    } else {
                        setupFile(chatsDir, chatFile);
                        fileLogger(LogFormat);
                    }
                }
                allChatsLogFile.flush();
                allChatsLogFile.close();
            } else {
                setupFile(dir, file);
                fileLogger(LogFormat);
            }
            return;
        } catch(Exception e) {}
    }

    public static void consoleLogger(String LogFormat) {
        try {
            Calendar calendar = new GregorianCalendar();
            SimpleDateFormat TimeFormatter = new SimpleDateFormat("HH:mm:ss:SSSS");
            String TZone = plugin.getConfig().getString(String.format("general.timeZone")).trim();
            TimeFormatter.setTimeZone(TimeZone.getTimeZone(TZone));

            LogFormat = new MessageFormatter().placeholderReplacement(LogFormat, "time", TimeFormatter.format(calendar.getTime()));

            Bukkit.getLogger().info(LogFormat);
        } catch(Exception e) {}
    }

    public static void setupFile(File dir, File file) {
        try {
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch(Exception e) {}
    }
}
