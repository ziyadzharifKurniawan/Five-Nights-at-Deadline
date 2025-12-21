package fnaf.aim.groupA.logic;

import com.badlogic.gdx.math.Vector2;

public class EnemyLogic {

    public enum WindowSide {
        LEFT,
        RIGHT
    }
    private float x;
    private float y;
    private float targetX;
    private float targetY;
    private float speed;
    private int enemyId;
    private boolean alive;
    private WindowSide targetWindow;
    private boolean red;
    private float redThreshold;
    private boolean atWindow;
    private boolean hasAttacked;

    public EnemyLogic(float spawnX,
                      float spawnY,
                      float targetX,
                      float targetY,
                      float speed,
                      int enemyId,
                      WindowSide targetWindow,
                      float redThreshold) {

        this.x = spawnX;
        this.y = spawnY;

        this.targetX = targetX;
        this.targetY = targetY;

        this.speed = speed;
        this.enemyId = enemyId;
        this.targetWindow = targetWindow;

        this.redThreshold = redThreshold;

        this.alive = true;
        this.red = false;
        this.atWindow = false;
        this.hasAttacked = false;
    }

    public void update(float delta) {
        if (!alive || hasAttacked) return;
        if (atWindow) {
            hasAttacked = true;
            return;
        }
        float dx = targetX - x;
        float dy = targetY - y;

        float len = (float)Math.sqrt(dx * dx + dy * dy);
        if (len > 0.0001f) {
            dx /= len;
            dy /= len;

            x += dx * speed * delta;
            y += dy * speed * delta;
        }
        float dist = Vector2.dst(x, y, targetX, targetY);
        if (!red && dist <= redThreshold) {
            red = true;
        }
        if (dist <= 4f) { // small epsilon
            atWindow = true;
        }
    }
    public void kill() {
        if (!alive) return;
        alive = false;
    }
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isAlive() {
        return alive && !hasAttacked;
    }

    public boolean hasAttacked() {
        return hasAttacked;
    }

    public boolean isRed() {
        return red;
    }

    public WindowSide getTargetWindow() {
        return targetWindow;
    }

    public float getRedThreshold() {
        return redThreshold;
    }

    public float getSpeed() {
        return speed;
    }

    public int getEnemyId() {
        return enemyId;
    }
}
