package fnaf.aim.groupA.ai;

public interface EnemyListener {
    void onEnemyReachedWindow(Enemy enemy, String windowId);
    void onEnemyRetreated(Enemy enemy, String fromWindowId);
    void onEnemyJumpscare(Enemy enemy);
}

