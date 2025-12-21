package fnaf.aim.groupA.hud;

public abstract class MeterHUD {
    protected String name;
    protected int maxValue;
    protected int value;
    protected float x;
    protected float y;
    protected float width;
    protected float height;

    public MeterHUD(String name, int maxValue, float x, float y, float width, float height) {
        this.name = name;
        this.maxValue = maxValue;
        this.value = maxValue;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // --- Getters for GameScreen ---
    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public int getValue() { return value; }
    public int getMaxValue() { return maxValue; }
    public String getName() { return name; }

    // --- Core meter logic ---
    public void setValue(int value) {
        this.value = Math.max(0, Math.min(value, maxValue));
    }

    public void decrease(int amount) {
        setValue(value - amount);
    }

    public void increase(int amount) {
        setValue(value + amount);
    }

    // --- Abstract methods for subclasses ---
    public abstract void render(com.badlogic.gdx.graphics.glutils.ShapeRenderer shape);
    public abstract void reset();
}
