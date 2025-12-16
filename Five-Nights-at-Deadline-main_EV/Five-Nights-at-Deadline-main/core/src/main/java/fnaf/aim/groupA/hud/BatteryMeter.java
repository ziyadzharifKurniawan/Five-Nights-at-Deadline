package fnaf.aim.groupA.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class BatteryMeter extends Meter {

    private BitmapFont font;

    public BatteryMeter(float x, float y, float width, float height) {
        super("Battery", 100, x, y, width, height);
        font = new BitmapFont();
    }

    @Override
    public void render(ShapeRenderer shape) {
        shape.setColor(Color.ORANGE);
        float filledWidth = width * ((float) value / maxValue);
        shape.rect(x, y, filledWidth, height);
        shape.setColor(Color.WHITE);
        shape.end();
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.rect(x, y, width, height);
        shape.end();
        shape.begin(ShapeRenderer.ShapeType.Filled);
    }
    public void renderIcon(SpriteBatch batch) {
        font.setColor(Color.YELLOW);
        font.draw(batch, "⚡", x - 20, y + height - 2);
    }
}
