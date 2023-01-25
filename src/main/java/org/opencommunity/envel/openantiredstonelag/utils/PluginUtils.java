package org.opencommunity.envel.openantiredstonelag.utils;

import org.bukkit.Bukkit;
import org.opencommunity.envel.openantiredstonelag.OpenAntiRedstoneLag;
import org.opencommunity.envel.openantiredstonelag.listener.OpenAntiRedstoneLagCommand;
import org.opencommunity.envel.openantiredstonelag.listener.RedstoneActivationListener;

import java.util.Objects;

public class PluginUtils {

    public static void loadCommands() {
        Objects.requireNonNull(OpenAntiRedstoneLag.m.getCommand("rlag")).setExecutor(new OpenAntiRedstoneLagCommand());
        Objects.requireNonNull(OpenAntiRedstoneLag.m.getCommand("redstonelag")).setExecutor(new OpenAntiRedstoneLagCommand());
    }

    public static void loadListeners() {
        Bukkit.getPluginManager().registerEvents(new RedstoneActivationListener(), OpenAntiRedstoneLag.m);
    }
}
