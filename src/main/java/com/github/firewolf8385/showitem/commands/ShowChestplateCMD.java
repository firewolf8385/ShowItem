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
package com.github.firewolf8385.showitem.commands;

import com.github.firewolf8385.showitem.ShowItemPlugin;
import com.github.firewolf8385.showitem.settings.PluginMessage;
import com.github.firewolf8385.showitem.utils.ChatUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Runs the /showchestplate command, which displays the chestplate the player is currently wearing in chat.
 * Permission: showitem.use
 */
public class ShowChestplateCMD extends AbstractCommand {
    private final ShowItemPlugin plugin;

    /**
     * Creates the command.
     * @param plugin Instance of the plugin.
     */
    public ShowChestplateCMD(@NotNull final ShowItemPlugin plugin) {
        super("showchestplate", "showitem.use", false);
        this.plugin = plugin;
    }

    /**
     * Runs when the command is executed.
     * @param sender The Command Sender.
     * @param args Arguments of the command.
     */
    @Override
    public void execute(@NotNull final CommandSender sender, @NotNull final String[] args) {
        final Player player = (Player) sender;

        // Makes sure they have a helmet.
        if(player.getInventory().getChestplate() == null || player.getInventory().getChestplate().getType() == Material.AIR) {
            ChatUtils.chat(player, plugin.getConfigManager().processMessage(PluginMessage.NO_CHESTPLATE));
            return;
        }

        final ItemStack item = player.getInventory().getChestplate();

        // Config string.
        final String configMessage = PlaceholderAPI.setPlaceholders(player, plugin.getConfigManager().processMessage(PluginMessage.FORMAT_CHESTPLATE));

        // Item Component.
        final Component itemComponent = Component.text().append(item.displayName()).hoverEvent(item.asHoverEvent()).asComponent();

        // Final component
        final Component component = MiniMessage.miniMessage().deserialize(ChatUtils.replaceLegacy(configMessage), Placeholder.component("item", itemComponent));

        // Broadcast message
        Bukkit.broadcast(component);
    }
}