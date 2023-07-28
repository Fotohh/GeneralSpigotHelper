# GeneralSpigotHelper

![GeneralSpigotHelper](https://jitpack.io/v/Fotohh/GeneralSpigotHelper.svg)

## Overview

GeneralSpigotHelper is a library packed with a multitude of helpers designed to speed up the plugin-making process in Spigot-based Minecraft plugins. It provides various utility classes and methods to simplify common tasks like registering commands, event listeners, working with configuration files, and more.

## Installation

To use GeneralSpigotHelper in your project, you can add it as a dependency through [JitPack](https://jitpack.io/#Fotohh/GeneralSpigotHelper/1.0.1). Follow the steps below to add the library to your project:

```xml
<repositories>
   <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
   </repository>
</repositories>
```
```xml
<dependency>
   <groupId>com.github.Fotohh</groupId>
   <artifactId>GeneralSpigotHelper</artifactId>
   <version>1.0.1</version>
</dependency>
```

## Utility Classes

### GeneralUtility

A utility class providing various helper methods for managing configuration files and their defaults.

```java
public GeneralUtility(YMLFile file, Defaults prefix, Defaults noPermission, Defaults invalidPlayer, Defaults senderNotPlayer)
```
```java
public String getPath()
public Object getDefaultValue()
public Defaults[] getValues()
public Defaults instance()
```
### ItemBuilder

A utility class to create custom ItemStack objects with title, lore, and enchantments.

```java
public ItemBuilder(Material material)
```
```java
public ItemBuilder withTitle(String title)
public ItemBuilder withLore(String... lore)
public ItemBuilder addEnchantment(EnchantmentBuilder enchantmentBuilder)
public ItemBuilder addEnchantments(EnchantmentBuilder... enchantmentBuilder)
public ItemBuilder build()
```

### EnchantmentBuilder

A utility class for building enchantments.

```java
public EnchantmentBuilder(Enchantment enchantment, int level, boolean value)
```

### YMLFile

A utility class to handle YAML configuration files in a Spigot plugin.

```java
public YMLFile(String fileName)
```

```java
public void save()
public void reload()
public ConfigurationSection getDefaults()
public boolean contains(String path)
public void set(String path, Object value)
public Object get(String path)
```

## GeneralSpigotHelperAPI

A utility class providing methods to register commands and event listeners in a Spigot-based Minecraft plugin.

```java
public GeneralSpigotHelperAPI(JavaPlugin plugin)
```

```java
public void registerCommands(CommandRegister... register)
public void registerListeners(Listener... listeners)
```

## Usage Example
```java
GeneralSpigotHelperAPI.CommandRegister commandRegister = new GeneralSpigotHelperAPI.CommandRegister(myCommandExecutor, commandName);
GeneralSpigotHelperAPI api = new GeneralSpigotHelperAPI(myPlugin);
api.registerCommands(new GeneralSpigotHelperAPI.CommandRegister(myCommandExecutor, commandName), new GeneralSpigotHelperAPI.CommandRegister(anotherCommandExecutor, commandName));
```
## License

This library is licensed under the [MIT License](LICENSE).
