
package com.github.fotohh.itemutil;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The ItemParser class provides methods to serialize and deserialize ItemStacks to and from JSON strings.
 * It allows ItemStacks to be converted into JSON format for storage or transmission, and then converted
 * back to ItemStacks when needed.
 */
public class ItemParser {

    /**
     * Serialize an ItemStack to a JSON string.
     *
     * @param itemStack The ItemStack to serialize.
     * @return The JSON string representing the ItemStack.
     */
    public static String serializeItemStack(ItemStack itemStack) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("material", itemStack.getType().name());
        jsonObject.addProperty("amount", itemStack.getAmount());

        if (itemStack.hasItemMeta()) {
            ItemMeta meta = itemStack.getItemMeta();

            if (meta.hasDisplayName()) {
                jsonObject.addProperty("display_name", meta.getDisplayName());
            }

            if (meta.hasLore()) {
                JsonArray loreArray = new JsonArray();
                for (String lore : meta.getLore()) {
                    loreArray.add(lore);
                }
                jsonObject.add("lore", loreArray);
            }

            if (meta.hasEnchants()) {
                JsonObject enchantObject = new JsonObject();
                for (Map.Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
                    enchantObject.addProperty(entry.getKey().getName(), entry.getValue());
                }
                jsonObject.add("enchantments", enchantObject);
            }
        }

        return jsonObject.getAsString();
    }

    /**
     * Deserialize an ItemStack from a JSON string.
     *
     * @param jsonString The JSON string representing the ItemStack.
     * @return The deserialized ItemStack, or null if the parsing fails.
     */
    public static ItemStack deserializeItemStack(String jsonString) {
        try {
            JsonObject jsonObject = (JsonObject) JsonParser.parseString(jsonString);

            Material material = Material.valueOf(jsonObject.get("material").getAsString());
            int amount = (jsonObject.get("amount")).getAsInt();

            ItemStack itemStack = new ItemStack(material, amount);

            if (jsonObject.has("display_name")) {
                ItemMeta meta = itemStack.getItemMeta();
                meta.setDisplayName(jsonObject.get("display_name").getAsString());
                itemStack.setItemMeta(meta);
            }

            if (jsonObject.has("lore")) {
                JsonArray loreArray = (JsonArray) jsonObject.get("lore");
                List<String> loreList = new ArrayList<>();
                for (Object lore : loreArray) {
                    loreList.add((String) lore);
                }
                ItemMeta meta = itemStack.getItemMeta();
                meta.setLore(loreList);
                itemStack.setItemMeta(meta);
            }

            if (jsonObject.has("enchantments")) {
                JsonObject enchantObject = (JsonObject) jsonObject.get("enchantments");
                for (String key : enchantObject.keySet()) {
                    int enchantLevel = enchantObject.get(key).getAsInt();
                    Enchantment enchantment = Enchantment.getByName(key);
                    if (enchantment != null) {
                        itemStack.addUnsafeEnchantment(enchantment, enchantLevel);
                    }
                }
            }

            return itemStack;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}
