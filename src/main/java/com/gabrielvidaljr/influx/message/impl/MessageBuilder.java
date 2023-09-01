package com.gabrielvidaljr.influx.message.impl;

import com.gabrielvidaljr.influx.message.InfluxMessageBuilder;
import com.gabrielvidaljr.influx.message.message.InfluxMessage;
import com.gabrielvidaljr.influx.message.message.impl.Message;
import com.gabrielvidaljr.influx.message.registry.MessageRegistry;
import com.gabrielvidaljr.strings.builder.InfluxComponentBuilder;
import com.gabrielvidaljr.strings.builder.impl.ComponentBuilder;
import com.gabrielvidaljr.strings.condition.component.ComponentCondition;
import com.gabrielvidaljr.strings.condition.player.Condition;
import com.gabrielvidaljr.strings.placeholder.Placeholder;
import com.gabrielvidaljr.strings.placeholder.impl.ConditionalPlaceholder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MessageBuilder implements InfluxMessageBuilder {

    private InfluxComponentBuilder componentBuilder;
    private final JavaPlugin plugin;
    private final MessageRegistry registry;

    public MessageBuilder(final JavaPlugin plugin) {
        this.plugin = plugin;
        this.componentBuilder = new ComponentBuilder();
        this.registry = new MessageRegistry();
    }

    @Override
    public JavaPlugin getPlugin() {
        return this.plugin;
    }

    @Override
    public MessageRegistry getRegistry() {
        return this.registry;
    }

    @Override
    public InfluxMessageBuilder setComponentBuilder(InfluxComponentBuilder builder) {
        this.componentBuilder = builder;

        return this;
    }

    @Override
    public InfluxMessageBuilder addCondition(ComponentCondition condition) {
        this.componentBuilder.addCondition(condition);

        return this;
    }

    @Override
    public InfluxMessageBuilder addPlaceholder(Placeholder placeholder) {
        this.componentBuilder.addPlaceholder(placeholder);

        return this;
    }

    @Override
    public InfluxMessageBuilder addConditionPlaceholder(ConditionalPlaceholder placeholder, String or) {
        this.componentBuilder.addConditionalPlaceholder(placeholder, or);

        return this;
    }

    @Override
    public InfluxMessageBuilder cacheMessage(String key, InfluxMessage message) {
        this.getRegistry().register(key, message);

        return this;
    }

    @Override
    public InfluxMessageBuilder cacheMessages(FileConfiguration file, String path) {

        for (final String key : file.getConfigurationSection(path).getKeys(false)) {

            System.out.println(key);

            final String dir = path + "." + key;
            final List<String> messages = file.getStringList(dir + ".message.value");

            System.out.println(messages);

            this.getRegistry().register(key, new Message(file, dir, this.componentBuilder.toComponent(messages)));
        }

        return this;
    }

    @Override
    public InfluxMessage getMessage(String key) {
        return this.getRegistry().get(key).get();
    }
}
