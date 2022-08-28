package ru.rtire.lightchat.chat.modules;

import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

import ru.rtire.lightchat.LightChat;
import ru.rtire.lightchat.chat.modules.JSONConverter;

public class MessageSender {
    private LightChat plugin;
    public MessageSender() {
        this.plugin = LightChat.getInstance();;
    }

    public void sendToChat(Player p, String m) {
        p.spigot().sendMessage(transform(m));
    }
    public void sendToHotbar(Player p, String m) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, transform(m));
    }
    public TextComponent transform(String m) {
        return new TextComponent(ComponentSerializer.parse(new JSONConverter().converter(m)));
    }
}
