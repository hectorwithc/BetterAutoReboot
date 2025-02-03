package dev.thrivingstudios.betterAutoReboot.commands;

import dev.thrivingstudios.betterAutoReboot.AlertUtils;
import dev.thrivingstudios.betterAutoReboot.DateUtils;
import dev.thrivingstudios.betterAutoReboot.RebootTimer;
import dev.thrivingstudios.betterAutoReboot.enums.RebootTypeEnum;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
            if (args.length > 2) {
                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 2; i < args.length; i++) {
                    stringBuilder.append(args[i] + " ");
                }

                rebootReason = stringBuilder.toString().trim();
            }

            boolean result = false;
            if (subCommand.equals("now")) {
                result = rebootTimer.startTimer(rebootCountdownDuration, RebootTypeEnum.NOW, rebootReason);
            } else if (subCommand.equals("force")) {
                result = rebootTimer.startTimer(rebootCountdownDuration, RebootTypeEnum.FORCE, rebootReason);
            }

            if (result == false) {
                sender.sendMessage("§cThere is already a scheduled server reboot. (Use: /reboot stop)");

                return true;
            }

            if (rebootReason != null) {
                AlertUtils.alertServerAdmins(String.format("§f%s §7scheduled a server reboot in §e%s §7for §6%s§7.", sender.getName(), DateUtils.formatCountFull(rebootCountdownDuration), rebootReason));
            } else {
                AlertUtils.alertServerAdmins(String.format("§f%s §7scheduled a server reboot in §e%s§7.", sender.getName(), DateUtils.formatCountFull(rebootCountdownDuration)));
            }

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (subCommand.equals("now")) {
                    if (rebootReason != null) {
                        player.sendMessage("");
                        player.sendMessage(String.format("§c[Reboot] §7An administrator has initiated a server reboot in §f%s §7for §f%s§7.", DateUtils.formatCountFull(rebootCountdownDuration), rebootReason));
                        player.sendMessage("");
                    } else {
                        player.sendMessage("");
                        player.sendMessage(String.format("§c[Reboot] §7An administrator has initiated a server reboot in §f%s§7.", DateUtils.formatCountFull(rebootCountdownDuration)));
                        player.sendMessage("");
                    }
                } else if (subCommand.equals("force")) {
                    if (rebootReason != null) {
                        player.sendMessage("");
                        player.sendMessage(String.format("§c[Reboot] §7The server is rebooting in §f%s §7for §f%s§7.", DateUtils.formatCountFull(rebootCountdownDuration), rebootReason));
                        player.sendMessage("");
                    } else {
                        player.sendMessage("");
                        player.sendMessage(String.format("§c[Reboot] §7The server is rebooting in §f%s§7.", DateUtils.formatCountFull(rebootCountdownDuration)));
                        player.sendMessage("");
                    }
                }
            }

            return true;
        } else if (subCommand.equals("stop")) {
            boolean result = rebootTimer.stopTimer();

            if (result == true) {
                AlertUtils.alertServerAdmins(String.format("§f%s §7stopped the scheduled server reboot.", sender.getName()));
            } else {
                sender.sendMessage("§cNo reboot scheduled reboot.");
            }

            return true;
        } else if (subCommand.equals("help")) {
            return true;
        } else if (subCommand.equals("version")) {
            return true;
        } else {
            sender.sendMessage("§cInvalid command usage. (Use: /reboot help)");

            return true;
        }
    }
}
