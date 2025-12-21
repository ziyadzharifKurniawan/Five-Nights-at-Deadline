package fnaf.aim.groupA.ai;

import fnaf.aim.groupA.maps.GameMapGraph;
import fnaf.aim.groupA.maps.MapIds;

import java.util.Set;

// On Toilet, long cd, attacks both windows
public class Enemy4 extends Enemy {
    public Enemy4() {
        super("Placeholder4",
            MapIds.R_TOILET,
            3.0f,   // steady movement
            3.0f,   // very fast windup
            6.0f,   // long time retreat
            0.55f); // kinda smart but also random, half half
    }

    @Override
    public Set<String> targetWindows() {
        return MapIds.BOTH_WINDOWS;
    }

    @Override
    public void update(float delta, GameMapGraph map, DefenseState defense) {

    }
}
