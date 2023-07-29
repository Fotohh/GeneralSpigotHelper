
package com.github.fotohh.player;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * The PlayerDataManager class handles saving, retrieving, and removing player-specific data
 * to/from a YAML file for a Bukkit plugin.
 * It provides methods to interact with player data using their unique identifiers (UUIDs).
 */
public class PlayerDataManager {

    private final JavaPlugin plugin;
    private File dataFile;
    private FileConfiguration dataConfig;

    /**
     * Constructs a PlayerDataManager object with the specified JavaPlugin instance.
     *
     * @param plugin The JavaPlugin instance associated with the PlayerDataManager.
     */
    public PlayerDataManager(JavaPlugin plugin) {
        this.plugin = plugin;
        initDataFile();
    }

    /**
     * Initializes the data file for player data.
     * If the file does not exist, it will be created by copying the default "playerdata.yml"
     * resource from the plugin's JAR file. The configuration data is loaded from the file.
     */
    private void initDataFile() {
        dataFile = new File(plugin.getDataFolder(), "playerdata.yml");
        if (!dataFile.exists()) {
            plugin.saveResource("playerdata.yml", false);
        }

        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    /**
     * Saves player-specific data to the data file.
     *
     * @param player The Player object for whom the data is being saved.
     * @param key    The key identifying the data field in the data file.
     * @param value  The value to be stored for the specified key.
     */
    public void savePlayerData(Player player, String key, Object value) {
        String playerId = player.getUniqueId().toString();
        dataConfig.set(playerId + "." + key, value);
        saveDataFile();
    }

    /**
     * Retrieves player-specific data from the data file.
     *
     * @param player The Player object for whom the data is being retrieved.
     * @param key    The key identifying the data field in the data file.
     * @return The value associated with the specified key, or null if the key is not found.
     */
    public Object getPlayerData(Player player, String key) {
        String playerId = player.getUniqueId().toString();
        return dataConfig.get(playerId + "." + key);
    }

    /**
     * Removes player-specific data from the data file.
     *
     * @param player The Player object for whom the data is being removed.
     * @param key    The key identifying the data field in the data file.
     */
    public void removePlayerData(Player player, String key) {
        String playerId = player.getUniqueId().toString();
        dataConfig.set(playerId + "." + key, null);
        saveDataFile();
    }

    /**
     * Saves the data configuration to the data file.
     * If any errors occur during the save process, they will be printed to the console.
     */
    private void saveDataFile() {
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
