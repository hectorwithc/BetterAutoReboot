package dev.thrivingstudios.betterAutoReboot.commands;

import dev.thrivingstudios.betterAutoReboot.AlertUtils;
import dev.thrivingstudios.betterAutoReboot.DateUtils;
import dev.thrivingstudios.betterAutoReboot.RebootTimer;
import dev.thrivingstudios.betterAutoReboot.enums.RebootTypeEnum;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RebootCommand implements CommandExecutor {

    private final RebootTimer rebootTimer;

    public RebootCommand(RebootTimer rebootTimer) {
        this.rebootTimer = rebootTimer;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!sender.hasPermission(command.getPermission())) {
            sender.sendMessage(command.getPermissionMessage());
        }

        String subCommand = args[0];

        if (subCommand.equals("now") || subCommand.equals("force")) {
            // Reboot server commands

            if (args.length < 2) {
                sender.sendMessage("§cPlease specify a valid reboot countdown duration in seconds.");

                return true;
            }

            int rebootCountdownDuration;
            try {
                rebootCountdownDuration = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage("§cPlease specify a valid reboot countdown duration in seconds.");

                return true;
            }

            String rebootReason = null;

            if (args.length >= 3) {
                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 2; i < args.length; i++) {
                    stringBuilder.append(args[i]);
                }
            }

            if (subCommand.equals("now")) {
                rebootTimer.startTimer(rebootCountdownDuration, RebootTypeEnum.NOW, rebootReason);
            } else if (subCommand.equals("force")) {
                rebootTimer.startTimer(rebootCountdownDuration, RebootTypeEnum.FORCE, rebootReason);
            }

            AlertUtils.alertServerAdmins(String.format("§f%s §7scheduled a server reboot in %s.", sender.getName(), DateUtils.formatCountFull(rebootCountdownDuration)));

            return true;
        } else if (subCommand.equals("help")) {
            return true;
        } else if (subCommand.equals("version")) {
            return true;
        } else {
            return true;
        }
    }
}
