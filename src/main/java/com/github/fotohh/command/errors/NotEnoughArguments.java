package com.github.fotohh.command.errors;

/**
 * The NotEnoughArguments class is a custom CommandError implementation that represents an error when a command does not have enough arguments.
 * It provides methods to set and get the required argument length for the command and inherits functionality to set and send error messages.
 *
 * @since 1.0.4
 */
public class NotEnoughArguments {

    private int length;

    /**
     * Sets the required argument length for the command.
     * This value indicates the minimum number of arguments expected for the command to be considered valid.
     *
     * @param length The required argument length for the command.
     * @since 1.0.4
     */
    public void setArgumentLength(int length) {
        this.length = length;
    }

    /**
     * Gets the required argument length for the command.
     *
     * @return The required argument length.
     * @since 1.0.4
     */
    public int getArgumentLength() {
        return length;
    }
}
