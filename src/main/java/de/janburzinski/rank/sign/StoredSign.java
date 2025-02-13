package de.janburzinski.rank.sign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.World;

@Getter
@Setter
@AllArgsConstructor
public class StoredSign {
    public World world;
    public double x, y, z;
}
