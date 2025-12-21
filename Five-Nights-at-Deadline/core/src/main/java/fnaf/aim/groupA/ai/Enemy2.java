package fnaf.aim.groupA.ai;

import fnaf.aim.groupA.maps.GameMapGraph;
import fnaf.aim.groupA.maps.MapIds;

import java.util.Collections;
import java.util.Set;

// fast but random Enemy
public class Enemy2 extends Enemy {
    public Enemy2() {
        super("Placeholder2",
            MapIds.R_CANTEEN,
            2.2f,   // faster movement
            7.0f,   // slower windup
            1.2f,   // short retreat cooldown
            0.25f); // mostly random movement
    }

    @Override
    public Set<String> targetWindows() {
        return Collections.singleton(MapIds.W_RIGHT);
    }

    @Override
    public void update(float delta, GameMapGraph map, DefenseState defense) {

    }
}

