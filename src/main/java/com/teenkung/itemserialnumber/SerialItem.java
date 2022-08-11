package com.teenkung.itemserialnumber;

import com.google.common.hash.Hashing;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static com.teenkung.itemserialnumber.ItemSerialNumber.colorize;

public class SerialItem {

    private ItemStack stack;
    private NBTItem nbt;
    private UUID owner;
    private UUID holder;
    private long time;
    private boolean canUnlock;
    private boolean canPunish;
    private boolean sendMessage;

    public SerialItem(ItemStack stack) {
        if (stack.getType() != Material.AIR) {
            this.stack = stack;
            nbt = new NBTItem(stack);
            NBTCompound compound = nbt.getCompound("ItemSerialNumber");
            if (nbt.getCompound("ItemSerialNumber") != null) {
                owner = UUID.fromString(compound.getString("owner"));
                holder = UUID.fromString(compound.getString("lastHolder"));
                time = compound.getLong("registerTime");
                canPunish = compound.getBoolean("canPunish");
                canUnlock = compound.getBoolean("canUnlock");
                sendMessage = compound.getBoolean("sendMessage");
            } else {
                time = 0;
                canPunish = false;
                canUnlock = true;
                sendMessage = false;
            }
        }
    }

    public ItemStack getItem() {
        nbt.applyNBT(stack);
        return stack;
    }

    public boolean isRegistered() {
        return nbt.getCompound() != null;
    }

    public String getSerialNumber() {
        return Hashing.sha256().hashLong(time).toString();
    }

    public Long getRegisterTime() {
        return time;
    }

    public Long getMicroRegisterTime() {
        return time;
    }

    public Date getRegisterDate() {
        return new Date(getMicroRegisterTime());
    }

    public UUID getOwnerUUID() {
        return owner;
    }

    public UUID getHolderUUID() {
        return holder;
    }

    public boolean getCanUnlock() {
        return canUnlock;
    }

    public boolean getCanPunish() {
        return canPunish;
    }

    public boolean getSendMessage() {
        return sendMessage;
    }

    public boolean canHold(Player holder) {
        return owner.equals(holder.getUniqueId()) || holder.hasPermission("ISN.Bypass");
    }

    public boolean canRegister() {
        return !nbt.getString("ISN").equals("") || !(nbt.getString("ISN") == null);
    }

    public void registerSerialItem(UUID owner, boolean canUnlock, boolean canPunish, boolean sendMessage) {
        if (nbt.getCompound("ItemSerialNumber") == null) {
            String AlphaNumericString = "0123456789";
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                int index = (int) (AlphaNumericString.length() * Math.random());
                sb.append(AlphaNumericString.charAt(index));
            }

            this.owner = owner;
            holder = owner;
            time = (Instant.now().getEpochSecond() * 1000) + Integer.parseInt(sb.toString());
            this.canUnlock = canUnlock;
            this.canPunish = canPunish;
            this.sendMessage = sendMessage;
            stack = getItem();

            nbt.removeKey("ISN");
            NBTCompound compound = nbt.getOrCreateCompound("ItemSerialNumber");
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

    public boolean unRegisterItem(boolean Bypass) {
        if (Bypass || canUnlock) {
            nbt.removeKey("ItemSerialNumber");
            return true;
        }
        return false;
    }

    public void setOwner(UUID Owner) {
        if (nbt.getCompound("ItemSerialNumber") != null) {
            NBTCompound compound = nbt.getCompound("ItemSerialNumber");
            compound.setString("owner", Owner.toString());
            this.owner = Owner;
            nbt.mergeCompound(compound);
        }
    }
    public void setLastHolder(UUID Holder) {
        if (nbt.getCompound("ItemSerialNumber") != null) {
            NBTCompound compound = nbt.getCompound("ItemSerialNumber");
            compound.setString("lastHolder", Holder.toString());
            this.holder = Holder;
            nbt.mergeCompound(compound);
        }
    }
    public void setRegisterTime(long UnixTime) {
        if (nbt.getCompound("ItemSerialNumber") != null) {
            NBTCompound compound = nbt.getCompound("ItemSerialNumber");
            compound.setLong("registerTime", UnixTime);
            this.time = UnixTime;
            nbt.mergeCompound(compound);
        }
    }
    public void setCanPunish(boolean bool) {
        if (nbt.getCompound("ItemSerialNumber") != null) {
            NBTCompound compound = nbt.getCompound("ItemSerialNumber");
            compound.setBoolean("canPunish", bool);
            this.canPunish = bool;
            nbt.mergeCompound(compound);
        }
    }
    public void setCanUnlock(boolean bool) {
        if (nbt.getCompound("ItemSerialNumber") != null) {
            NBTCompound compound = nbt.getCompound("ItemSerialNumber");
            compound.setBoolean("canUnlock", bool);
            this.canUnlock = bool;
            nbt.mergeCompound(compound);
        }
    }
    public void setSendMessage(boolean bool) {
        if (nbt.getCompound("ItemSerialNumber") != null) {
            NBTCompound compound = nbt.getCompound("ItemSerialNumber");
            compound.setBoolean("sendMessage", bool);
            this.sendMessage = bool;
            nbt.mergeCompound(compound);
        }
    }
    public String getItemName() {
        ItemMeta meta = getItem().getItemMeta();
        if (meta != null) {
            if (meta.getDisplayName().equals("")) {
                return colorize(meta.getLocalizedName());
            } else {
                return colorize(meta.getDisplayName());
            }
        }
        return colorize(getItem().getItemMeta().getLocalizedName());
    }

    public void sendItemInfoToPlayer(Player player) {
        if (nbt.getCompound("ItemSerialNumber") != null) {
            String ownerName = Bukkit.getOfflinePlayer(this.owner).getName();
            String holderName = Bukkit.getOfflinePlayer(this.holder).getName();
            if (ownerName == null) {
                ownerName = "Unknown";
            }
            if (holderName == null) {
                holderName = "Unknown";
            }
            if (player.hasPermission("isn.moreInfo")) {
                for (String message : ConfigLoader.getMessageItemInfoAdmin()) {
                    message = message.replaceAll("<item>", getItemName());
                    message = message.replaceAll("<owner>", ownerName);
                    message = message.replaceAll("<owner UUID>", owner.toString());
                    message = message.replaceAll("<holder>", holderName);
                    message = message.replaceAll("<holder UUID>", holder.toString());
                    message = message.replaceAll("<register time>", String.valueOf(time));
                    message = message.replaceAll("<register date>", new Date(time).toString());
                    message = message.replaceAll("<serial>", getSerialNumber());
                    message = message.replaceAll("<canUnlock>", String.valueOf(canUnlock));
                    message = message.replaceAll("<allowPunishment>", String.valueOf(canPunish));
                    message = message.replaceAll("<alerts>", String.valueOf(sendMessage));
                    player.sendMessage(ConfigLoader.getMessagePrefix() + message);
                }
            } else {
                for (String message : ConfigLoader.getMessageItemInfoDefault()) {
                    message = message.replaceAll("<item>", getItemName());
                    message = message.replaceAll("<owner>", ownerName);
                    message = message.replaceAll("<owner UUID>", owner.toString());
                    message = message.replaceAll("<holder>", holderName);
                    message = message.replaceAll("<holder UUID>", holder.toString());
                    message = message.replaceAll("<register time>", String.valueOf(time));
                    message = message.replaceAll("<register date>", new Date(time).toString());
                    message = message.replaceAll("<serial>", getSerialNumber());
                    message = message.replaceAll("<canUnlock>", String.valueOf(canUnlock));
                    message = message.replaceAll("<allowPunishment>", String.valueOf(canPunish));
                    message = message.replaceAll("<alerts>", String.valueOf(sendMessage));
                    player.sendMessage(ConfigLoader.getMessagePrefix() + message);
                }
            }
        } else {
            player.sendMessage(colorize(ConfigLoader.getMessagePrefix() + ConfigLoader.getMessageSerialNotFound()));
        }
    }

}
