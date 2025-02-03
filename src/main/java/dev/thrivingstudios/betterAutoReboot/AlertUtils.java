package dev.thrivingstudios.betterAutoReboot;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AlertUtils {
    public static void alertServerAdmins(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("betterautoreboot.alerts")) {
                player.sendMessage(String.format("§c[Reboot] §8(Admin) §7%s", message));
            }
        }
    }
}
