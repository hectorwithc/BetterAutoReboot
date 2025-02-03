package dev.thrivingstudios.betterAutoReboot;

import java.util.List;

public class PluginConfig {

    private final BetterAutoReboot betterAutoReboot;

    // Config variables
    public List<String> ALERT_MESSAGE_INTERVALS;

    public PluginConfig(BetterAutoReboot betterAutoReboot) {
        this.betterAutoReboot = betterAutoReboot;
    }

    public void loadConfig() {
        // Load variables from config.yml
        ALERT_MESSAGE_INTERVALS = betterAutoReboot.getConfig().getStringList("alert-message-intervals");
    }


}
