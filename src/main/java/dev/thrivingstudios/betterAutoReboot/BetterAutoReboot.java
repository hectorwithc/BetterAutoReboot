package dev.thrivingstudios.betterAutoReboot;

import dev.thrivingstudios.betterAutoReboot.commands.RebootCommand;
import dev.thrivingstudios.betterAutoReboot.enums.RebootTypeEnum;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterAutoReboot extends JavaPlugin {

    private PluginConfig pluginConfig;
    private RebootTimer rebootTimer;

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Save default config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Load config
        pluginConfig = new PluginConfig(this);
        pluginConfig.loadConfig();

        // Load reboot timer
        rebootTimer = new RebootTimer(this, pluginConfig);

        // Register commands
        getCommand("reboot").setExecutor(new RebootCommand(rebootTimer));

        getLogger().info("Plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Plugin has been disabled!");
    }

    public JavaPlugin getPlugin() {
        return this.getPlugin();
    }
}
