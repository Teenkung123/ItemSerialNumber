package com.teenkung.itemserialnumber.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class SerialTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(@SuppressWarnings("NullableProblems") CommandSender sender, @SuppressWarnings("NullableProblems") Command command, @SuppressWarnings("NullableProblems") String label, String[] args) {
        ArrayList<String> result = new ArrayList<>();
        if (args.length == 0 && sender.hasPermission("isn.use")) {
            result.add("register");
            result.add("unregister");
            result.add("lock");
            result.add("unlock");
            result.add("info");
            if (sender.hasPermission("isn.admin")) {
                result.add("admin");
            }
        } else if (args[0].equalsIgnoreCase("admin") && sender.hasPermission("isn.admin")) {
            if (args.length == 1) {
                result.add("lock");
                result.add("register");
                result.add("unlock");
                result.add("unregister");
            } else if (args[1].equalsIgnoreCase("lock") || args[1].equalsIgnoreCase("register")) {
                if (args.length == 2) {
                    result.add("[Boolean] canUnlock|[Boolean] canPunish|[Boolean] allowAlerts");
                    result.add("true|true|true");
                    result.add("true|true|false");
                    result.add("true|false|true");
                    result.add("true|false|false");
                    result.add("false|true|true");
                    result.add("false|true|false");
                    result.add("false|false|true");
                    result.add("false|false|false");
                }
            }
        }
        return result;
    }
}
