package dev.thrivingstudios.betterAutoReboot;

import dev.thrivingstudios.betterAutoReboot.enums.RebootTypeEnum;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class RebootTimer {

    private final BetterAutoReboot betterAutoReboot;
    private final PluginConfig pluginConfig;

    private BukkitTask timerTask;

    public RebootTimer(BetterAutoReboot betterAutoReboot, PluginConfig pluginConfig) {
        this.betterAutoReboot = betterAutoReboot;
        this.pluginConfig = pluginConfig;
    }

    public boolean startTimer(int durationSeconds, RebootTypeEnum rebootType, String rebootReason) {
        if (timerTask != null) {
            return false;
        }

        timerTask = new BukkitRunnable() {
            int count = durationSeconds;

            @Override
            public void run() {
                if (pluginConfig.ALERT_MESSAGE_INTERVALS.contains(Integer.toString(count))) {
                    // Send reboot alert message
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle("§c§lReboot!", String.format("§7Rebooting in §f%s§7.", DateUtils.formatCountSimple(count)));

                        player.sendMessage("");
                        if (rebootReason != null) {
                            player.sendMessage(String.format("§c[Reboot] §7The server is rebooting in §f%s §7for §f%s§7.", DateUtils.formatCountFull(count), rebootReason));
                        } else {
                            player.sendMessage(String.format("§c[Reboot] §7Server is rebooting in §f%s§7.", DateUtils.formatCountFull(count)));
                        }
                        player.sendMessage("");
                    }
                }

                // Reboot counter
                count--;

                if (count == 0) {
                    // Reboot the server
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage("");
                        player.sendMessage("§c[Reboot] §7Server is rebooting.");
                        player.sendMessage("");
                    }

                    System.out.println("/restart"); // Placeholder

                    // Stop the bukkit runnable
                    cancel();
                }
            }
        }.runTaskTimer(JavaPlugin.getPlugin(BetterAutoReboot.class), 0, 20L);

        return true;
    }

    public boolean stopTimer() {
        if (timerTask != null) {
            // Stop the server reboot timer
            timerTask.cancel();
            timerTask = null;

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendTitle("§c§lReboot!", "§7Reboot §fstopped§7.");

                player.sendMessage("");
                player.sendMessage("§c[Reboot] §7The server reboot has been §fstopped §7by an administrator.");
                player.sendMessage("");
            }

            return true;
        } else {
            return false;
        }
    }
}
