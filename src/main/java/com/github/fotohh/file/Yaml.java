package com.github.fotohh.file;

import com.github.fotohh.utility.GeneralUtility;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * An additional helper which adds some methods.
 */
public class Yaml extends YamlConfiguration {

    public Yaml(){
        super();
    }

    public ConfigurationSection createNumberedSection(String baseSectionName) {
        int sectionNumber = 1;
        while (isConfigurationSection(baseSectionName + "." + sectionNumber)) {
            sectionNumber++;
        }
        return createSection(baseSectionName + "." + sectionNumber);
    }

    /**
     Adds a new number section to the specified YMLFile's configuration under the provided section.
     If the section doesn't exist, it will be created.
     @deprecated Use {@link #createNumberedSection(String)} instead of this.
     @param section The section
     @return The newly created ConfigurationSection. If a section is null method returns null
     */
    @Deprecated
    public static ConfigurationSection addNumberedSection(ConfigurationSection section){
        if(section == null) return null;
        int i;
        i = section.getKeys(false).stream()
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0) + 1;
        return section.createSection(String.valueOf(i));
    }

    /**
     * Converts an integer list to org.Bukkit.Location();
     * @param world param for location
     * @param doubleList integer list
     * @return location or else null
     */
    public static @Nullable Location listToLocation(World world, List<Double> doubleList){
        if(doubleList == null || world == null) return null;
        return new Location(world, doubleList.get(0),doubleList.get(1),doubleList.get(2));
    }

    /**
     * Converts a Location object to a List of Double values representing its coordinates.
     *
     * @param location The Location object to be converted.
     * @return A List containing the X, Y, and Z coordinates of the given Location object.
     */
    public static List<Double> locationToList(Location location){
        List<Double> doubleList = new ArrayList<>();
        doubleList.add(location.getX());
        doubleList.add(location.getY());
        doubleList.add(location.getZ());
        return doubleList;
    }

    /**
     * Gets or creates a configuration section within the YAML file.
     * If the section already exists, it will be returned. Otherwise, a new section will be created.
     *
     * @param sectionName The name of the section.
     * @return The configuration section.
     */
    public @Nullable ConfigurationSection getOrCreateSection(String sectionName) {
        ConfigurationSection section = getConfigurationSection(sectionName);
        if (section == null) {
            section = createSection(sectionName);
        }
        return section;
    }

    public ConfigurationSection getOrCreateSection(@NotNull ConfigurationSection parentSection, String sectionName) {
        ConfigurationSection section = parentSection.getConfigurationSection(sectionName);
        if (section == null) {
            section = parentSection.createSection(sectionName);
        }
        return section;
    }

    /**
     * Gets or creates a nested configuration section within the YAML file.
     * If the parent section doesn't exist, it will return null.
     * If the nested section already exists, it will be returned. Otherwise, a new section will be created.
     *
     * @param parentSectionName The name of the parent section.
     * @param sectionName       The name of the nested section.
     * @return The nested configuration section, or null if the parent section doesn't exist.
     */
    public @Nullable ConfigurationSection getOrCreateSection(String parentSectionName, String sectionName) {
        ConfigurationSection parentSection = findNestedSection(parentSectionName);
        if (parentSection == null) return null;
        ConfigurationSection targetSection = parentSection.getConfigurationSection(sectionName);
        if (targetSection == null) {
            targetSection = parentSection.createSection(sectionName);
        }
        return targetSection;
    }

    /**
     * Checks if a section with the given name exists in the YAML file.
     *
     * @param sectionName The name of the section to check.
     * @return True if the section exists, false otherwise.
     */
    public boolean sectionExists(String sectionName) {
        return isConfigurationSection(sectionName) && contains(sectionName);
    }

    private @Nullable ConfigurationSection findNestedSection(String parentSectionName, ConfigurationSection currentSection) {
        for (String sectionName : currentSection.getKeys(false)) {
            ConfigurationSection nestedSection = currentSection.getConfigurationSection(sectionName);
            if (nestedSection != null) {
                if (sectionName.equalsIgnoreCase(parentSectionName)) {
                    return nestedSection;
                }
                ConfigurationSection foundSection = findNestedSection(parentSectionName, nestedSection);
                if (foundSection != null) {
                    return foundSection;
                }
            }
        }
        return null;
    }

    /**
     * Finds a nested configuration section within the YAML file.
     *
     * @param parentSectionName The name of the parent section.
     * @return The nested configuration section, or null if it doesn't exist.
     */
    public @Nullable ConfigurationSection findNestedSection(String parentSectionName) {
        if (!sectionExists(parentSectionName)) {
            return null;
        }
        return findNestedSection(parentSectionName, this);
    }

    public Color stringToColor(String msg){
        switch (msg.toUpperCase()){
            case "RED": return Color.RED;
            case "ORANGE": return Color.ORANGE;
            case "YELLOW": return Color.YELLOW;
            case "GREEN": return Color.GREEN;
            case "BLUE": return Color.BLUE;
            case "PURPLE":  return Color.PURPLE;
            case "BLACK": return Color.BLACK;
            case "AQUA": return Color.AQUA;
            case "FUCHISA":return Color.FUCHSIA;
            case "GRAY":return Color.GRAY;
            case "LIME":return Color.LIME;
            case "MAROON":return Color.MAROON;
            case "NAVY":return Color.NAVY;
            case "OLIVE":return Color.OLIVE;
            case "SILVER":return Color.SILVER;
            case "TEAL":return Color.TEAL;
            case "WHITE":return Color.WHITE;
            default: throw new IllegalStateException("Unexpected value: " + msg);
        }
    }
    public void set(String path, Material material){
        set(path, material.toString());
    }
    public Material getMaterial(String path){
        return Material.getMaterial(getString(path));
    }

    public void set(String path, Color color){
        set(path, color.toString().toUpperCase());
    }
    @Override
    public Color getColor(@NotNull String path){
        return stringToColor(getString(path));
    }
    public World getWorld(String path){
        UUID uuid = UUID.fromString(getString(path));
        return Bukkit.getWorld(uuid);
    }

    public void setWorld(String path, World world){
        set(path, world.getUID().toString());
    }
    public void set(String path, ItemStack itemStack){
        ConfigurationSection itemSection = getOrCreateSection(path);
        Material material = itemStack.getType();
        set(itemSection.getCurrentPath() + ".material", material);

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemSection.set("displayName", itemMeta.getDisplayName());
            itemSection.set("lore", itemMeta.getLore());

            Map<Enchantment, Integer> enchantments = itemStack.getEnchantments();
            List<String> enchantmentList = new ArrayList<>();
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                Enchantment enchantment = entry.getKey();
                int level = entry.getValue();
                enchantmentList.add(enchantment.getKey().getNamespace() + enchantment.getKey().getKey() + ":" + level);
            }
            itemSection.set("enchantments", enchantmentList);
        }
    }
    @Override
    public ItemStack getItemStack(@NotNull String path) {
        ConfigurationSection section = getOrCreateSection(path);

        ConfigurationSection itemSection = section.getConfigurationSection(path);
        String currentPath = itemSection.getCurrentPath();

        Material material = getMaterial(currentPath + ".material");
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return null;

        String name = itemSection.getString("displayName");
        meta.setDisplayName(name);

        List<String> lore = itemSection.getStringList("lore");
        meta.setLore(lore.stream().map(GeneralUtility::chat).collect(Collectors.toList()));

        List<String> enchantments = itemSection.getStringList("enchantments");
        for (String enchant : enchantments) {
            String[] data = enchant.split(":");
            int level = Integer.parseInt(data[2]);
            String enchantmentName = data[1];
            itemStack.addEnchantment(Enchantment.getByKey(NamespacedKey.minecraft(enchantmentName)), level);
        }

        itemStack.setItemMeta(meta);
        return itemStack;
    }
    public void set(String path, Location location){
        ConfigurationSection locSection = getOrCreateSection(path);
        locSection.set("coordinates", Arrays.asList(location.getX(), location.getY(), location.getZ()));
        locSection.set("world", location.getWorld().getUID().toString());
    }
    @Override
    public Location getLocation(@NotNull String path){
        List<Double> list = getConfigurationSection(path).getDoubleList("coordinates");
        UUID uuid = UUID.fromString(getConfigurationSection(path).getString("uuid"));
        return new Location(Bukkit.getWorld(uuid),list.get(0),list.get(1),list.get(2));
    }

    public void set(String path, Player player){
        set(path, player.getUniqueId());
    }
    public Player getPlayer(String path){
        return Bukkit.getPlayer(UUID.fromString(getString(path)));
    }
    public void set(String path, OfflinePlayer player){
        set(path, player.getUniqueId());
    }
    @Override
    public OfflinePlayer getOfflinePlayer(String path){
        return Bukkit.getOfflinePlayer(UUID.fromString(getString(path)));
    }

    public void set(String path, Inventory inventory, String title){
        ConfigurationSection section = getOrCreateSection(path);
        int size = inventory.getSize();
        section.set("inventory_title", title);
        section.set("inventory_size", size);
        section.set("inventory_type", inventory.getType().name());
        ConfigurationSection itemSection = getOrCreateSection(path + ".items");
        for(int i = 0; i < size; i++){
            ItemStack[] items = inventory.getContents();
            set(itemSection.getCurrentPath() +"."+i, items[i]);
        }
    }

    public Inventory getInventory(String path){
        Inventory inventory;
        boolean sizeIsNotNull = get(path + ".inventory_size") == null;
        String title = getString(path + ".inventory_title");
        Map<Integer, ItemStack> items = new HashMap<>();
        for(String key : getConfigurationSection(path + ".items").getKeys(false)){
            int i = Integer.parseInt(key);
            items.put(i,getItemStack(key));
        }
        if(!sizeIsNotNull) {
            int size = getInt(path + ".inventory_size");
            inventory = Bukkit.createInventory(null, size, title);

        }else {
            InventoryType type = InventoryType.valueOf(getString(path + ".inventory_type"));
            inventory = Bukkit.createInventory(null, type, title);
        }
        for(int key : items.keySet()){
            inventory.setItem(key, items.get(key));
        }
        return inventory;
    }

}
