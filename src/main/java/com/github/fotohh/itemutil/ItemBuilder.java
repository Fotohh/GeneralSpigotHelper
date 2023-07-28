package com.github.fotohh.itemutil;

import com.github.fotohh.utility.GeneralUtility;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * ItemBuilder class provides a convenient way to create custom ItemStack objects with title, lore, and enchantments.
 */
public class ItemBuilder extends ItemStack {

    private final ItemMeta itemMeta;

    /**
     * Constructs a new ItemBuilder with the specified Material.
     *
     * @param material The Material to set for the ItemStack.
     */
    public ItemBuilder(Material material) {
        super(material);
        this.itemMeta = getItemMeta();
    }

    /**
     * Sets the title (display name) of the ItemStack.
     *
     * @param title The title (display name) to set.
     * @return The ItemBuilder instance to allow method chaining.
     */
    public ItemBuilder withTitle(String title) {
        itemMeta.setDisplayName(GeneralUtility.chat(title));
        return this;
    }

    /**
     * Sets the lore of the ItemStack.
     *
     * @param lore An array of strings representing the lore lines to set.
     * @return The ItemBuilder instance to allow method chaining.
     */
    public ItemBuilder withLore(String... lore) {
        itemMeta.setLore(Arrays.stream(lore).map(GeneralUtility::chat).collect(Collectors.toList()));
        return this;
    }

    /**
     * Adds an enchantment to the ItemStack.
     *
     * @param enchantmentBuilder The EnchantmentBuilder object representing the enchantment to add.
     * @return The ItemBuilder instance to allow method chaining.
     */
    public ItemBuilder addEnchantment(EnchantmentBuilder enchantmentBuilder) {
        itemMeta.addEnchant(enchantmentBuilder.enchantment(), enchantmentBuilder.level(), enchantmentBuilder.value());
        return this;
    }

    /**
     * Adds multiple enchantments to the ItemStack.
     *
     * @param enchantmentBuilders An array of EnchantmentBuilder objects representing the enchantments to add.
     * @return The ItemBuilder instance to allow method chaining.
     */
    public ItemBuilder addEnchantments(EnchantmentBuilder... enchantmentBuilders) {
        Arrays.stream(enchantmentBuilders).forEach(this::addEnchantment);
        return this;
    }

    /**
     * Builds the ItemStack with the specified attributes.
     *
     * @return The built ItemBuilder instance.
     */
    public ItemBuilder build() {
        setItemMeta(itemMeta);
        return this;
    }

}