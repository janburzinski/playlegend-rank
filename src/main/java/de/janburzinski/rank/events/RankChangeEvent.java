package de.janburzinski.rank.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@Getter
public class RankChangeEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    @Setter
    private final UUID uuid;
    @Setter
    private final String rankId;

    public RankChangeEvent(UUID uuid, String rankId) {
        this.uuid = uuid;
        this.rankId = rankId;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
