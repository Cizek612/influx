package com.gabrielvidaljr.influx.logger;

import com.gabrielvidaljr.influx.InfluxSpigotPlugin;
import com.gabrielvidaljr.strings.builder.impl.ComponentBuilder;

import java.util.List;
import java.util.logging.Level;

public class InfluxLogger {

    public static ComponentBuilder COMPONENT_BUILDER = new ComponentBuilder();

    public static void log(final Level level, final String... messages) {
        for (final String message : messages) {
            InfluxSpigotPlugin.getInstance().getLogger().log(level, "[Influx] " + message);
        }
    }

    public static void log(final String... messages) {
        for (final String message : messages) {
            InfluxSpigotPlugin.getInstance().getLogger().info("[Influx] " + message);
        }
    }

    public static void log(final List<String> messages) {
        for (final String message : messages) {
            InfluxSpigotPlugin.getInstance().getLogger().info("[Influx] " + message);
        }
    }

    public static void console(final String... strings) {
        for (final String line : strings) {
            InfluxSpigotPlugin.getInstance().getServer().getConsoleSender().sendMessage(InfluxLogger.COMPONENT_BUILDER.toComponent("&3[Influx] &b" + line).parseString());
        }
    }
}
