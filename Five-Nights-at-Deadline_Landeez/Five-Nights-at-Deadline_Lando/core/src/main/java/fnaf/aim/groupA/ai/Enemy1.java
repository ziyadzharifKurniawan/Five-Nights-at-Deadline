package fnaf.aim.groupA.ai;

import fnaf.aim.groupA.maps.GameMapGraph;
import fnaf.aim.groupA.maps.MapIds;

import java.util.Collections;
import java.util.Set;

// Balanced left Enemy
public class Enemy1 extends Enemy {
    public Enemy1() {
        super("Placeholder1",
            MapIds.R_S676,
            3.5f,   // Move interval
            6.0f,   // Windup before jumpscare
            2.0f,   // Retreat cooldown
            0.75f); // Path bias
    }

    @Override
    public Set<String> targetWindows() {
        return Collections.singleton(MapIds.W_LEFT);
    }

    @Override
    public void update(float delta, GameMapGraph map, DefenseState defense) {

    }
}

