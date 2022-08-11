package com.teenkung.itemserialnumber.Events;

import com.teenkung.itemserialnumber.SerialItem;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChangeHolderListener {

    public void InventoryClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if ((item != null && item.getType() != Material.AIR) && !event.getWhoClicked().hasPermission("isn.bypass")) {
            SerialItem serial = new SerialItem(item);
            if (serial.isRegistered()) {
                if (serial.getHolderUUID() != event.getWhoClicked().getUniqueId()) {
                    serial.setLastHolder(event.getWhoClicked().getUniqueId());
                    event.setCurrentItem(serial.getItem());
                }
            }
        }
    }

    public void HandChangeEvent(PlayerItemHeldEvent event) {
        Inventory inv = event.getPlayer().getInventory();
        ItemStack item = inv.getItem(event.getPreviousSlot());
        if ((item != null && item.getType() != Material.AIR) && !event.getPlayer().hasPermission("isn.bypass")) {
            SerialItem serial = new SerialItem(item);
            if (serial.isRegistered()) {
                if (serial.getHolderUUID() != event.getPlayer().getUniqueId()) {
                    serial.setLastHolder(event.getPlayer().getUniqueId());
                    inv.setItem(event.getPreviousSlot(), serial.getItem());
                }
            }
        }
    }

}
