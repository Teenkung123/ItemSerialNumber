package com.teenkung.itemserialnumber.Events;

import com.teenkung.itemserialnumber.SerialItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RegisterEvent implements Listener {

    @EventHandler
    public void onHold(PlayerItemHeldEvent event) {
        Inventory inv = event.getPlayer().getInventory();
        ItemStack item = inv.getItem(event.getPreviousSlot());
        if (item != null && item.getType() != Material.AIR) {
            SerialItem serial = new SerialItem(item);
            if (serial.canRegister()) {
                serial.registerSerialItemByISNNBT(event.getPlayer().getUniqueId());
                inv.setItem(event.getPreviousSlot(), serial.getItem());
            }
        }
    }

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item != null) {
            SerialItem serial = new SerialItem(item);
            if (serial.canRegister()) {
                serial.registerSerialItemByISNNBT(event.getWhoClicked().getUniqueId());
                event.setCurrentItem(serial.getItem());
            }
        }
    }

}
