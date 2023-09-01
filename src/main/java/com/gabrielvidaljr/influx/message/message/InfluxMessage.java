package com.gabrielvidaljr.influx.message.message;

import com.gabrielvidaljr.strings.component.InfluxComponent;
import org.bukkit.entity.Player;

import java.util.List;

public interface InfluxMessage {

    boolean isMessageEnabled();
    InfluxComponent getMessageValue();
    boolean isSoundEnabled();
    String getSound();
    int getSoundVolume();
    int getSoundPitch();
    void send(final Player player);
    void send(final List<Player> players);
    void broadcast();
}
