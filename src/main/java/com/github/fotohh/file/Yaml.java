package com.github.fotohh.file;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * An additional helper which adds some methods.
 */
public class Yaml extends YamlConfiguration {

    public Yaml(){
        super();
    }

    /**
     Adds a new number section to the specified YMLFile's configuration under the provided section.
     If the section doesn't exist, it will be created.
     @param section The section
     @return The newly created ConfigurationSection. if section is null method returns null
     */
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
     * Converts integer list to org.Bukkit.Location();
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


}
