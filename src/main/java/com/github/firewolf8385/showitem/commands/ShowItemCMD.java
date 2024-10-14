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
 * Runs the /showitem command, which displays the item the player is currently holding in chat.
 * Permission: showitem.use
 */
public class ShowItemCMD extends AbstractCommand {
    private final ShowItemPlugin plugin;

    /**
     * Creaters the command.
     * @param plugin Instance of the plugin.
     */
    public ShowItemCMD(@NotNull final ShowItemPlugin plugin) {
        super("showitem", "showitem.use", false);
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
        final ItemStack item;

        // Check if the item should be the main hand or off hand.
        if(player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR) {

            // Makes sure they aren't both null.
            if(player.getInventory().getItemInOffHand() == null || player.getInventory().getItemInOffHand().getType() == Material.AIR) {
                ChatUtils.chat(player, plugin.getConfigManager().processMessage(PluginMessage.NO_ITEM_IN_HAND));
                return;
            }
            else {
                item = player.getInventory().getItemInOffHand();
            }
        }
        else {
            item = player.getInventory().getItemInMainHand();
        }

        // Config string.
        final String configMessage = PlaceholderAPI.setPlaceholders(player, plugin.getConfigManager().processMessage(PluginMessage.FORMAT_HAND));

        // Item Component.
        String itemName = MiniMessage.miniMessage().serialize(item.displayName());
        if(item.getAmount() > 1) itemName += " <white>x" + item.getAmount();
        final Component itemComponent = Component.text().append(MiniMessage.miniMessage().deserialize(itemName)).hoverEvent(item.asHoverEvent()).asComponent();

        // Final component
        final Component component = MiniMessage.miniMessage().deserialize(ChatUtils.replaceLegacy(configMessage), Placeholder.component("item", itemComponent));

        // Broadcast message
        Bukkit.broadcast(component);
    }
}