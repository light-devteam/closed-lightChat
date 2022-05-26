package tire.lightchat.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import tire.lightchat.commands.ReloadConfig;

public class Commands implements CommandExecutor  {
    private LightChat plugin;
    public Commands(LightChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("lightchat")) {
            if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                ReloadConfig ReloadConfig = new ReloadConfig(plugin);
                ReloadConfig.reload(sender);
                return true;
            }
        }
        return false;
    }
}
