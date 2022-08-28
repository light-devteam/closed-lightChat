package ru.rtire.lightchat.chat.modules;

import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.Location;

import ru.rtire.lightchat.LightChat;
import ru.rtire.lightchat.utils.StringToMillis;
import ru.rtire.lightchat.utils.Random;

public class Automessage {

    private static LightChat plugin;

    static {
        plugin = LightChat.getInstance();
    }

    public static void start() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            List<String> Messages = plugin.getConfig().getStringList("automessage.messages");
            String Mode = plugin.getConfig().getString("automessage.mode").trim();
            if(Mode.length() <= 0) Mode = "contract";
            Long Delay = StringToMillis.Parse(plugin.getConfig().getString("automessage.delay"));
            Long ownDelay = Delay;
            int msgNum = 0;
            String Message = "";
            Location Locate = new Location(Bukkit.getWorld("world"), 0, 0, 0);

            while(plugin.isEnabled()) {

                if(Mode.equalsIgnoreCase("contract")) {
                    Message = Messages.get(msgNum).trim();
                    if(msgNum == Messages.size() - 1) msgNum = 0;
                    else msgNum++;
                } else if(Mode.equalsIgnoreCase("mixed")) {
                    Message = Messages.get(Random.generate(Messages.size() - 1)).trim();
                }

                Matcher m = Pattern.compile("((time|group):[\\w\\s]+;)|((chat):[\\w]+,[\\w]+,[-?\\d]+,[-?\\d]+,[-?\\d]+;)").matcher(Message);
                while (m.find()) {
                    String fullMatch = m.group(0);
                    fullMatch = fullMatch.substring(0, fullMatch.length() - 1);
                    String[] groups = fullMatch.split(":");
                    if(groups[0].equalsIgnoreCase("time")) {
                        ownDelay = StringToMillis.Parse(groups[1]);
                    }
                    if(groups[0].equalsIgnoreCase("chat")) {
                        // ...
                    }
                    if(groups[0].equalsIgnoreCase("group")) {
                        String groupName = groups[1];
                        List<String> groupMessages = plugin.getConfig().getStringList(String.format("automessage.groups.%s.messages", groupName));
                        Long groupDelay = StringToMillis.Parse(plugin.getConfig().getString(String.format("automessage.groups.%s.delay", groupName)));
                        String groupMode = plugin.getConfig().getString(String.format("automessage.groups.%s.mode", groupName));
                        ownDelay = 0L;
                    }

                }

                try {
                    Thread.sleep(ownDelay);
                    ownDelay = Delay;
                } catch(Exception e) {
                    e.printStackTrace();
                }
                Bukkit.broadcastMessage(Message);
            }
        });
    }

}
