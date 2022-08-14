package ru.rtire.lightchat.chat;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

import ru.rtire.lightchat.LightChat;
import ru.rtire.lightchat.modules.JSONConverter;

public class MessageSender {
    private LightChat plugin;
    public MessageSender() {
        this.plugin = LightChat.getInstance();;
    }

    public void send(Player p, String m) {
        p.spigot().sendMessage(new TextComponent(ComponentSerializer.parse(new JSONConverter().converter(m))));
    }
    public TextComponent transform(String m) {
        return new TextComponent(ComponentSerializer.parse(new JSONConverter().converter(m)));
    }
}
