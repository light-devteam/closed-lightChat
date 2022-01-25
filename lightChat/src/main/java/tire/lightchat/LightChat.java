package tire.lightchat;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LightChat extends JavaPlugin {

    @Override
    public void onEnable() {

        File config = new File(getDataFolder() + File.separator + "src/java/resources/config.yml");
        if(!config.exists()) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }

        Bukkit.getPluginManager().registerEvents(new Handler(this), this);

    }

    @Override
    public void onDisable() {}
}
