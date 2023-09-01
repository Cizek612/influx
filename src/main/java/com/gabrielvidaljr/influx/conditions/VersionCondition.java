package com.gabrielvidaljr.influx.conditions;

import com.gabrielvidaljr.strings.component.version.Version;
import com.gabrielvidaljr.strings.condition.player.Condition;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import org.bukkit.entity.Player;

/**
 * Condition for {@link com.gabrielvidaljr.strings.component.impl.Component} that is based off a player's version
 */
public class VersionCondition implements Condition {

    private final Player player;
    private final Version version;
    private final SearchMode comparison;

    public VersionCondition(final Player player, final Version version, final SearchMode comparison) {
        this.player = player;
        this.version = version;
        this.comparison = comparison;
    }

    @Override
    public boolean hasCondition() {

        final Version playerVersion = Version.valueOf(this.convertVersion(this.getVersion(player)));

        switch (this.comparison) {
            case OLDER -> {
                return playerVersion.comparedTo(this.version) == Version.ComparisonResult.OLDER;
            }
            case NEWER -> {
                return playerVersion.comparedTo(this.version) == Version.ComparisonResult.NEWER;
            }
            case CURRENT -> {
                return playerVersion == this.version;
            }

        }
        return false;
    }

    public String getVersion(final Player player) {
        final int protocolVersion = Via.getAPI().getPlayerVersion(player);

        ProtocolVersion version = ProtocolVersion.getProtocol(protocolVersion);

        if (version != null) {
            return version.getName();
        }

        return "N/A";
    }

    private String convertVersion(final String version) {

        final String[] parts = version.split("\\.");

        if (parts.length >= 2) {
            return "v" + parts[0] + "_" + parts[1] + "_R" + parts[2].charAt(parts[2].length() - 1);
        }

        return "";
    }

    public enum SearchMode {
        OLDER, CURRENT, NEWER;
    }
}
