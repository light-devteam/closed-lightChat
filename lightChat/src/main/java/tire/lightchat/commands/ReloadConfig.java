package tire.lightchat.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tire.lightchat.errorfinder.FatalErrors;
import tire.lightchat.main.LightChat;
import tire.lightchat.modules.ReplaceMethods;
import tire.lightchat.modules.sendMessage;

public class ReloadConfig {
    private LightChat plugin;
    public ReloadConfig(LightChat plugin) {
        this.plugin = plugin;
    }

    public void reload(CommandSender sender) {

        sendMessage sendMessage = new sendMessage(plugin);
        ReplaceMethods ReplaceMethods = new ReplaceMethods(plugin);

        String success = plugin.getConfig().getString("reload.success");
            success = ReplaceMethods.player(success, (Player)sender, "sender");
        String noPerms = plugin.getConfig().getString("reload.noPerms");
            noPerms = ReplaceMethods.player(noPerms, (Player)sender, "sender");
        String error = plugin.getConfig().getString("reload.error");
            error = ReplaceMethods.unicode(error);

        if(sender.hasPermission("lchat.reload")) {
            try {
                plugin.reloadConfig();

                new FatalErrors(plugin).onEnableCheck();

                sendMessage.send((Player)sender, success);
            }
            catch(Exception e) {
                sendMessage.send((Player)sender, error);
                sendMessage.send((Player)sender, e.getMessage());
                plugin.getLogger().info(e.getMessage());
            }
        }
        else {
            sendMessage.send((Player)sender, noPerms);
        }
    }
}
