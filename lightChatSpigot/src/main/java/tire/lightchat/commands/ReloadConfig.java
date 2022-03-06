package tire.lightchat.commands;

import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import tire.lightchat.errorfinder.FatalErrors;
import tire.lightchat.main.LightChat;

public class ReloadConfig {
    private LightChat plugin;
    public ReloadConfig(LightChat plugin) {
        this.plugin = plugin;
    }

    public void reload(CommandSender sender) {
        String success = plugin.getConfig().getString("reload.success").replace("&", "\u00a7").replace("{sender}", ((Player)sender).getName());
        String noPerms = plugin.getConfig().getString("reload.noPerms").replace("&", "\u00a7").replace("{sender}", ((Player)sender).getName());
        String error = plugin.getConfig().getString("reload.error").replace("&", "\u00a7");

        if(sender.hasPermission("lchat.reload")) {
            try {
                plugin.reloadConfig();

                new FatalErrors(plugin).onEnableCheck();

                sender.sendMessage(success);
            }
            catch(Exception e) {
                sender.sendMessage(error);
                sender.sendMessage(e.getMessage());
                plugin.getLogger().info(e.getMessage());
            }
        }
        else {
            sender.sendMessage(noPerms);
        }
    }
}
