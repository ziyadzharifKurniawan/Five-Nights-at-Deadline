package fnaf.aim.groupA.ai;

import fnaf.aim.groupA.maps.GameMapGraph;
import fnaf.aim.groupA.maps.LocationType;
import fnaf.aim.groupA.maps.MapNode;

import java.util.*;

public abstract class Enemy {
    protected final String name;
    protected final Random rng = new Random();

    protected EnemyState state = EnemyState.ROAMING;
    protected String currentNode;
    protected String lastNode;

    protected float moveTimer = 0f;
    protected float attackTimer = 0f;
    protected float retreatTimer = 0f;

    protected final float baseMoveInterval;
    protected final float attackWindupSeconds;
    protected final float retreatCooldown;

    protected final float pathBias;

    protected EnemyListener listener;

    protected Enemy(String name,
                    String startNode,
                    float baseMoveInterval,
                    float attackWindupSeconds,
                    float retreatCooldown,
                    float pathBias) {
        this.name = name;
        this.currentNode = startNode;
        this.baseMoveInterval = baseMoveInterval;
        this.attackWindupSeconds = attackWindupSeconds;
        this.retreatCooldown = retreatCooldown;
        this.pathBias = clamp01(pathBias);
    }

    public void setListener(EnemyListener listener) {
        this.listener = listener;
    }

    public String getName() { return name; }
    public EnemyState getState() { return state; }
    public String getCurrentNode() { return currentNode; }

    public abstract Set<String> targetWindows();

    public void update(float delta, GameMapGraph map, DefenseState defense) {
        if (state == EnemyState.JUMPSCARE) return;

        switch (state) {
            case ROAMING -> updateRoaming(delta, map);
            case AT_WINDOW -> updateAtWindow(delta, defense);
            case RETREATING -> updateRetreating(delta);
        }
    }

    private void updateRoaming(float delta, GameMapGraph map) {
        moveTimer += delta;
        if (moveTimer < baseMoveInterval) return;
        moveTimer = 0f;

        MapNode node = map.getNode(currentNode);
        if (node == null || node.getNeighbors().isEmpty()) return;

        String next = chooseNextMove(map, node.getNeighbors());
        if (next == null) return;

        lastNode = currentNode;
        currentNode = next;

        MapNode arrived = map.getNode(currentNode);
        if (arrived != null && arrived.getType() == LocationType.WINDOW) {
            state = EnemyState.AT_WINDOW;
            attackTimer = 0f;
            if (listener != null) listener.onEnemyReachedWindow(this, currentNode);
        }
    }

    private void updateAtWindow(float delta, DefenseState defense) {
        // If window is locked (minigame success), enemy gets pushed back
        if (defense.isWindowLocked(currentNode)) {
            state = EnemyState.RETREATING;
            retreatTimer = 0f;
            if (listener != null) listener.onEnemyRetreated(this, currentNode);
            return;
        }

        attackTimer += delta;
        if (attackTimer >= attackWindupSeconds) {
            state = EnemyState.JUMPSCARE;
            if (listener != null) listener.onEnemyJumpscare(this);
        }
    }

    private void updateRetreating(float delta) {
        retreatTimer += delta;
        if (retreatTimer < retreatCooldown) return;
        if (lastNode != null) {
            currentNode = lastNode;
        }
        state = EnemyState.ROAMING;
    }

    private String chooseNextMove(GameMapGraph map, Set<String> neighbors) {
        // With probability pathBias, take shortest step toward any target window
        if (rng.nextFloat() < pathBias) {
            List<String> path = map.shortestPathNextStep(currentNode, targetWindows());
            if (path.size() >= 2) return path.get(1);
        }
        // If not, Random Neighbor
        int idx = rng.nextInt(neighbors.size());
        int i = 0;
        for (String n : neighbors) {
            if (i++ == idx) return n;
        }
        return null;
    }

    private static float clamp01(float v) {
        return Math.max(0f, Math.min(1f, v));
    }

}
