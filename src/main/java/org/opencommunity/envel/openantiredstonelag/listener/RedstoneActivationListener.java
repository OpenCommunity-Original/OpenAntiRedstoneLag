package org.opencommunity.envel.openantiredstonelag.listener;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

import java.util.HashMap;

public class RedstoneActivationListener implements Listener {
    public static HashMap<World, HashMap<Chunk, Integer>> redstoneTicks = new HashMap<>();

    @EventHandler
    public void on(BlockRedstoneEvent event) {
        Chunk chunk = event.getBlock().getChunk();
        World world = chunk.getWorld();
        if (redstoneTicks.containsKey(world)) {
            HashMap<Chunk, Integer> ticks = redstoneTicks.get(world);
            if (ticks.containsKey(chunk)) {
                ticks.put(chunk, ticks.get(chunk) + 1);
            } else {
                ticks.put(chunk, 1);
            }
            redstoneTicks.put(world, ticks);
            return;
        }
        HashMap<Chunk, Integer> ticks2 = new HashMap<>();
        ticks2.put(chunk, 1);
        redstoneTicks.put(world, ticks2);
    }
}
