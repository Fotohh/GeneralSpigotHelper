package com.github.fotohh.utility;

import com.github.fotohh.file.Defaults;
import com.github.fotohh.file.YMLFile;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GeneralUtility {

    private final YMLFile file;
    private final Defaults prefix, noPermission, invalidPlayer, senderNotPlayer;

    public GeneralUtility(YMLFile file, Defaults prefix, Defaults noPermission, Defaults invalidPlayer, Defaults senderNotPlayer) {
        this.file = file;
        this.prefix = prefix;
        this.noPermission = noPermission;
        this.invalidPlayer = invalidPlayer;
        this.senderNotPlayer = senderNotPlayer;
        if(file.getDefaults() == null) throw new RuntimeException("No defaults were found! " + file.getPath());
    }

    /**
     * Send message with prefix
     * @param player the target to send the message to
     */
    public void message(Player player, Defaults defaults){
        player.sendMessage(chat(file.get(prefix, defaults)));
    }

    /**
     * Send message with prefix
     * @param player the target to send the message to
     * @param placeholders the placeholders you want to set
     */
    public void message(Player player, Defaults defaults, Object... placeholders){
        player.sendMessage(chat(String.format(file.get(prefix, defaults))));
    }

    /**
     * Send a message without the prefix added before the message
     * @param player the player to send the message to
     */
    public void message(Defaults defaults, Player player){
        player.sendMessage(chat(file.get(defaults)));
    }

    /**
     *
     * @param player the target to check
     * @param perms perms to check
     * @return true if player has perms, false if not
     */
    public boolean checkPermission(@NotNull Player player, String... perms) {
        if(perms != null) {
            for (String perm : perms) {
                if (perm != null && !player.hasPermission(perm)) {
                    message(player, noPermission);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     *
     * @param sender the target to check
     * @return true if sender is valid, false if not
     */
    public boolean isValid(@NotNull CommandSender sender){
        if(!(sender instanceof Player)){
            message(sender, invalidPlayer);
            return false;
        }
        return true;
    }

    /**
     *
     * @param sender the target to check
     * @param perms perms to check
     * @return true if sender is valid, false if not
     */
    public boolean isValid(@NotNull CommandSender sender, String... perms){

        if(!(sender instanceof Player player)){
            message(sender,senderNotPlayer);
            return false;
        }
        int length = perms.length;
        int i = 0;
        for(String perm : perms) {
            if(player.hasPermission(perm)) i++;
        }
        return i == length;
    }

    /**
     * Checks if target is valid
     * @param player the player to send a message to if the player is invalid
     * @param target the target to check
     * @return true if valid, false if not
     */
    public boolean isTargetValid(Player player, Player target){
        if(target == null || !target.isOnline()){
            message(player, invalidPlayer);
            return false;
        }
        return true;
    }

    /**
     * Checks if target is valid
     * @param player the player to send a message to if the player is invalid
     * @param target the target to check
     * @return true if valid, false if not
     */
    public boolean isTargetValid(Player player, OfflinePlayer target){
        if(target == null){
            message(player, invalidPlayer);
            return false;
        }
        return true;
    }

    /**
     * Send message with prefix
     * @param sender the target to send the message to.
     */
    public void message(CommandSender sender, Defaults message){
        sender.sendMessage(chat( file.get(prefix) + message));
    }

    /**
     * Returns the string color coded using {@link ChatColor#translateAlternateColorCodes(char, String)}.
     * Default is the '&' character. If you want to use your own alternate color code use the method {@link GeneralUtility#chat(String, char)}
     * @param s The message
     * @return the color coded message
     */
    public static @NotNull String chat(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    /**
     * Returns the string color coded using {@link ChatColor#translateAlternateColorCodes(char, String)}.
     * @param s The message
     * @param character The alternate color code to replace.
     * @return the color coded message
     */
    public static @NotNull String chat(String s, char character){
        return ChatColor.translateAlternateColorCodes(character, s);
    }

    /**
     * Send message with prefix
     * @param player the target to send the message to
     */
    public void message(Player player, Defaults message, YMLFile file){
        player.sendMessage(chat( file.get(prefix)+message));
    }

    /**
     * Send message with prefix
     * @param player the target to send the message to
     * @param placeholders the placeholders you want to set
     */
    public void message(Player player, Defaults identifier, YMLFile file, Object... placeholders){
        player.sendMessage(chat(String.format(file.get(prefix,identifier), placeholders)));
    }

    /**
     * Send a message without the prefix added before the message
     * @param player the player to send the message to
     */
    public void message(Defaults identifier, YMLFile file, Player player){
        player.sendMessage(chat(file.get(identifier)));
    }

    /**
     *
     * @param player the target to check
     * @param perms perms to check
     * @return true if player has perms, false if not
     */
    public boolean checkPermission(@NotNull Player player, YMLFile file, String... perms) {
        if(perms != null) {
            for (String perm : perms) {
                if (perm != null && !player.hasPermission(perm)) {
                    message(player, noPermission, file);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     *
     * @param sender the target to check
     * @return true if sender is valid, false if not
     */
    public boolean isValid(@NotNull CommandSender sender, YMLFile file){

        if(!(sender instanceof Player)){
            message(sender, senderNotPlayer, file);
            return false;
        }

        return true;
    }

    /**
     *
     * @param sender the target to check
     * @param perms perms to check
     * @return true if sender is valid, false if not
     */
    public boolean isValid(@NotNull CommandSender sender, YMLFile file, String... perms){

        if(!(sender instanceof Player player)){
            message(sender, senderNotPlayer, file);
            return false;
        }
        int length = perms.length;
        int i = 0;
        for(String perm : perms) {
            if(checkPermission(player,file, perm)) i++;
        }
        return i == length;
    }

    /**
     * Checks if target is valid
     * @param player the player to send a message to if the player is invalid
     * @param target the target to check
     * @return true if valid, false if not
     */
    public boolean isTargetValid(Player player, Player target, YMLFile file){

        if(target == null || !target.isOnline()){
            message(player, invalidPlayer, file);
            return false;
        }
        return true;
    }

    /**
     * Checks if target is valid
     * @param player the player to send a message to if the player is invalid
     * @param target the target to check
     * @return true if valid, false if not
     */
    public boolean isTargetValid(Player player, OfflinePlayer target, YMLFile file){

        if(target == null){
            message(player, invalidPlayer, file);
            return false;
        }
        return true;
    }

    /**
     * it does not send a message.
     * @param target the target to check
     * @return true if valid, false if not
     */
    public boolean isTargetValid(Player target){
        return target != null && target.isOnline();
    }

    /**
     * it does not send a message.
     * @param target the target to check
     * @return true if valid, false if not
     */
    public boolean isTargetValid(OfflinePlayer target){
        return target != null;
    }

    /**
     * Send message with prefix
     * @param sender the target to send the message to
     */
    public void message(CommandSender sender, Defaults identifier, YMLFile file){
        sender.sendMessage(chat(file.get(prefix, identifier)));
    }
}
