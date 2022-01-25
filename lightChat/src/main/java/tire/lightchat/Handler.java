package tire.lightchat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import org.bukkit.Bukkit;

import tire.modules.replaceVarsNUnicode;

public class Handler implements Listener {
    private LightChat plugin;
    public Handler(LightChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        String Message = e.getMessage();
        Player Player = e.getPlayer();
        String PlayerName = Player.getName();

        //Префиксы
        String[] prefixes  = new String[3];
        prefixes[0] = plugin.getConfig().getString("globalChat.prefix"); // Глобальный чат
        prefixes[1] = plugin.getConfig().getString("localChat.prefix"); // Локальный чат
        prefixes[2] = plugin.getConfig().getString("worldChat.prefix"); // Мировой чат

        String prefixError = plugin.getConfig().getString("prefixError");

        //Режим вкл/выкл
        String[] enables  = new String[3];
        enables[0] = plugin.getConfig().getString("globalChat.enable"); // Глобальный чат
        enables[1] = plugin.getConfig().getString("localChat.enable"); // Локальный чат
        enables[2] = plugin.getConfig().getString("worldChat.enable"); // Мировой чат

        String MessageZero = Character.toString(Message.charAt(0));
        Message = Message.substring(1).trim();

        // Проверка на уникальность каждого префикса
        if(prefixes[0].equals(prefixes[1]) || prefixes[1].equals(prefixes[2]) || prefixes[2].equals(prefixes[0])) {
            plugin.getLogger().info("[LightChat] The plugin was disabled due to a bug: chat prefixes cannot be the same.");
            Bukkit.getPluginManager().disablePlugin(plugin);
            return;
        }
        else {
            if(Message != "") {
                // Проверка на свовпадение первого символа сообщения с одним из префиксов
                for (int i = 0; i < 3; i++) {
                    if (enables[i].equalsIgnoreCase("true") && prefixes[i].equals(MessageZero)) {
                        callChatClass(i, Message);
                        Player.sendMessage(Integer.toString(i));
                        return;
                    }
                }
                // Если не совпало, то проверяем на совпадение
                // одного из префиксов с пустой строкой
                for (int i = 0; i < 3; i++) {
                    if (enables[i].equalsIgnoreCase("true") && prefixes[i].equals("")) {
                        callChatClass(i, Message); // При первом же совпадении вызываем соответствующий класс
                        Player.sendMessage(Integer.toString(i));
                        return;
                    } else {             // Иначе просто пишем игроку сообщение об ошибке
                        Player.sendMessage(replaceVarsNUnicode.customReplace(prefixError, PlayerName,
                                MessageZero, prefixes[0],
                                prefixes[1], prefixes[2]));
                        return;
                    }
                }
            }
        }
    }

    public void callChatClass(int id, String Message) {
        switch (id) {
            case (0):
                //code
                break;
            case (1):
                //code
                break;
            case (2):
                //code
                break;
        }
    }
}