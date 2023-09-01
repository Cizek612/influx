package com.gabrielvidaljr.influx;

import com.gabrielvidaljr.influx.listener.PlayerJoin;
import com.gabrielvidaljr.influx.logger.InfluxLogger;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class InfluxSpigotPlugin extends JavaPlugin {

    public static InfluxSpigotPlugin instance;

    @Override
    public void onEnable() {
        InfluxSpigotPlugin.instance = this;

        this.saveDefaultConfig();

        InfluxLogger.console("&b&lTEST!");

        new PlayerJoin(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static InfluxSpigotPlugin getInstance() {
        return InfluxSpigotPlugin.instance;
    }
}
