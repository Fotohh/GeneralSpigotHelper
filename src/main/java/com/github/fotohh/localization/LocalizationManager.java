
package com.github.fotohh.localization;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * The LocalizationManager class handles localization and language bundles for a Bukkit plugin.
 * It allows loading and retrieval of localized messages based on the specified locale and key.
 * Language bundles are loaded from the "lang" folder within the plugin's data folder.
 */
public class LocalizationManager {

    private final Plugin plugin;
    private final String defaultLanguage;
    private final Map<Locale, ResourceBundle> languageBundles;

    /**
     * Constructs a LocalizationManager object with the specified Plugin instance and default language.
     *
     * @param plugin          The Bukkit Plugin instance associated with the LocalizationManager.
     * @param defaultLanguage The default language represented as a language tag (e.g., "en-US").
     */
    public LocalizationManager(Plugin plugin, String defaultLanguage) {
        this.plugin = plugin;
        this.defaultLanguage = defaultLanguage;
        this.languageBundles = new HashMap<>();
        loadLanguageBundles();
    }

    /**
     * Loads language bundles from the "lang" folder within the plugin's data folder.
     * It loads the default language bundle and any other available language bundles.
     * If a language bundle fails to load, a warning is logged to the plugin's logger.
     */
    private void loadLanguageBundles() {
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        File langFolder = new File(dataFolder, "lang");
        if (!langFolder.exists()) {
            langFolder.mkdir();
        }

        // Load default language bundle
        Locale defaultLocale = Locale.forLanguageTag(defaultLanguage);
        ResourceBundle defaultBundle = ResourceBundle.getBundle("lang/messages", defaultLocale);
        languageBundles.put(defaultLocale, defaultBundle);

        // Load other language bundles
        try {
            Files.list(langFolder.toPath())
                    .filter(path -> path.getFileName().toString().endsWith(".properties"))
                    .forEach(path -> {
                        String fileName = path.getFileName().toString();
                        String languageTag = fileName.substring(0, fileName.lastIndexOf('.'));
                        Locale locale = Locale.forLanguageTag(languageTag);
                        try {
                            ResourceBundle bundle = ResourceBundle.getBundle("lang/messages", locale);
                            languageBundles.put(locale, bundle);
                        } catch (Exception e) {
                            plugin.getLogger().warning("Failed to load language bundle for: " + languageTag);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the localized message for the specified locale and key.
     *
     * @param locale The locale for which the message should be retrieved.
     * @param key    The key identifying the message in the language bundle.
     * @return The localized message as a String, or an empty string if the key is not found in the bundle.
     */
    public String getMessage(Locale locale, String key) {
        ResourceBundle bundle = languageBundles.getOrDefault(locale, languageBundles.get(Locale.forLanguageTag(defaultLanguage)));
        return bundle.getString(key);
    }

    /**
     * Gets the localized message for the default language and the specified key.
     *
     * @param key The key identifying the message in the language bundle.
     * @return The localized message as a String, or an empty string if the key is not found in the bundle.
     */
    public String getMessage(String key) {
        ResourceBundle bundle = languageBundles.get(Locale.forLanguageTag(defaultLanguage));
        return bundle.getString(key);
    }
}
