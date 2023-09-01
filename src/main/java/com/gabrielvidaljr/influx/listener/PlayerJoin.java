package com.gabrielvidaljr.influx.listener;

import com.gabrielvidaljr.influx.InfluxSpigotPlugin;
import com.gabrielvidaljr.influx.conditions.GradientCondition;
import com.gabrielvidaljr.influx.conditions.RainbowCondition;
import com.gabrielvidaljr.influx.conditions.VersionCondition;
import com.gabrielvidaljr.influx.message.InfluxMessageBuilder;
import com.gabrielvidaljr.influx.message.impl.MessageBuilder;
import com.gabrielvidaljr.influx.message.message.InfluxMessage;
import com.gabrielvidaljr.strings.builder.InfluxComponentBuilder;
import com.gabrielvidaljr.strings.builder.impl.ComponentBuilder;
import com.gabrielvidaljr.strings.component.InfluxComponent;
import com.gabrielvidaljr.strings.component.impl.Component;
import com.gabrielvidaljr.strings.component.version.ComponentVersion;
import com.gabrielvidaljr.strings.component.version.Version;
import com.gabrielvidaljr.strings.condition.component.ComponentCondition;
import com.gabrielvidaljr.strings.placeholder.impl.ConditionalPlaceholder;
import com.gabrielvidaljr.strings.placeholder.impl.StringPlaceholder;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Arrays;
import java.util.List;

public class PlayerJoin implements Listener {

    private final InfluxSpigotPlugin plugin;
    private final InfluxComponentBuilder builder;
    private final InfluxMessageBuilder messageBuilder;
    private final ComponentCondition rainbowCondition = new RainbowCondition();
    private final ComponentCondition gradientCondition = new GradientCondition();

    public PlayerJoin(final InfluxSpigotPlugin plugin) {
        this.plugin = plugin;
        this.builder = new ComponentBuilder().markVersion(ComponentVersion.MODERN);

        this.messageBuilder = new MessageBuilder(this.plugin)
                .addCondition(this.gradientCondition)
                .addCondition(this.rainbowCondition)
                .cacheMessages(this.plugin.getConfig(), "messages");

        Bukkit.getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        /*final List<String> message = Arrays.asList(
                "&dHello, [gradient:FFFFFF-881337]%name%[/gradient]&d!",
                "&r ",
                "&d&lVERSION: [rainbow]%version%[/rainbow]",
                "&d&lSUPPORT: [rainbow]%type%[/rainbow]",
                "&r",
                "&7&o(( Enjoy your stay! ))"
        );

        final StringPlaceholder placeholder = new StringPlaceholder("%name%", player.getName());
        final StringPlaceholder versionPlaceholder = new StringPlaceholder("%version%", this.getPlayerMinecraftVersion(player));
        final ConditionalPlaceholder conditionalPlaceholder = new ConditionalPlaceholder("%type%", "Modern", new VersionCondition(player, Version.v1_14_R1, VersionCondition.SearchMode.NEWER));

        this.builder.addPlaceholder(placeholder).addCondition(gradientCondition).addPlaceholder(versionPlaceholder).addConditionalPlaceholder(conditionalPlaceholder, "Legacy").addCondition(rainbowCondition);

        // Split the message into smaller components
        for (String line : message) {
            final InfluxComponent component = builder.toComponent(line);

            component.parse().forEach(player::sendMessage);
        }*/

        final StringPlaceholder placeholder = new StringPlaceholder("%name%", player.getName());
        final StringPlaceholder versionPlaceholder = new StringPlaceholder("%version%", this.getPlayerMinecraftVersion(player));

        final InfluxMessage message = this.messageBuilder
                .addPlaceholder(placeholder)
                .addPlaceholder(versionPlaceholder)
                .getMessage("test");

        message.send(player);
    }


    public String getPlayerMinecraftVersion(Player player) {
        int protocolVersion = Via.getAPI().getPlayerVersion(player);

        ProtocolVersion version = ProtocolVersion.getProtocol(protocolVersion);

        if (version != null) {
            return version.getName();
        } else {
            // Handle the case where the version couldn't be determined.
            return "Unknown";
        }
    }
}
