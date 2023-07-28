package com.github.fotohh.itemutil;

import org.bukkit.enchantments.Enchantment;

/**
 * Aids in building enchantments.
 */
public class EnchantmentBuilder {

    private final Enchantment enchantment;
    private final int level;
    private final boolean ignoreLevelRestriction;

    /**
     * Constructs an EnchantmentBuilder object with the given enchantment, level, and value.
     *
     * @param enchantment The enchantment to be applied.
     * @param level       The level of the enchantment.
     * @param value       Whether to ignore the level restriction.
     */
    public EnchantmentBuilder(Enchantment enchantment, int level, boolean value) {
        this.enchantment = enchantment;
        this.level = level;
        this.ignoreLevelRestriction = value;
    }

    /**
     * Gets the enchantment of the EnchantmentBuilder.
     *
     * @return The enchantment.
     */
    public Enchantment getEnchantment() {
        return enchantment;
    }

    /**
     * Gets the level of the enchantment.
     *
     * @return The level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Checks if the level restriction should be ignored.
     *
     * @return True if the level restriction should be ignored, false otherwise.
     */
    public boolean isIgnoreLevelRestriction() {
        return ignoreLevelRestriction;
    }
}
