package com.teenkung.itemserialnumber;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class SerialItem {

    ItemStack original;
    NBTItem nbt;
    NBTCompound compound;

    public SerialItem(ItemStack stack) {
        if (stack.getType() != Material.AIR) {
            nbt = new NBTItem(stack);
            compound = nbt.getCompound("ItemSerialNumber");
        } else {
            nbt = new NBTItem(new ItemStack(Material.STONE));
        }
    }

    public Boolean isRegistered() {
        return nbt.getCompound("ItemSerialNumber") != null;
    }
    

    public void registerSerialItem(RegisterLevel level, UUID owner) {
        if (!isRegistered()) {
            compound = nbt.addCompound("ItemSerialNumber");
            compound.setString("RegisterLevel", level.getString());
            compound.setLong("RegisterTime", System.nanoTime());
            compound.setString("Owner", owner.toString());
            compound.setString("LastHolder", owner.toString());
        }
    }

    public void changeRegisterLevel(RegisterLevel level) {

    }

    public void applyToOriginal() {
        original = nbt.getItem();
    }

}
