package com.teenkung.itemserialnumber;

import com.google.common.hash.Hashing;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.UUID;

import static com.teenkung.itemserialnumber.ItemSerialNumber.colorize;
import static org.bukkit.Bukkit.getName;

public class SerialItem {

    private NBTItem nbt;
    private NBTCompound compound;
    private UUID owner;
    private UUID holder;
    private long time;
    private boolean canUnlock;
    private boolean canPunish;
    private boolean sendMessage;

    public SerialItem(ItemStack stack) {
        if (stack.getType() != Material.AIR) {
            nbt = new NBTItem(stack);
            compound = nbt.getCompound("ItemSerialNumber");
            if (nbt.getCompound("ItemSerialNumber") != null) {
                owner = UUID.fromString(compound.getString("owner"));
                holder = UUID.fromString(compound.getString("lastHolder"));
                time = compound.getLong("registerTime");
                canPunish = compound.getBoolean("canPunish");
                canUnlock = compound.getBoolean("canUnlock");
                sendMessage = compound.getBoolean("sendMessage");
            }
        }
    }

    public ItemStack getItem() { return nbt.getItem(); }
    public String getSerialNumber() { return Hashing.sha256().hashLong(time).toString(); }
    public Long getRegisterTime() { return time; }
    public Long getMicroRegisterTime() { return Double.valueOf(Math.round(time / 1000D)).longValue();}
    public Date getRegisterDate() { return new Date(getMicroRegisterTime()); }
    public UUID getOwnerUUID() { return owner; }
    public UUID getHolderUUID() { return holder; }
    public boolean getCanUnlock() { return canUnlock; }
    public boolean getCanPunish() { return canPunish; }
    public boolean getSendMessage() { return sendMessage; }
    public boolean canHold(Player holder) { return owner.equals(holder.getUniqueId()) || holder.hasPermission("ISN.Bypass"); }
    public boolean canRegister() { return nbt.getString("ISN") != null; }

    public void registerSerialItem(UUID owner, boolean canUnlock, boolean canPunish, boolean sendMessage) {
        if (nbt.getCompound("ItemSerialNumber") != null) {

            this.owner = owner;
            holder = owner;
            time = System.nanoTime();
            this.canUnlock = canUnlock;
            this.canPunish = canPunish;
            this.sendMessage = sendMessage;

            nbt.removeKey("ISN");
            nbt.addCompound("ItemSerialNumber");
            compound.setString("owner", owner.toString());
            compound.setString("lastHolder", owner.toString());
            compound.setLong("registerTime", time);
            compound.setBoolean("canPunish", canPunish);
            compound.setBoolean("canUnlock", canUnlock);
            compound.setBoolean("sendMessage", sendMessage);
        }
    }

    public void registerSerialItemByISNNBT(UUID owner) {
        String[] split = nbt.getString("ISN").split("\\|");
        registerSerialItem(owner, split[0].equalsIgnoreCase("true"), split[1].equalsIgnoreCase("true"), split[2].equalsIgnoreCase("true"));
    }

    public void unRegisterItem() {
        nbt.removeKey("ItemSerialNumber");
    }

    public void setOwner(UUID Owner) {
        if (nbt.getCompound("ItemSerialNumber") != null) {
            compound = nbt.getCompound("ItemSerialNumber");
            compound.setString("owner", Owner.toString());
            this.owner = Owner;
        }
    }
    public void setLastHolder(UUID Holder) {
        if (nbt.getCompound("ItemSerialNumber") != null) {
            compound = nbt.getCompound("ItemSerialNumber");
            compound.setString("lastHolder", Holder.toString());
            this.holder = Holder;
        }
    }
    public void setRegisterTime(long UnixTime) {
        if (nbt.getCompound("ItemSerialNumber") != null) {
            compound = nbt.getCompound("ItemSerialNumber");
            compound.setLong("registerTime", UnixTime);
            this.time = UnixTime;
        }
    }
    public void setCanPunish(boolean bool) {
        if (nbt.getCompound("ItemSerialNumber") != null) {
            compound = nbt.getCompound("ItemSerialNumber");
            compound.setBoolean("canPunish", bool);
            this.canPunish = bool;
        }
    }
    public void setCanUnlock(boolean bool) {
        if (nbt.getCompound("ItemSerialNumber") != null) {
            compound = nbt.getCompound("ItemSerialNumber");
            compound.setBoolean("canUnlock", bool);
            this.canUnlock = bool;
        }
    }
    public void setSendMessage(boolean bool) {
        if (nbt.getCompound("ItemSerialNumber") != null) {
            compound = nbt.getCompound("ItemSerialNumber");
            compound.setBoolean("sendMessage", bool);
            this.sendMessage = bool;
        }
    }
    public String getItemName() {
        ItemMeta meta = nbt.getItem().getItemMeta();
        if (meta != null) {
            if (meta.getDisplayName().equals("")) {
                return colorize(meta.getLocalizedName());
            } else {
                return colorize(meta.getDisplayName());
            }
        }
        return colorize(nbt.getItem().getItemMeta().getLocalizedName());
    }

    public void sendItemInfoToPlayer(Player player) {
        String ownerName = Bukkit.getOfflinePlayer(this.owner).getName();
        String holderName = Bukkit.getOfflinePlayer(this.holder).getName();
        if (ownerName == null) { ownerName = "Unknown"; }
        if (holderName == null) { holderName = "Unknown"; }
        if (player.hasPermission("isn.moreInfo")) {
            for (String message : ConfigLoader.getMessageItemInfoAdmin()) {
                message = message.replaceAll("<item>", getItemName());
                message = message.replaceAll("<owner>", ownerName);
                message = message.replaceAll("<owner UUID>", owner.toString());
                message = message.replaceAll("<holder>", holderName);
                message = message.replaceAll("<holder UUID>", holder.toString());
                message = message.replaceAll("<register time>", String.valueOf(Math.round(time / 1000D)));
                message = message.replaceAll("<register date>", new Date(Double.valueOf(Math.round(time / 1000D)).longValue()).toString());
                message = message.replaceAll("<serial>", getSerialNumber());
                player.sendMessage(message);
            }
        } else {
            for (String message : ConfigLoader.getMessageItemInfoDefault()) {
                message = message.replaceAll("<item>", getItemName());
                message = message.replaceAll("<owner>", ownerName);
                message = message.replaceAll("<owner UUID>", owner.toString());
                message = message.replaceAll("<holder>", holderName);
                message = message.replaceAll("<holder UUID>", holder.toString());
                message = message.replaceAll("<register time>", String.valueOf(Math.round(time / 1000D)));
                message = message.replaceAll("<register date>", new Date(Double.valueOf(Math.round(time / 1000D)).longValue()).toString());
                message = message.replaceAll("<serial>", getSerialNumber());
                player.sendMessage(message);
            }
        }
    }

}
