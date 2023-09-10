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
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * A wrapper for CommandExecutor that makes it quicker to implement commands.
 */
public abstract class AbstractCommand implements CommandExecutor {
    private final String permission;
    private final boolean canConsoleUse;
    private static ShowItemPlugin plugin;

    /**
     * Creates a new AbstractCommand.
     * @param commandName Name of the command.
     * @param permission Permission required to use the command.
     * @param canConsoleUse Whether console can use the command.
     */
    public AbstractCommand(final String commandName, final String permission, final boolean canConsoleUse) {
        this.permission = permission;
        this.canConsoleUse = canConsoleUse;
        plugin.getCommand(commandName).setExecutor(this);
    }

    /**
     * Registers all commands in the plugin.
     * @param pl Plugin.
     */
    public static void registerCommands(ShowItemPlugin pl) {
        plugin = pl;
        new ShowItemCMD(pl);
    }

    /**
     * Executes the command.
     * @param sender The Command Sender.
     * @param args Arguments of the command.
     */
    public abstract void execute(CommandSender sender, String[] args);

    /**
     * Processes early execution of the plugin.
     * @param sender Command Sender.
     * @param cmd The Command.
     * @param label Command Label.
     * @param args Command Arguments.
     * @return Successful Completion.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        // Makes sure the player has permission to use the command.
        if(!permission.equals("") && !sender.hasPermission(permission)) {
            ChatUtils.chat(sender, plugin.settingsManager().processMessage(PluginMessage.NO_PERMISSION));
            return true;
        }

        // Makes sure the console can use the command if the sender is the console.
        if(!canConsoleUse && !(sender instanceof Player)) {
            ChatUtils.chat(sender, plugin.settingsManager().processMessage(PluginMessage.ONLY_PLAYERS_CAN_USE));
            return true;
        }

        // Executes the command.
        execute(sender, args);
        return true;
    }
}