package fnaf.aim.groupA.hud;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Meter {
    protected String name;
    protected int value;
    protected int maxValue;
    protected float x, y, width, height;
    public Meter(String name, int maxValue, float x, float y, float width, float height) {
        this.name = name;
        this.maxValue = maxValue;
        this.value = maxValue;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void increase(int amount) {
        value = Math.min(maxValue, value + amount);
    }

    public void decrease(int amount) {
        value = Math.max(0, value - amount);
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public abstract void render(ShapeRenderer shape);
}
