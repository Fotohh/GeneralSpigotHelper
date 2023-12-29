package com.github.fotohh.file;

import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;

/**
 * General class to aid in creating custom YAML files. Also parses different much needed
 * objects from the configuration.
 */
public class YMLFile extends File {

    private final Defaults defaults;

    public Defaults[] getDefaults() {
        return defaults != null ? defaults.getValues() : null;
    }

    private final Yaml config;

    /**
     * Creates a {@link YMLFile} from the corresponding path.
     * @param path The path to the file.
     */
    public YMLFile(String path) {
        this(path, (Defaults) null);
    }

    /**
     * Creates a {@link YMLFile} from the corresponding path along with
     * {@link Defaults}. You can set the defaults by using the method {@link Defaults#instance()}
     * @param path The path to the file.
     * @param defaults defaults of the {@link YMLFile}
     */
    public YMLFile(String path, Defaults defaults) {
        super(path);
        this.defaults = defaults;
        this.config = new Yaml();
        try {
            this.config.load(this);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        if(loadDefaults())
            save();
    }

    public void save(){
        try{
            config.save(this);
        }catch (IOException e){
            throw new RuntimeException("Unable to save file!", e);
        }
    }

    /**
     * Creates a new {@link YMLFile} in the corresponding directory with the file.
     * Keep in mind that the extension is required and not automatically set.
     * @param dir The directory to the file
     * @param fileName The name of the file, extension required.
     */
    public YMLFile(String dir, String fileName) {
        this(new File(dir, fileName), null);
    }

    /**
     * Creates a new {@link YMLFile} in the parent folder.
     * @param parent The parent file.
     * @param fileName The name of the file, extension required.
     */
    public YMLFile(File parent, String fileName) {
        this(parent.getPath(), fileName, null);
    }

    /**
     * Creates a new {@link YMLFile} in the corresponding path along with {@link Defaults}.
     * You can set the defaults by using {@link Defaults#instance()}
     * @param path The path to the file.
     * @param fileName The name of the file, extension required.
     * @param defaults defaults of the {@link YMLFile}
     */
    public YMLFile(String path, String fileName, Defaults defaults) {
        this(new File(path, fileName).getPath(), defaults);
    }

    /**
     * Loads default values from the specified Defaults object into the YamlConfiguration.
     * If no Defaults object is provided, the method returns false indicating that no changes were made.
     *
     * @return true if any changes were made to the configuration, false otherwise.
     */
    private boolean loadDefaults() {
        if (defaults == null) return false;
        boolean changed = false;
        for (Defaults defaultClass : defaults.getValues()) {
            if (!config.contains(defaultClass.getPath())) {
                config.set(defaultClass.getPath(), defaultClass.getDefaultValue());
                changed = true;
            }
        }
        return changed;
    }

    /**
     * Gets the YamlConfiguration object associated with this YMLFile instance.
     *
     * @return The YamlConfiguration object representing the configuration of this YMLFile.
     */
    public Yaml getConfig() {
        return config;
    }

    public String fromPath(Defaults defaults){
        return config.get(defaults.getPath()).toString();
    }

    public String get(Defaults... defaults){
        StringBuilder stringBuilder = new StringBuilder();
        for(Defaults defaultClass : defaults){
            stringBuilder.append(fromPath(defaultClass)).toString();
        }
        return stringBuilder.toString();
    }
}
