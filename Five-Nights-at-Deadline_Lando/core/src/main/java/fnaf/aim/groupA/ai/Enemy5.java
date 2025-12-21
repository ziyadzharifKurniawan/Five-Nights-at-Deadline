package fnaf.aim.groupA.ai;

import fnaf.aim.groupA.maps.GameMapGraph;
import fnaf.aim.groupA.maps.MapIds;

import java.util.Collections;
import java.util.Set;

// Change windows after fail on one window
public class Enemy5 extends Enemy {

    private boolean targetLeft = true;
    private final float dashChance;

    public Enemy5() {
        this(0.35f);
    }

    public Enemy5(float dashChance) {
        super("Placeholder5",
            MapIds.H_SCAN,
            3.3f,   // movement
            5.0f,   // windup
            2.5f,   // retreat cooldown
            0.85f); // generally shortest path
        this.dashChance = Math.max(0f, Math.min(1f, dashChance));
    }

    @Override
    public Set<String> targetWindows() {
        return Collections.singleton(targetLeft ? MapIds.W_LEFT : MapIds.W_RIGHT);
    }

    // Change window if not get in
    @Override
    public void update(float delta, GameMapGraph map, DefenseState defense) {
        EnemyState before = state;
        String beforeNode = currentNode;
        super.update(delta, map, defense);

        if (before == EnemyState.AT_WINDOW
            && state == EnemyState.RETREATING
            && beforeNode != null) {
            targetLeft = !targetLeft;
        }
    }
}
