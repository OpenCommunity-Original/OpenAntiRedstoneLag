package org.opencommunity.envel.openantiredstonelag.utils;

import org.bukkit.Bukkit;
import org.opencommunity.envel.openantiredstonelag.OpenAntiRedstoneLag;
import org.opencommunity.envel.openantiredstonelag.listener.OpenAntiRedstoneLagCommand;
import org.opencommunity.envel.openantiredstonelag.listener.RedstoneActivationListener;

public class PluginUtils {

    public static void loadCommands() {
        OpenAntiRedstoneLag.m.getCommand("rl").setExecutor(new OpenAntiRedstoneLagCommand());
        OpenAntiRedstoneLag.m.getCommand("OpenAntiRedstoneLag").setExecutor(new OpenAntiRedstoneLagCommand());
    }

    public static void loadListeners() {
        Bukkit.getPluginManager().registerEvents(new RedstoneActivationListener(), OpenAntiRedstoneLag.m);
    }
}
