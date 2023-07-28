GeneralSpigotHelper

GeneralSpigotHelper
Overview

GeneralSpigotHelper is a library packed with a multitude of helpers designed to speed up the plugin-making process in Spigot-based Minecraft plugins. It provides various utility classes and methods to simplify common tasks like registering commands, event listeners, working with configuration files, and more.
Installation

To use GeneralSpigotHelper in your project, you can add it as a dependency through JitPack. Follow the steps below to add the library to your project:

Step 1: Add the JitPack repository to your pom.xml:

xml

<repositories>
   <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
   </repository>
</repositories>

Step 2: Add the GeneralSpigotHelper dependency:

xml

<dependency>
   <groupId>com.github.Fotohh</groupId>
   <artifactId>GeneralSpigotHelper</artifactId>
   <version>1.0.1</version>
</dependency>

Utility Classes
GeneralUtility

A utility class providing various helper methods for managing configuration files and their defaults.

Constructor

java

public GeneralUtility(YMLFile file, Defaults prefix, Defaults noPermission, Defaults invalidPlayer, Defaults senderNotPlayer)

    file: The YMLFile representing the configuration file.
    prefix: The default value for the prefix.
    noPermission: The default value for the "no permission" case.
    invalidPlayer: The default value for the "invalid player" case.
    senderNotPlayer: The default value for the "sender is not a player" case.

Methods

java

public String getPath()

Gets the path that was set in the enum class.

java

public Object getDefaultValue()

Gets the default value from the object that was set in the enum class.

java

public Defaults[] getValues()

Typically, this would be filled in with Enum.getValues();, which returns an array of the Enums set.

java

public Defaults instance()

Create an instance of the Defaults class to then be used inside YMLFile.

Usage Example:

java

YMLFile configFile = new YMLFile("config.yml");
GeneralUtility utility = new GeneralUtility(configFile, Defaults.PREFIX, Defaults.NO_PERMISSION, Defaults.INVALID_PLAYER, Defaults.SENDER_NOT_PLAYER);

ItemBuilder

A utility class to create custom ItemStack objects with title, lore, and enchantments.

Constructor

java

public ItemBuilder(Material material)

Constructs a new ItemBuilder with the specified Material.

    material: The Material to set for the ItemStack.

Methods

java

public ItemBuilder withTitle(String title)

Sets the title (display name) of the ItemStack.

    title: The title (display name) to set.

java

public ItemBuilder withLore(String... lore)

Sets the lore of the ItemStack.

    lore: An array of strings representing the lore lines to set.

java

public ItemBuilder addEnchantment(EnchantmentBuilder enchantmentBuilder)

Adds an enchantment to the ItemStack.

    enchantmentBuilder: The EnchantmentBuilder object representing the enchantment to add.

java

public ItemBuilder addEnchantments(EnchantmentBuilder... enchantmentBuilder)

Adds multiple enchantments to the ItemStack.

    enchantmentBuilder: An array of EnchantmentBuilder objects representing the enchantments to add.

java

public ItemBuilder build()

Builds the ItemStack with the specified attributes.

Usage Example:

java

ItemBuilder builder = new ItemBuilder(Material.DIAMOND_SWORD)
    .withTitle("Powerful Sword")
    .withLore("This sword can deal massive damage!")
    .addEnchantment(new EnchantmentBuilder(Enchantment.DAMAGE_ALL, 5, true))
    .build();

EnchantmentBuilder

A utility class for building enchantments.

Constructor

java

public EnchantmentBuilder(Enchantment enchantment, int level, boolean value)

Constructs an EnchantmentBuilder object with the given enchantment, level, and value.

    enchantment: The enchantment to be applied.
    level: The level of the enchantment.
    value: Whether to ignore the level restriction.

Usage Example:

java

EnchantmentBuilder builder = new EnchantmentBuilder(Enchantment.DAMAGE_ALL, 5, true);

YMLFile

A utility class to handle YAML configuration files in a Spigot plugin.

Constructor

java

public YMLFile(String fileName)

Constructs a new YMLFile with the specified file name.

    fileName: The name of the YAML file.

Methods

java

public void save()

Saves the configuration to the file.

java

public void reload()

Reloads the configuration from the file.

java

public ConfigurationSection getDefaults()

Gets the defaults section from the configuration.

java

public boolean contains(String path)

Checks if the configuration contains a certain path.

java

public void set(String path, Object value)

Sets a value in the configuration.

java

public Object get(String path)

Gets a value from the configuration.

Usage Example:

java

YMLFile configFile = new YMLFile("config.yml");
configFile.reload();
if (!configFile.contains("prefix")) {
    configFile.set("prefix", "&e[MyPlugin]");
    configFile.save();
}
String prefix = configFile.get("prefix");

GeneralSpigotHelperAPI

A utility class providing methods to register commands and event listeners in a Spigot-based Minecraft plugin.

Constructor

java

public GeneralSpigotHelperAPI(JavaPlugin plugin)

Constructs a new instance of GeneralSpigotHelperAPI with the specified JavaPlugin instance.

    plugin: The JavaPlugin instance of the plugin using this API.

Methods

java

public void registerCommands(CommandRegister... register)

Registers multiple commands with their respective CommandExecutors.

    register: An array of CommandRegister objects representing the commands to register.

java

public void registerListeners(Listener... listeners)

Registers multiple event listeners with the plugin's event manager.

    listeners: An array of Listener objects representing the event listeners to register.

Usage Example:

java

CommandExecutor myCommandExecutor = ...; // Your CommandExecutor implementation
String commandName = "mycommand";
GeneralSpigotHelperAPI.CommandRegister commandRegister = new GeneralSpigotHelperAPI.CommandRegister(myCommandExecutor, commandName);
GeneralSpigotHelperAPI api = new GeneralSpigotHelperAPI(myPlugin);
api.registerCommands(commandRegister);

License

This library is licensed under the MIT License.
