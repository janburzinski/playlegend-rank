package de.janburzinski.rank.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LegendGroup {
    public String rank;
    public int rankPrio;
    public String rankPrefix;
    public int rankColor;
}
