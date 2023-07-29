package com.github.fotohh;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The PermissionManager class provides utility methods for handling player permissions in Bukkit.
 * It allows checking, granting, and revoking permissions for players.
 */
public class PermissionManager {

    private final JavaPlugin plugin;

    public PermissionManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Checks if a player has a specific permission node.
     *
     * @param player        The Player to check for permissions.
     * @param permissionNode The permission node to check.
     * @return true if the player has the specified permission, false otherwise.
     */
    public boolean hasPermission(Player player, String permissionNode) {
        return player.hasPermission(permissionNode);
    }

    /**
     * Grants a specific permission node to a player.
     *
     * @param player        The Player to grant the permission.
     * @param permissionNode The permission node to grant.
     */
    public void givePermission(Player player, String permissionNode) {
        player.addAttachment(plugin).setPermission(permissionNode, true);
    }

    /**
     * Revokes a specific permission node from a player.
     *
     * @param player        The Player from whom to revoke the permission.
     * @param permissionNode The permission node to revoke.
     */
    public void revokePermission(Player player, String permissionNode) {
        player.addAttachment(plugin).unsetPermission(permissionNode);
    }
}
