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
package com.github.firewolf8385.showitem.settings;

/**
 * Represents a plugin message configurable in config.yml.
 */
public enum PluginMessage {
    FORMAT_HAND("Formats.Hand", "%luckperms_prefix% %player_name% <green>is currently holding <item>"),
    FORMAT_HELMET("Formats.Helmet", "%luckperms_prefix% %player_name% <green>is currently wearing <item>"),
    FORMAT_CHESTPLATE("Formats.Chestplate", "%luckperms_prefix% %player_name% <green>is currently wearing <item>"),
    FORMAT_LEGGINGS("Formats.Leggings", "%luckperms_prefix% %player_name% <green>is currently wearing <item>"),
    FORMAT_BOOTS("Formats.Boots", "%luckperms_prefix% %player_name% <green>is currently wearing <item>"),
    NO_PERMISSION("Messages.NoPermission", "<red><bold>Error</bold> <dark_gray>» <red>You do not have access to that command."),
    ONLY_PLAYERS_CAN_USE("Messages.OnlyPlayersCanUse", "<red><bold>Error</bold> <dark_gray>» <red>Only players can use that command!"),
    NO_ITEM_IN_HAND("Messages.NoItemInHand", "<red><bold>Error</bold> <dark_gray>» <red>You are not holding an item.");

    private final String configPath;
    private final String defaultValue;

    /**
     * Creates the plugin message.
     * @param configPath Path to the message in config.yml
     * @param defaultValue Default value in case the path is missing.
     */
    PluginMessage(final String configPath, final String defaultValue) {
        this.configPath = configPath;
        this.defaultValue = defaultValue;
    }

    /**
     * Gets the path to the configured message.
     * @return Message path.
     */
    public String configPath() {
        return configPath;
    }

    /**
     * Gets the default value of the message configuration.
     * @return Default message.
     */
    public String defaultValue() {
        return defaultValue;
    }
}