package com.teenkung.itemserialnumber;

import com.teenkung.itemserialnumber.Commands.Serial;
import com.teenkung.itemserialnumber.Commands.SerialTabComplete;
import com.teenkung.itemserialnumber.Events.RegisterEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ItemSerialNumber extends JavaPlugin {

    private static ItemSerialNumber Instance;


    @Override
    public void onEnable() {
        Instance = this;

        //This part used for Register event listener
        Bukkit.getPluginManager().registerEvents(new RegisterEvent(), this);

        //This part used for Register commands
        PluginCommand command = getCommand("ItemSerialNumber");
        if (command != null) {
            command.setExecutor(new Serial());
            command.setTabCompleter(new SerialTabComplete());
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ItemSerialNumber getInstance() { return Instance; }
    public static String colorize(String message) {
        if (message == null) {
            message = "";
        }
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char c : ch) {
                builder.append("&").append(c);
            }

            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static ArrayList<String> colorizeArray(ArrayList<String> array) {
        ArrayList<String> ret = new ArrayList<>();
        for (String str : array) {
            ret.add(colorize(str));
        }
        return ret;
    }

}
