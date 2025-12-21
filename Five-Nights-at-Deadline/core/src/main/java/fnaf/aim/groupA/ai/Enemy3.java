package fnaf.aim.groupA.ai;

import fnaf.aim.groupA.maps.GameMapGraph;
import fnaf.aim.groupA.maps.MapIds;

import java.util.Collections;
import java.util.Set;

// Slow, always took the shortest path
public class Enemy3 extends Enemy {
    public Enemy3() {
        super("Placeholder3",
            MapIds.R_GK302,
            4.5f,   // slow movement
            4.5f,   // quicker windup
            3.0f,   // medium retreat cooldown
            0.95f); // almost always shortest path
    }

    @Override
    public Set<String> targetWindows() {
        return Collections.singleton(MapIds.W_RIGHT);
    }

    @Override
    public void update(float delta, GameMapGraph map, DefenseState defense) {

    }
}
