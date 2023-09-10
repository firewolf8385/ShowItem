package com.github.firewolf8385.showitem;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShowItemPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        // Enables bStats for statistics tracking.
        new Metrics(this, 19781);
    }
}