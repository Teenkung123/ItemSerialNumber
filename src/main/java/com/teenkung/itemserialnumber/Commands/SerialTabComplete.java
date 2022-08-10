package com.teenkung.itemserialnumber.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class SerialTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(@SuppressWarnings("NullableProblems") CommandSender sender, @SuppressWarnings("NullableProblems") Command command, @SuppressWarnings("NullableProblems") String label, @SuppressWarnings("NullableProblems") String[] args) {
        return null;
    }
}
