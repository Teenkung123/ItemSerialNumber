package com.teenkung.itemserialnumber.Commands;

import com.teenkung.itemserialnumber.ConfigLoader;
import com.teenkung.itemserialnumber.SerialItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

import static com.teenkung.itemserialnumber.ItemSerialNumber.colorize;

public class SerialCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, @SuppressWarnings("NullableProblems") Command command, @SuppressWarnings("NullableProblems") String label, @SuppressWarnings("NullableProblems") String[] args) {
        if (!sender.hasPermission("isn.use")) {
            sender.sendMessage(colorize(ConfigLoader.getMessagePrefix() + ConfigLoader.getMessageNoPermission()));
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage(colorize("&cInvalid Arguments!"));
            return false;
        }
        String arg = args[0].toLowerCase(Locale.ROOT);
        arg = arg.replace("unregister", "unlock");
        arg = arg.replace("register", "lock");
        switch (arg) {
            case "lock":
                if (sender instanceof Player player) {
                    ItemStack item = player.getInventory().getItemInMainHand();
                    SerialItem serial = new SerialItem(item);
                    if (serial.isRegistered()) {
                        player.sendMessage(ConfigLoader.getMessagePrefix() + ConfigLoader.getMessageItemAlreadyHaveSerial());
                    } else {
                        serial.registerSerialItem(player.getUniqueId(), true, true, false);
                        player.getInventory().setItemInMainHand(serial.getItem());
                        player.sendMessage(ConfigLoader.getMessagePrefix() + ConfigLoader.getMessageSuccessRegister());
                    }
                } else {
                    sender.sendMessage(ConfigLoader.getMessagePrefix() + ConfigLoader.getMessageCommandCanOnlySendByPlayer());
                }
                break;
            case "unlock":
                if (sender instanceof Player player) {
                    ItemStack item = player.getInventory().getItemInMainHand();
                    SerialItem serial = new SerialItem(item);
                    if (serial.isRegistered()) {
                        boolean result = serial.unRegisterItem(false);
                        player.getInventory().setItemInMainHand(serial.getItem());
                        if (result) {
                            player.sendMessage(ConfigLoader.getMessagePrefix() + ConfigLoader.getMessageSuccessUnregister());
                        } else {
                            player.sendMessage(ConfigLoader.getMessagePrefix() + ConfigLoader.getMessageFailedToUnregister());
                        }
                    } else {
                        player.sendMessage(ConfigLoader.getMessagePrefix() + ConfigLoader.getMessageSerialNotFound());
                    }
                }else {
                    sender.sendMessage(ConfigLoader.getMessagePrefix() + ConfigLoader.getMessageCommandCanOnlySendByPlayer());
                }
                break;
            case "info":
                if (sender instanceof Player player) {
                    ItemStack item = player.getInventory().getItemInMainHand();
                    SerialItem serial = new SerialItem(item);
                    serial.sendItemInfoToPlayer(player);
                }else {
                    sender.sendMessage(ConfigLoader.getMessagePrefix() + ConfigLoader.getMessageCommandCanOnlySendByPlayer());
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
