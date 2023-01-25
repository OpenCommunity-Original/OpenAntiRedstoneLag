package org.opencommunity.envel.openantiredstonelag.listener;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MainConfig {
    private final File file = new File("plugins/OpenAntiRedstoneLag", "config.yml");
    private final FileConfiguration config = YamlConfiguration.loadConfiguration(this.file);
    private int pageItems = 15;
    private int storedLists = 5;
    private int interval = 60;

    public void load() {
        this.pageItems = setObject("settings.page.items", this.pageItems);
        this.storedLists = setObject("settings.stored.lists", this.storedLists);
        this.interval = setObject("settings.stored.interval", this.interval);
    }

    public int setObject(String path, int object) {
        if (this.config.contains(path)) {
            return this.config.getInt(path);
        }
        this.config.set(path, object);
        save();
        return object;
    }

    public void save() {
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPageItems() {
        return this.pageItems;
    }

    public int getStoredLists() {
        return this.storedLists;
    }

    public int getInterval() {
        return this.interval;
    }
}
