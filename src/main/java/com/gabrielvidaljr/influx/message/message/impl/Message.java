package com.gabrielvidaljr.influx.message.message.impl;

import com.cryptomorin.xseries.XSound;
import com.gabrielvidaljr.influx.message.message.InfluxMessage;
import com.gabrielvidaljr.strings.component.InfluxComponent;
import com.gabrielvidaljr.strings.component.impl.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Optional;

public class Message implements InfluxMessage {

    private final boolean messageEnabled, soundEnabled;
    private final InfluxComponent component;
    private final String sound;
    private final int soundPitch, soundValue;

    public Message(boolean messageEnabled, boolean soundEnabled, InfluxComponent component, String sound, int soundPitch, int soundValue) {
        this.messageEnabled = messageEnabled;
        this.soundEnabled = soundEnabled;
        this.component = component;
        this.sound = sound;
        this.soundPitch = soundPitch;
        this.soundValue = soundValue;
    }

    public Message(final FileConfiguration file, final String path, final InfluxComponent component) {
        this.messageEnabled = file.getBoolean(path + ".message.enabled", true);
        this.soundEnabled = file.getBoolean(path + ".sound.enabled", true);
        this.sound = file.getString(path + ".sound.value", "FIZZ");
        this.soundPitch = file.getInt(path + ".sound.pitch", 1);
        this.soundValue = file.getInt(path + ".sound.volume", 1);
        this.component = component;
    }

    @Override
    public boolean isMessageEnabled() {
        return this.messageEnabled;
    }

    @Override
    public InfluxComponent getMessageValue() {
        return this.component;
    }

    @Override
    public boolean isSoundEnabled() {
        return this.soundEnabled;
    }

    @Override
    public String getSound() {
        return this.sound;
    }

    @Override
    public int getSoundVolume() {
        return this.soundValue;
    }

    @Override
    public int getSoundPitch() {
        return this.soundPitch;
    }

    @Override
    public void send(Player player) {
        if (this.messageEnabled) {
            component.parse().forEach(player::sendMessage);
        }

        if (this.soundEnabled) {
            Optional<XSound> xSoundOptional = XSound.matchXSound(this.sound);
            xSoundOptional.ifPresent(xSound -> xSound.play(player, this.soundValue, this.soundPitch));
        }

    }

    @Override
    public void send(List<Player> players) {
        players.forEach(player -> {
            if (this.messageEnabled) {
                component.parse().forEach(player::sendMessage);
            }

            if (this.soundEnabled) {
                Optional<XSound> xSoundOptional = XSound.matchXSound(this.sound);
                xSoundOptional.ifPresent(xSound -> xSound.play(player, this.soundValue, this.soundPitch));
            }
        });

    }

    @Override
    public void broadcast() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (this.messageEnabled) {
                component.parse().forEach(player::sendMessage);
            }

            if (this.soundEnabled) {
                Optional<XSound> xSoundOptional = XSound.matchXSound(this.sound);
                xSoundOptional.ifPresent(xSound -> xSound.play(player, this.soundValue, this.soundPitch));
            }
        });;
    }
}
