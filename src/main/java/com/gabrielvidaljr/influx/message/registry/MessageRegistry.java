package com.gabrielvidaljr.influx.message.registry;

import com.gabrielvidaljr.influx.message.message.InfluxMessage;
import com.gabrielvidaljr.influx.patterns.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class MessageRegistry implements Registry<String, InfluxMessage> {

    private final Map<String, InfluxMessage> registry;

    public MessageRegistry() {
        this.registry = new LinkedHashMap<>();
    }

    @Override
    public Map<String, InfluxMessage> getRegistry() {
        return this.registry;
    }
}
