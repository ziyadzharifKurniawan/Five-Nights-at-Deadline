package fnaf.aim.groupA.logic;

public class EnemyLogic {

    public enum WindowSide {
        LEFT, RIGHT
    }

    private float x, y;
    private float targetX, targetY;
    private float speed;

    private boolean alive = true;
    private boolean red = false;

    private final WindowSide targetWindow;
    private final float redThreshold;

    public EnemyLogic(
        float startX,
        float startY,
        float targetX,
        float targetY,
        float speed,
        WindowSide targetWindow,
        float redThreshold
    ) {
        this.x = startX;
        this.y = startY;
        this.targetX = targetX;
        this.targetY = targetY;
        this.speed = speed;
        this.targetWindow = targetWindow;
        this.redThreshold = redThreshold;
    }

    public void update(float delta) {
        if (!alive) return;

        float dx = targetX - x;
        float dy = targetY - y;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);

        if (dist < redThreshold) {
            red = true;
        }

        if (dist > 2f) {
            x += (dx / dist) * speed * delta;
            y += (dy / dist) * speed * delta;
        }
    }

    public void kill() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isRed() {
        return red;
    }

    public WindowSide getTargetWindow() {
        return targetWindow;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
