package org.opencommunity.envel.openantiredstonelag.utils;

import java.util.*;

public class MapUtils {
    public static Map<org.bukkit.Chunk, Integer> sortByValues(Map<org.bukkit.Chunk, Integer> map) {
        List<Map.Entry<org.bukkit.Chunk, Integer>> list = new LinkedList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        Map<org.bukkit.Chunk, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<org.bukkit.Chunk, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
