package com.teenkung.itemserialnumber;

import java.util.ArrayList;

import static com.teenkung.itemserialnumber.ItemSerialNumber.colorize;
import static com.teenkung.itemserialnumber.ItemSerialNumber.colorizeArray;

public class ConfigLoader {

    static boolean cancelEvent;
    static boolean removeItem;
    static double reduceHealthPercentage;
    static double reduceHealth;
    static ArrayList<String> Command;
    //==================================================================================
    static boolean lightningVisual;
    static boolean fireworkVisual;
    static boolean playSoundVisual;
    //==================================================================================
    static boolean alertsEnabled;
    static int alertsSendEvery;
    static String alertsMessage;
    //==================================================================================
    static boolean blockMessageEnabled;
    static int blockMessageSendEvery;
    static String blockMessage;
    //==================================================================================
    static String messagePrefix;
    static String messageNoPermission;
    static String messageSuccessRegister;
    static String messageSuccessUnregister;
    static ArrayList<String> messageItemInfoAdmin;
    static ArrayList<String> messageItemInfoDefault;
    static String messageSerialNotFound;

    public static void generateConfigFile() {
        System.out.println(colorize("&aChecking and Generating Config File"));
        ItemSerialNumber.getInstance().getConfig().options().copyDefaults();
        ItemSerialNumber.getInstance().saveDefaultConfig();
        System.out.println("&aCompleted Checking and Generate Config File!");
    }

    public static void loadConfigurations() {
        ItemSerialNumber Instance = ItemSerialNumber.getInstance();
        ItemSerialNumber.getInstance().reloadConfig();
        cancelEvent = Instance.getConfig().getBoolean("Punishments.CancelEvent", true);
        removeItem = Instance.getConfig().getBoolean("Punishments.RemoveItem", false);
        reduceHealthPercentage = Instance.getConfig().getDouble("Punishments.ReduceHealthPercentage", 0);
        reduceHealth = Instance.getConfig().getDouble("Punishments.ReduceHealth", 0);
        Command = colorizeArray(new ArrayList<>(Instance.getConfig().getStringList("Punishments.Commands")));
        //==================================================================================
        lightningVisual = Instance.getConfig().getBoolean("PunishmentVisual.Lightning", true);
        fireworkVisual = Instance.getConfig().getBoolean("PunishmentVisual.Firework", true);
        playSoundVisual = Instance.getConfig().getBoolean("PunishmentVisual.PlaySound", true);
        //==================================================================================
        alertsEnabled = Instance.getConfig().getBoolean("Alerts.Enable", true);
        alertsSendEvery = Instance.getConfig().getInt("Alerts.SendEvery", 10);
        alertsMessage = Instance.getConfig().getString(colorize("Alerts.AlertMessage"), "");
        //==================================================================================
        blockMessageEnabled = Instance.getConfig().getBoolean("BlockMessage.Enable", true);
        blockMessageSendEvery = Instance.getConfig().getInt("BlockMessage.SendEvery", 10);
        blockMessage = Instance.getConfig().getString(colorize("BlockMessage.BlockMessage"), "");
        //==================================================================================
        messagePrefix = Instance.getConfig().getString("Message.Prefix", "&7[&6ISN&7] &r");
        messageNoPermission = Instance.getConfig().getString("Message.noPermission", colorize("&cYou do not have permission to perform this command!"));
        messageSuccessRegister = Instance.getConfig().getString("Message.successRegister", colorize("&aSuccessfully Registered this Item to Serial System!"));
        messageSuccessUnregister = Instance.getConfig().getString("Message.successUnregister", colorize("&cSuccessfully Unregistered this Item from Serial System!"));
        messageItemInfoAdmin = colorizeArray(new ArrayList<>(Instance.getConfig().getStringList("Message.ItemInfo-Admin")));
        messageItemInfoDefault = colorizeArray(new ArrayList<>(Instance.getConfig().getStringList("Message.ItemInfo-Default")));
        messageSerialNotFound = Instance.getConfig().getString("Message.serialNotFound", colorize("&cSerial Number Not Found from this item"));
    }

    public static boolean getCancelEvent() { return cancelEvent; }
    public static boolean getRemoveItem() { return removeItem; }
    public static double getReduceHealthPercentage() { return reduceHealthPercentage; }
    public static double getReduceHealth() { return reduceHealth; }
    public static ArrayList<String> getCommand() { return Command; }

    public static boolean getLightningVisual() { return lightningVisual; }
    public static boolean getFireworkVisual() { return fireworkVisual; }
    public static boolean getPlaySoundVisual() { return playSoundVisual; }

    public static boolean getAlertsEnabled() { return alertsEnabled; }
    public static int getAlertsSendEvery() { return alertsSendEvery; }
    public static String getAlertsMessage() { return alertsMessage; }

    public static boolean getBlockMessageEnabled() { return blockMessageEnabled; }
    public static int getBlockMessageSendEvery() { return blockMessageSendEvery; }
    public static String getBlockMessageMessage() { return blockMessage; }

    public static String getMessagePrefix() { return messagePrefix; }
    public static String getMessageNoPermission() { return messageNoPermission; }
    public static String getMessageSuccessRegister() { return messageSuccessRegister; }
    public static String getMessageSuccessUnregister() { return messageSuccessUnregister; }
    public static ArrayList<String> getMessageItemInfoAdmin() { return messageItemInfoAdmin; }
    public static ArrayList<String> getMessageItemInfoDefault() { return messageItemInfoDefault; }
    public static String getMessageSerialNotFound() { return messageSerialNotFound; }

}
