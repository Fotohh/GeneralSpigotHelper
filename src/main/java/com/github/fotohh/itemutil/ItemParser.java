
package com.github.fotohh.itemutil;

import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("material", itemStack.getType().name());
        jsonObject.put("amount", itemStack.getAmount());

        if (itemStack.hasItemMeta()) {
            ItemMeta meta = itemStack.getItemMeta();

            if (meta.hasDisplayName()) {
                jsonObject.put("display_name", meta.getDisplayName());
            }

            if (meta.hasLore()) {
                JSONArray loreArray = new JSONArray();
                for (String lore : meta.getLore()) {
                    loreArray.add(lore);
                }
                jsonObject.put("lore", loreArray);
            }

            if (meta.hasEnchants()) {
                JSONObject enchantObject = new JSONObject();
                for (Map.Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
                    enchantObject.put(entry.getKey().getName(), entry.getValue());
                }
                jsonObject.put("enchantments", enchantObject);
            }
        }

        return jsonObject.toJSONString();
    }

    /**
     * Deserialize an ItemStack from a JSON string.
     *
     * @param jsonString The JSON string representing the ItemStack.
     * @return The deserialized ItemStack, or null if the parsing fails.
     */
    public static ItemStack deserializeItemStack(String jsonString) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);

            Material material = Material.valueOf((String) jsonObject.get("material"));
            int amount = ((Long) jsonObject.get("amount")).intValue();

            ItemStack itemStack = new ItemStack(material, amount);

            if (jsonObject.containsKey("display_name")) {
                ItemMeta meta = itemStack.getItemMeta();
                meta.setDisplayName((String) jsonObject.get("display_name"));
                itemStack.setItemMeta(meta);
            }

            if (jsonObject.containsKey("lore")) {
                JSONArray loreArray = (JSONArray) jsonObject.get("lore");
                List<String> loreList = new ArrayList<>();
                for (Object lore : loreArray) {
                    loreList.add((String) lore);
                }
                ItemMeta meta = itemStack.getItemMeta();
                meta.setLore(loreList);
                itemStack.setItemMeta(meta);
            }

            if (jsonObject.containsKey("enchantments")) {
                JSONObject enchantObject = (JSONObject) jsonObject.get("enchantments");
                for (Object key : enchantObject.keySet()) {
                    String enchantmentName = (String) key;
                    int enchantLevel = ((Long) enchantObject.get(key)).intValue();
                    Enchantment enchantment = Enchantment.getByName(enchantmentName);
                    if (enchantment != null) {
                        itemStack.addUnsafeEnchantment(enchantment, enchantLevel);
                    }
                }
            }

            return itemStack;
        } catch (ParseException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        return null;
    }
}
