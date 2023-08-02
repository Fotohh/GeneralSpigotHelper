package com.github.fotohh.player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

/**
 * The Title class represents a customizable title message that can be displayed to a player.
 *
 * @since 1.0.5
 */
public class Title {

    private final String title;
    private final String subtitle;
    private final String actionbar;
    private int fadeIn = 5;
    private int stay = 5;
    private int fadeOut = 5;

    /**
     * Constructs a Title object with the specified title message.
     *
     * @param title The title message to display.
     * @since 1.0.5
     */
    public Title(String title) {
        this.title = title;
        this.subtitle = "";
        this.actionbar = "";
    }

    /**
     * Constructs a Title object with the specified title and subtitle messages.
     *
     * @param title    The title message to display.
     * @param subtitle The subtitle message to display.
     * @since 1.0.5
     */
    public Title(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
        this.actionbar = "";
    }

    /**
     * Constructs a Title object with the specified title, subtitle, and actionbar messages.
     *
     * @param title    The title message to display.
     * @param subtitle The subtitle message to display.
     * @param actionbar The actionbar message to display.
     * @since 1.0.5
     */
    public Title(String title, String subtitle, String actionbar) {
        this.title = title;
        this.subtitle = subtitle;
        this.actionbar = actionbar;
    }

    /**
     * Sets the fade-in time for the title in ticks.
     *
     * @param fadeIn The fade-in time in ticks.
     * @return This Title object with the updated fade-in time.
     * @since 1.0.5
     */
    public Title setFadeIn(int fadeIn) {
        this.fadeIn = fadeIn;
        return this;
    }

    /**
     * Sets the fade-out time for the title in ticks.
     *
     * @param fadeOut The fade-out time in ticks.
     * @return This Title object with the updated fade-out time.
     * @since 1.0.5
     */
    public Title setFadeOut(int fadeOut) {
        this.fadeOut = fadeOut;
        return this;
    }

    /**
     * Sets the stay time for the title in ticks.
     *
     * @param stay The stay time in ticks.
     * @return This Title object with the updated stay time.
     * @since 1.0.5
     */
    public Title setStay(int stay) {
        this.stay = stay;
        return this;
    }

    /**
     * Sends the title and subtitle to the specified player.
     *
     * @param player The player to whom the title and subtitle will be sent.
     * @since 1.0.5
     */
    public void sendTo(Player player) {
        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
        if (actionbar != null) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(actionbar));
        }
    }

}
