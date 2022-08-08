package com.teenkung.itemserialnumber;

import java.util.ArrayList;

import static com.teenkung.itemserialnumber.ItemSerialNumber.colorize;

public class ConfigLoader {

    private static Boolean cancelEvent;
    private static Boolean removeItem;
    private static Double reduceHealthPercentage;
    private static Double reduceHealth;
    private static ArrayList<String> Command;
    //==================================================================================
    private static Boolean lightningVisual;
    private static Boolean fireworkVisual;
    private static Boolean playSoundVisual;
    //==================================================================================
    private static Boolean alertsEnabled;
    private static Integer alertsSendEvery;
    private static String alertsMessage;
    //==================================================================================
    private static Boolean blockMessageEnabled;
    private static Integer blockMessageSendEvery;
    private static String blockMessage;

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
        Command = new ArrayList<>(Instance.getConfig().getStringList("Punishments.Commands"));
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
    }

    public static Boolean getCancelEvent() { return cancelEvent; }
    public static Boolean getRemoveItem() { return removeItem; }
    public static Double getReduceHealthPercentage() { return reduceHealthPercentage; }
    public static Double getReduceHealth() { return reduceHealth; }
    public static ArrayList<String> getCommand() { return Command; }

    public static Boolean getLightningVisual() { return lightningVisual; }
    public static Boolean getFireworkVisual() { return fireworkVisual; }
    public static Boolean getPlaySoundVisual() { return playSoundVisual; }

    public static Boolean getAlertsEnabled() { return alertsEnabled; }
    public static Integer getAlertsSendEvery() { return alertsSendEvery; }
    public static String getAlertsMessage() { return alertsMessage; }

    public static Boolean getBlockMessageEnabled() { return blockMessageEnabled; }
    public static Integer getBlockMessageSendEvery() { return blockMessageSendEvery; }
    public static String getBlockMessageMessage() { return blockMessage; }

}
