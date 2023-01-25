package org.opencommunity.envel.openantiredstonelag.utils;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.opencommunity.envel.openantiredstonelag.OpenAntiRedstoneLag;
import org.opencommunity.envel.openantiredstonelag.listener.RedstoneActivationListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RedstoneManager {
    public static HashMap<Long, HashMap<World, HashMap<Chunk, Integer>>> redstoneChunks = new HashMap<>();
    public static long lastTime = System.currentTimeMillis();

    public static void runTask() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(OpenAntiRedstoneLag.m, () -> {
            RedstoneManager.lastTime = System.currentTimeMillis() / 1000;
            HashMap<World, HashMap<Chunk, Integer>> sortedWorlds = new HashMap<>();
            for (World world : RedstoneActivationListener.redstoneTicks.keySet()) {
                sortedWorlds.put(world, new HashMap<>(MapUtils.sortByValues(RedstoneActivationListener.redstoneTicks.get(world))));
            }
            RedstoneManager.redstoneChunks.put(RedstoneManager.lastTime, sortedWorlds);
            RedstoneActivationListener.redstoneTicks.clear();
            long removeTime = RedstoneManager.lastTime - ((long) OpenAntiRedstoneLag.config.getInterval() * OpenAntiRedstoneLag.config.getStoredLists());
            List<Long> removeTimes = new ArrayList<>();
            for (Long time : RedstoneManager.redstoneChunks.keySet()) {
                if (time < removeTime) {
                    removeTimes.add(time);
                }
            }
            for (Long time : removeTimes) {
                RedstoneManager.redstoneChunks.remove(time);
            }
        }, 20, 20L * OpenAntiRedstoneLag.config.getInterval());
    }
}
