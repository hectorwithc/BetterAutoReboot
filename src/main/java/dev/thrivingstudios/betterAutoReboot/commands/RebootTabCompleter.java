package dev.thrivingstudios.betterAutoReboot.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RebootTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        List<String> results = new ArrayList<>();

        if (args.length == 1) {
            results.add("now");
            results.add("force");
            results.add("help");
            results.add("version");
        } else if (args.length == 2 && (args[0].equals("now") || args[0].equals("force"))) {
            results.add("<duration>");
        } else if (args.length == 3 && (args[0].equals("now") || args[0].equals("force"))) {
            results.add("(reason)");
        }

        if (args.length == 1) {
            return results.stream()
                    .filter(subCommand -> subCommand.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        } else {
            return results;
        }
    }
}
