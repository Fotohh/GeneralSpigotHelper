package com.github.fotohh.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * The ArgumentParser class provides utility methods for parsing command arguments into different data types.
 */
public class ArgumentParser {

    /**
     * Parses the provided argument into an integer.
     * If the parsing fails, the default value is returned.
     *
     * @param arg          The argument to parse as an integer.
     * @param defaultValue The default value to return if parsing fails.
     * @return The parsed integer value if successful, otherwise the default value.
     */
    public static int parseInt(String arg, int defaultValue) {
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Parses the provided argument into a double.
     * If the parsing fails, the default value is returned.
     *
     * @param arg          The argument to parse as a double.
     * @param defaultValue The default value to return if parsing fails.
     * @return The parsed double value if successful, otherwise the default value.
     */
    public static double parseDouble(String arg, double defaultValue) {
        try {
            return Double.parseDouble(arg);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Parses the provided argument into a boolean.
     * If the parsing fails, the default value is returned.
     *
     * @param arg          The argument to parse as a boolean.
     * @param defaultValue The default value to return if parsing fails.
     * @return The parsed boolean value if successful, otherwise the default value.
     */
    public static boolean parseBoolean(String arg, boolean defaultValue) {
        return Boolean.parseBoolean(arg);
    }

    /**
     * Parses the provided argument into a float.
     * If the parsing fails, the default value is returned.
     *
     * @param arg          The argument to parse as a float.
     * @param defaultValue The default value to return if parsing fails.
     * @return The parsed float value if successful, otherwise the default value.
     */
    public static float parseFloat(String arg, float defaultValue) {
        try {
            return Float.parseFloat(arg);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Parses the provided argument into a long.
     * If the parsing fails, the default value is returned.
     *
     * @param arg          The argument to parse as a long.
     * @param defaultValue The default value to return if parsing fails.
     * @return The parsed long value if successful, otherwise the default value.
     */
    public static long parseLong(String arg, long defaultValue) {
        try {
            return Long.parseLong(arg);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Parses the provided argument into a Player.
     *
     * @param arg The argument to parse as a Player's name or UUID.
     * @return The Player object if found, otherwise null.
     */
    public static Player parsePlayer(String arg) {
        return Bukkit.getPlayer(arg);
    }

    /**
     * Parses the provided argument into a UUID.
     *
     * @param arg The argument to parse as a UUID string.
     * @return The UUID object if the parsing is successful, otherwise null.
     */
    public static UUID parseUUID(String arg) {
        try {
            return UUID.fromString(arg);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Parses the provided argument into a Material.
     *
     * @param arg The argument to parse as a Material name or ID.
     * @return The Material object if found, otherwise null.
     */
    public static Material parseMaterial(String arg) {
        try {
            return Material.matchMaterial(arg);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
