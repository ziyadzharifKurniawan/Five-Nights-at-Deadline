package fnaf.aim.groupA.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class BatteryMeterHUD extends MeterHUD {

    public BatteryMeterHUD(float x, float y, float width, float height) {
        super("Battery", 100, x, y, width, height);
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

    @Override
    public void reset() {
        value = maxValue;
    }

    public void setProgress(float progress) {
        value = (int)(progress * maxValue);
    }
}
