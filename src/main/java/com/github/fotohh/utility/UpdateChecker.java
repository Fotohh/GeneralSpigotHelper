package com.github.fotohh.utility;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * The UpdateChecker class is used to check for updates of a plugin by comparing its version with the latest version available on Spigot.
 *
 * @since 1.0.5
 */
public class UpdateChecker {

    private final int resourceID;
    private final String pluginVersion;
    private final JavaPlugin plugin;
    private final Logger logger;
    private static String apiUrl = "https://api.spiget.org/v2/resources/%d/versions/latest";
    private String upToDatePluginMessage;
    private String outdatedPluginMessage;

    /**
     * Constructs an UpdateChecker object to check for updates of the plugin with the given resource ID and URL.
     *
     * @param resourceID   The Spigot resource ID of the plugin.
     * @param resourceURL  The URL where the updated version of the plugin can be downloaded.
     * @param plugin       The JavaPlugin instance of the plugin.
     * @param loggerName   The name of the logger to be used for logging update information. If null, a default logger name will be used.
     * @since 1.0.5
     */
    public UpdateChecker(int resourceID, String resourceURL, JavaPlugin plugin, String loggerName) {
        this.resourceID = resourceID;
        this.pluginVersion = plugin.getDescription().getVersion();
        this.plugin = plugin;
        upToDatePluginMessage = plugin.getName() + " is up to date";
        outdatedPluginMessage = plugin.getName() + " is outdated! Download the new update from " + resourceURL;
        if (loggerName != null) {
            logger = Logger.getLogger(loggerName);
        } else {
            logger = Logger.getLogger(plugin.getName() + " UpdateChecker");
        }
        runAsync();
    }

    /**
     * Sets the message to be logged when the plugin is up-to-date.
     *
     * @param upToDatePluginMessage The message to be logged when the plugin is up-to-date.
     * @since 1.0.5
     */
    public void setUpToDatePluginMessage(String upToDatePluginMessage) {
        this.upToDatePluginMessage = upToDatePluginMessage;
    }

    /**
     * Sets the message to be logged when the plugin is outdated, and a new update is available.
     *
     * @param outdatedPluginMessage The message to be logged when the plugin is outdated.
     * @since 1.0.5
     */
    public void setOutdatedPluginMessage(String outdatedPluginMessage) {
        this.outdatedPluginMessage = outdatedPluginMessage;
    }

    /**
     * Gets the logger used for logging update information.
     *
     * @return The logger used for logging update information.
     * @since 1.0.5
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Runs the update check asynchronously to avoid blocking the main thread.
     *
     * @since 1.0.5
     */
    public void runAsync() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String webVersion;
            try {
                webVersion = requestServerVersion(fetchURL());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (isUpToDate(parseVersion(pluginVersion), parseVersion(webVersion))) {
                logger.info(upToDatePluginMessage);
            } else {
                logger.warning(outdatedPluginMessage);
            }
        });
    }

    /**
     * Parses the version string into a list of doubles representing version numbers.
     *
     * @param version The version string to be parsed.
     * @return A list of doubles representing version numbers.
     * @since 1.0.5
     */
    public List<Double> parseVersion(String version) {
        List<Double> list = new ArrayList<>();
        String[] values = version.split("\\.");
        for (String val : values) {
            double num = Double.parseDouble(val);
            list.add(num);
        }
        return list;
    }

    /**
     * Checks if the plugin's version is up-to-date compared to the latest version on Spigot.
     *
     * @param parsedPluginVersion The parsed version of the plugin.
     * @param parsedWebVersion    The parsed version of the latest version available on Spigot.
     * @return true if the plugin's version is up-to-date, false otherwise.
     * @since 1.0.5
     */
    public boolean isUpToDate(List<Double> parsedPluginVersion, List<Double> parsedWebVersion) {
        if (parsedWebVersion.size() > parsedPluginVersion.size()) {
            return false;
        }
        for (int i = 0; i < parsedWebVersion.size(); i++) {
            if (parsedWebVersion.get(i) > parsedPluginVersion.get(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Fetches the URL of the latest version from the plugin from the Spigot API.
     *
     * @return The HttpsURLConnection object representing the connection to the Spigot API.
     * @throws IOException If an I/O error occurs while connecting to the Spigot API.
     * @since 1.0.5
     */
    public HttpsURLConnection fetchURL() throws IOException {
        URL url = URI.create(String.format(apiUrl, resourceID)).toURL();
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }

    /**
     * Requests the latest version of the plugin from the Spigot API.
     *
     * @param connection The HttpsURLConnection object representing the connection to the Spigot API.
     * @return The latest version of the plugin as a string.
     * @throws IOException If an I/O error occurs while reading the response from the Spigot API.
     * @since 1.0.5
     */
    public String requestServerVersion(HttpsURLConnection connection) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            JsonObject response = JsonParser.parseReader(in).getAsJsonObject();
            return response.get("name").getAsString();
        }
    }

}
