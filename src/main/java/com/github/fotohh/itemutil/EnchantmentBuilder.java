package com.github.fotohh.itemutil;

import org.bukkit.enchantments.Enchantment;

/**
 * Aids in building enchantments.
 * @param enchantment enchantment
 * @param level level of the enchantment
 * @param value ignore level restriction
 */
public record EnchantmentBuilder(Enchantment enchantment, int level, boolean value) {
}
