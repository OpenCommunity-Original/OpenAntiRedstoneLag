package org.opencommunity.envel.openantiredstonelag.utils;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.opencommunity.envel.openantiredstonelag.OpenAntiRedstoneLag;
import org.opencommunity.envel.openantiredstonelag.listener.RedstoneActivationListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class RedstoneManager {
    public static HashMap<Long, HashMap<World, HashMap<Chunk, Integer>>> redstoneChunks = new HashMap<>();
    public static long lastTime = System.currentTimeMillis();

    public static void runTask() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(OpenAntiRedstoneLag.m, new Runnable() {
            @Override
            public void run() {
                RedstoneManager.lastTime = System.currentTimeMillis() / 1000;
                HashMap<World, HashMap<Chunk, Integer>> sortedWorlds = new HashMap<>();
                Iterator<World> it = RedstoneActivationListener.redstoneTicks.keySet().iterator();
                while (it.hasNext()) {
                    World world = it.next();
                    sortedWorlds.put(world, MapUtils.sortByValues(RedstoneActivationListener.redstoneTicks.get(world)));
                }
                RedstoneManager.redstoneChunks.put(Long.valueOf(RedstoneManager.lastTime), sortedWorlds);
                RedstoneActivationListener.redstoneTicks.clear();
                long removeTime = RedstoneManager.lastTime - ((long) (OpenAntiRedstoneLag.config.getInterval() * OpenAntiRedstoneLag.config.getStoredLists()));
                List<Long> removeTimes = new ArrayList<>();
                Iterator<Long> it2 = RedstoneManager.redstoneChunks.keySet().iterator();
                while (it2.hasNext()) {
                    Long time = it2.next();
                    if (time.longValue() < removeTime) {
                        removeTimes.add(time);
                    }
                }
                Iterator<Long> it3 = removeTimes.iterator();
                while (it3.hasNext()) {
                    RedstoneManager.redstoneChunks.remove(it3.next());
                }
            }
        }, 20, 20 * OpenAntiRedstoneLag.config.getInterval());
    }
}
