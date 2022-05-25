package tire.lightchat.commands;

import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import tire.lightchat.errorfinder.FatalErrors;
import tire.lightchat.main.LightChat;
import tire.lightchat.modules.ReplaceMethods;

public class ReloadConfig {
    private LightChat plugin;
    public ReloadConfig(LightChat plugin) {
        this.plugin = plugin;
    }

    public void reload(CommandSender sender) {

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
