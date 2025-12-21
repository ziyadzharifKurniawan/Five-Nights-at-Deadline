package fnaf.aim.groupA.logic;

import com.badlogic.gdx.utils.Array;
import java.util.Iterator;
import java.util.Random;

public class EnemyManager {

    private Array<EnemyLogic> enemies = new Array<>();
    private Random random = new Random();

    private float spawnTimer = 0f;
    private float lowSpawnInterval = 4f;
    private float highSpawnInterval = 0.4f;

    private float laneX;
    private float laneY;
    private float laneW;
    private float laneH;

    private float leftWindowX;
    private float leftWindowY;

    private float rightWindowX;
    private float rightWindowY;

    private float redThreshold = 80f;

    public EnemyManager(float laneX,
                        float laneY,
                        float laneW,
                        float laneH,
                        float leftWindowX,
                        float leftWindowY,
                        float rightWindowX,
                        float rightWindowY) {

        this.laneX = laneX;
        this.laneY = laneY;
        this.laneW = laneW;
        this.laneH = laneH;

        this.leftWindowX = leftWindowX;
        this.leftWindowY = leftWindowY;

        this.rightWindowX = rightWindowX;
        this.rightWindowY = rightWindowY;
    }

    public int update(float delta, boolean phoneOpen, boolean flashLeft, boolean flashRight) {

        spawnTimer -= delta;

        float interval = phoneOpen ? highSpawnInterval : lowSpawnInterval;

        if (spawnTimer <= 0f) {
            spawnEnemy();
            spawnTimer = interval;
        }

        int attackedEnemyId = 0;
        Iterator<EnemyLogic> it = enemies.iterator();
        while (it.hasNext()) {
            EnemyLogic e = it.next();
            e.update(delta);

            if (flashLeft && e.getTargetWindow() == EnemyLogic.WindowSide.LEFT && e.isRed()) {
                e.kill();
            }

            if (flashRight && e.getTargetWindow() == EnemyLogic.WindowSide.RIGHT && e.isRed()) {
                e.kill();
            }

            if (e.hasAttacked()) {
                if (attackedEnemyId == 0) attackedEnemyId = e.getEnemyId();
            }

            if (!e.isAlive()) {
                it.remove();
            }
        }
        return attackedEnemyId;
    }

    private void spawnEnemy() {
        float spawnX = laneX + random.nextFloat() * laneW;
        float spawnY = laneY + laneH;

        boolean left = random.nextBoolean();

        float targetX = left ? leftWindowX : rightWindowX;
        float targetY = left ? leftWindowY : rightWindowY;

        float speed = 20f + random.nextFloat() * 20f;
        int enemyId = random.nextInt(5) + 1;

        EnemyLogic.WindowSide side = left ? EnemyLogic.WindowSide.LEFT : EnemyLogic.WindowSide.RIGHT;

        enemies.add(new EnemyLogic(
            spawnX,
            spawnY,
            targetX,
            targetY,
            speed,
            enemyId,
            side,
            redThreshold
        ));
    }

    public Array<EnemyLogic> getEnemies() {
        return enemies;
    }

    public float getLaneX() {
        return laneX;
    }

    public float getLaneY() {
        return laneY;
    }

    public float getLaneWidth() {
        return laneW;
    }

    public float getLaneHeight() {
        return laneH;
    }
}
