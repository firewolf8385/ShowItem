/*
 * This file is part of ShowItem, licensed under the MIT License.
 *
 *  Copyright (c) firewolf8385
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package com.github.firewolf8385.showitem;

import com.github.firewolf8385.showitem.commands.AbstractCommand;
import com.github.firewolf8385.showitem.listeners.ReloadListener;
import com.github.firewolf8385.showitem.settings.ConfigManager;
import com.github.firewolf8385.showitem.settings.HookManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShowItemPlugin extends JavaPlugin {
    private ConfigManager configManager;
    private HookManager hookManager;

    /**
     * Runs when the plugin is first enabled.
     */
    @Override
    public void onEnable() {
        // Loads the configuration file. Creates if not present.
        configManager = new ConfigManager(this);
        hookManager = new HookManager(this);

        // Enables bStats for statistics tracking.
        new Metrics(this, 19781);

        // Register commands.
        AbstractCommand.registerCommands(this);

        // Register Reload listener, if BetterReload is detected.
        if(hookManager.useBetterReload()) getServer().getPluginManager().registerEvents(new ReloadListener(this), this);
    }

    /**
     * Gets the plugins config manager, which manages the config file.
     * @return Config Manager.
     */
    public ConfigManager getConfigManager() {
        return configManager;
    }

    /**
     * Get the Hook Manager, which returns an object that keeps track of hooks into other plugins.
     * @return HookManager.
     */
    public HookManager getHookManager() {
        return hookManager;
    }

    /**
     * Reloads the plugin configuration and updates important values.
     */
    public void reload() {
        this.configManager.reloadConfig();
    }
}