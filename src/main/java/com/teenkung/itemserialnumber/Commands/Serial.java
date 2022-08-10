package com.teenkung.itemserialnumber.Commands;

import com.teenkung.itemserialnumber.SerialItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

import static com.teenkung.itemserialnumber.ItemSerialNumber.colorize;

public class Serial implements CommandExecutor {
    @Override
    public boolean onCommand(@SuppressWarnings("NullableProblems") CommandSender sender, @SuppressWarnings("NullableProblems") Command command, @SuppressWarnings("NullableProblems") String label, String[] args) {
        String arg = args[0].toLowerCase(Locale.ROOT);
        switch(arg) {
            case "lock":
                if (sender instanceof Player player) {
                    ItemStack item = player.getInventory().getItemInMainHand();
                    SerialItem serial = new SerialItem(item);
                    serial.registerSerialItem(player.getUniqueId(), true, true, false);
                    player.getInventory().setItemInMainHand(serial.getItem());
                } else {
                    sender.sendMessage(colorize("&cThis command can only send by Player!"));
                }
                break;
            case "unlock":
                if (sender instanceof Player player) {
                    ItemStack item = player.getInventory().getItemInMainHand();
                    SerialItem serial = new SerialItem(item);
                    serial.unRegisterItem();
                    player.getInventory().setItemInMainHand(serial.getItem());
                }else {
                    sender.sendMessage(colorize("&cThis command can only send by Player!"));
                }
                break;
            case "info":
                if (sender instanceof Player player) {
                    ItemStack item = player.getInventory().getItemInMainHand();
                    SerialItem serial = new SerialItem(item);
                    serial.sendItemInfoToPlayer(player);
                }else {
                    sender.sendMessage(colorize("&cThis command can only send by Player!"));
                }
                break;
            case "admin":
                SerialAdmin.receiveCommand(sender, args);
                break;
            default:
        }

        return false;
    }

    /*
    commands:
        /isn lock
        /isn unlock
        /isn info
        /isn admin
            - /isn admin lock <boolean> <boolean> <boolean>
            - /isn admin unlock
            - /isn admin info
     */
}
