package dev.thrivingstudios.betterAutoReboot;

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

        // Start reboot timer
        rebootTimer = new RebootTimer(this, pluginConfig);
        rebootTimer.startTimer(100);

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
