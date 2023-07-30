package com.github.fotohh.command;

/**
 * The CommandArgument class provides utility methods for handling command arguments in a Bukkit/Spigot plugin.
 *
 * @since 1.0.4
 */
public class CommandArgument {

    private final String[] args;

    /**
     * Constructs a new CommandArgument instance with the given array of command arguments.
     *
     * @param args The array of command arguments.
     * @since 1.0.4
     */
    public CommandArgument(String[] args) {
        this.args = args;
    }

    /**
     * Gets the argument at the specified index as a String.
     *
     * @param index The index of the argument to retrieve.
     * @return The argument at the specified index as a String, or null if the index is out of bounds.
     * @since 1.0.4
     */
    public String getString(int index) {
        if (index >= 0 && index < args.length) {
            return args[index];
        }
        return null; // Or throw an exception, depending on your requirements
    }

    /**
     * Gets the argument at the specified index as an integer.
     * If the argument at the given index cannot be parsed as an integer, it returns a default value.
     *
     * @param index        The index of the argument to retrieve.
     * @param defaultValue The default value to return if the argument cannot be parsed as an integer.
     * @return The argument at the specified index as an integer, or the default value if parsing fails or the index is out of bounds.
     * @since 1.0.4
     */
    public int getInt(int index, int defaultValue) {
        if (index >= 0 && index < args.length) {
            try {
                return Integer.parseInt(args[index]);
            } catch (NumberFormatException ignored) {
            }
        }
        return defaultValue;
    }

    /**
     * Checks if the number of command arguments is equal to or greater than the specified length.
     *
     * @param length The minimum required length of command arguments.
     * @return true if the number of arguments is equal to or greater than the specified length, otherwise false.
     * @since 1.0.4
     */
    public boolean hasEnoughArguments(int length) {
        return args.length >= length;
    }
}
