package com.gabrielvidaljr.influx.message;

import com.gabrielvidaljr.influx.message.message.InfluxMessage;
import com.gabrielvidaljr.influx.message.registry.MessageRegistry;
import com.gabrielvidaljr.strings.builder.InfluxComponentBuilder;
import com.gabrielvidaljr.strings.condition.component.ComponentCondition;
import com.gabrielvidaljr.strings.condition.player.Condition;
import com.gabrielvidaljr.strings.placeholder.Placeholder;
import com.gabrielvidaljr.strings.placeholder.impl.ConditionalPlaceholder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public interface InfluxMessageBuilder {

    JavaPlugin getPlugin();
    MessageRegistry getRegistry();
    InfluxMessageBuilder setComponentBuilder(final InfluxComponentBuilder builder);
    InfluxMessageBuilder addCondition(final ComponentCondition condition);
    InfluxMessageBuilder addPlaceholder(final Placeholder placeholder);
    InfluxMessageBuilder addConditionPlaceholder(final ConditionalPlaceholder placeholder, final String or);
    InfluxMessageBuilder cacheMessage(final String key, final InfluxMessage message);
    InfluxMessageBuilder cacheMessages(final FileConfiguration file, final String path);
    InfluxMessage getMessage(final String key);
}
