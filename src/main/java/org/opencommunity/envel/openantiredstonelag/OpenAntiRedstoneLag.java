package org.opencommunity.envel.openantiredstonelag;

import org.bukkit.plugin.java.JavaPlugin;
import org.opencommunity.envel.openantiredstonelag.listener.MainConfig;
import org.opencommunity.envel.openantiredstonelag.listener.OpenAntiRedstoneLagCommand;
import org.opencommunity.envel.openantiredstonelag.utils.RedstoneManager;

import static org.opencommunity.envel.openantiredstonelag.utils.PluginUtils.loadCommands;
import static org.opencommunity.envel.openantiredstonelag.utils.PluginUtils.loadListeners;

public class OpenAntiRedstoneLag extends JavaPlugin {
    public static OpenAntiRedstoneLag m;
    public static String name = "   §c§lRedstoneLag ";
    public static MainConfig config;

    public void onEnable() {
        m = this;
        config = new MainConfig();
        config.load();
        loadCommands();
        loadListeners();
        RedstoneManager.runTask();
    }

    public void onDisable() {
    }
}
