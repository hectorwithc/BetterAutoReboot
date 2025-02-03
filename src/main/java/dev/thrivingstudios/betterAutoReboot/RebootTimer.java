package dev.thrivingstudios.betterAutoReboot;

import dev.thrivingstudios.betterAutoReboot.enums.RebootTypeEnum;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class RebootTimer {

    private final BetterAutoReboot betterAutoReboot;
    private final PluginConfig pluginConfig;

    public RebootTimer(BetterAutoReboot betterAutoReboot, PluginConfig pluginConfig) {
        this.betterAutoReboot = betterAutoReboot;
        this.pluginConfig = pluginConfig;
    }

    public void startTimer(int durationSeconds, RebootTypeEnum rebootType, String rebootReason) {
        new BukkitRunnable() {
            int count = durationSeconds;

            @Override
            public void run() {
                if (pluginConfig.ALERT_MESSAGE_INTERVALS.contains(Integer.toString(count))) {
                    // Send reboot alert message
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle("§c§lReboot!", "§7Server is rebooting.");

                        player.sendMessage("");
                        player.sendMessage(String.format("§c[Reboot] §7Server is rebooting in §f%s§7!", DateUtils.formatCountFull(count)));
                        player.sendMessage("");
                    }
                }

                // Reboot counter
                count--;

                if (count == 0) {
                    cancel();
                }
            }
        }.runTaskTimer(JavaPlugin.getPlugin(BetterAutoReboot.class), 0, 20L);
    }
}
