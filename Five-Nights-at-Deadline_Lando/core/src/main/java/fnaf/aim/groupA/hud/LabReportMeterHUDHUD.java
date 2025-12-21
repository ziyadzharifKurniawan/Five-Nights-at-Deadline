package fnaf.aim.groupA.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class LabReportMeterHUDHUD extends MeterHUD {

    public LabReportMeterHUDHUD(float x, float y, float width, float height) {
        super("LabReport", 100, x, y, width, height);
        this.value = 0; // start empty
    }

    @Override
    public void render(ShapeRenderer shape) {
        shape.setColor(Color.GREEN);
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
        value = 0;
    }

    public void setProgress(float progress) {
        value = (int)(progress * maxValue);
    }
}
